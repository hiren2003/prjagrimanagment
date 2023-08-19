package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.getDrawable;
import static androidx.core.content.ContextCompat.startActivity;

public class RcFeedAdapter extends FirebaseRecyclerAdapter<clsFeedModel, RcFeedAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    boolean isMyFeed;

    public RcFeedAdapter(@NonNull FirebaseRecyclerOptions<clsFeedModel> options, Context context, boolean isMyFeed) {
        super(options);
        this.context = context;
        this.isMyFeed = isMyFeed;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcFeedAdapter.ViewHolder holder, int position, @NonNull clsFeedModel model) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        SharedPreferences sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        DatabaseReference parentRef=FirebaseDatabase.getInstance().getReference().child("Like").child(model.getKey());
        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                holder.txtlikecount.setText(count+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean hasliked=snapshot.hasChild(sharedPreferences.getString("mo","123456789"));
                if(hasliked){
                    holder.imglike.setImageDrawable(getDrawable(context,R.drawable.liked));
                }
                else{
                    holder.imglike.setImageDrawable(getDrawable(context,R.drawable.notliked));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
              holder.rllike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       boolean hasliked=dataSnapshot.hasChild(sharedPreferences.getString("mo","123456789"));
                       if(hasliked){
                           FirebaseDatabase.getInstance().getReference().child("Like").child(model.getKey()).child(sharedPreferences.getString("mo","1234567890")).removeValue();
                           holder.imglike.setImageDrawable(getDrawable(context,R.drawable.notliked));
                       }
                       else{
                           FirebaseDatabase.getInstance().getReference().child("Like").child(model.getKey()).child(sharedPreferences.getString("mo","1234567890")).setValue(true);
                           holder.imglike.setImageDrawable(getDrawable(context,R.drawable.liked));
                       }
                        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                long count = dataSnapshot.getChildrenCount();
                                holder.txtlikecount.setText(count+"");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }
        });
        if (model.mediatype.equals("1")) {
            holder.txtdes.setVisibility(View.VISIBLE);
            holder.imgpost.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(model.getPost())
                    .into(holder.imgpost);
            holder.txtdes.setText(model.des);
        } else if (model.mediatype.equals("2")) {
            holder.txtdes.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.VISIBLE);
            holder.videoView.setVideoURI(Uri.parse(model.post));
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(holder.videoView);
            mediaController.setMediaPlayer(holder.videoView);
            holder.videoView.setMediaController(mediaController);
            holder.txtdes.setText(model.des);
        } else {
            holder.txtdes.setVisibility(View.VISIBLE);
            holder.txtdes.setText(model.des);
        }
        holder.txtuname.setText(model.getUname());
        holder.txtdate.setText(model.getDate());
        if (isMyFeed) {
            holder.btndelete.setVisibility(View.VISIBLE);
        }
        Glide.with(context)
                .load(model.getPrfpc())
                .circleCrop()
                .into(holder.prfpc);
        holder.imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, model.getDes());
                intent.setType("text/plain");
                if (model.mediatype.equals("1")) {
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, model.getPost() + "\n" + model.getDes());
                } else {
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, model.getDes());
                }
                context.startActivity(Intent.createChooser(intent, "Choose App"));
            }

        });
        holder.imgcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                View view = LayoutInflater.from(context).inflate(R.layout.lytcomments, null, false);
                ImageView addcomment = view.findViewById(R.id.postcomment);
                TextInputEditText edtcomment = view.findViewById(R.id.edtcomment);
                RecyclerView rccomment = view.findViewById(R.id.rccomment);
                FirebaseRecyclerOptions<clsCommentModel> options = new FirebaseRecyclerOptions.Builder<clsCommentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Feed_Comments").child(model.getKey()), clsCommentModel.class)
                        .build();
                rccomment.setLayoutManager(new LinearLayoutManager(context));
                RccommentAdapter rccommentAdapter = new RccommentAdapter(options, context, model.getKey());
                rccomment.setAdapter(rccommentAdapter);
                rccommentAdapter.startListening();
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        rccommentAdapter.stopListening();
                    }
                });
                addcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
                        String mo = sharedPreferences.getString("mo", "1234567890");
                        String key = FirebaseDatabase.getInstance().getReference().child("Feed").child(model.getKey()).child("comments").push().getKey();
                        clsCommentModel clsCommentModel = new clsCommentModel(key, sharedPreferences.getString("uname", "unknown"), sharedPreferences.getString("url", "null").toString(), edtcomment.getText().toString(), sharedPreferences.getString("mo", "1234567890").toString());
                        FirebaseDatabase.getInstance().getReference().child("Feed_Comments").child(model.getKey()).child(key).setValue(clsCommentModel);
                        edtcomment.setText("");
                        Toast.makeText(context, "Comment Added", Toast.LENGTH_SHORT).show();
                    }
                });
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
            }
        });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "134567890")).child("Feed").child(model.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        FirebaseDatabase.getInstance().getReference().child("Feed").child(model.key2).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                FirebaseStorage.getInstance().getReference().child("feedimg").child(sharedPreferences.getString("mo", "134567890")).child(model.key2).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, context.getResources().getString(R.string.Post_Deleted), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public RcFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytfeed, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView prfpc, imgpost, imgshare, imgcomment;
        TextView txtuname, txtdes, txtdate,txtlikecount;
        ImageView btndelete,imglike;
        VideoView videoView;
        RelativeLayout rllike;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prfpc = itemView.findViewById(R.id.profilepc);
            txtuname = itemView.findViewById(R.id.txtuname);
            imgpost = itemView.findViewById(R.id.imgfeed);
            txtdate = itemView.findViewById(R.id.txtdate);
            btndelete = itemView.findViewById(R.id.btndelete);
            txtdes = itemView.findViewById(R.id.txtdes);
            videoView = itemView.findViewById(R.id.videoview);
            imgshare = itemView.findViewById(R.id.share);
            imgcomment = itemView.findViewById(R.id.comment);
            rllike=itemView.findViewById(R.id.rllike);
            imglike=itemView.findViewById(R.id.like);
            txtlikecount=itemView.findViewById(R.id.likecount);
        }
    }
}
