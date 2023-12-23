package com.project.agriculturemanagmentapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.StackView;
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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.getDrawable;
import static androidx.core.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

public class RcFeedAdapter extends RecyclerView.Adapter<RcFeedAdapter.ViewHolder> {
    Context context;
    boolean isMyFeed;
    ArrayList<clsFeedModel> feedModelArrayList;

    public RcFeedAdapter(Context context, boolean isMyFeed, ArrayList<clsFeedModel> feedModelArrayList) {
        this.context = context;
        this.isMyFeed = isMyFeed;
        this.feedModelArrayList = feedModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference().child("Like").child(feedModelArrayList.get(position).getKey());
        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                holder.txtlikecount.setText(count + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean hasliked = snapshot.hasChild(sharedPreferences.getString("mo", "123456789"));
                if (hasliked) {
                    holder.imglike.setImageDrawable(getDrawable(context, R.drawable.liked));
                } else {
                    holder.imglike.setImageDrawable(getDrawable(context, R.drawable.notliked));
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
                        boolean hasliked = dataSnapshot.hasChild(sharedPreferences.getString("mo", "123456789"));
                        if (hasliked) {
                            FirebaseDatabase.getInstance().getReference().child("Like").child(feedModelArrayList.get(position).getKey()).child(sharedPreferences.getString("mo", "1234567890")).removeValue();
                            holder.imglike.setImageDrawable(getDrawable(context, R.drawable.notliked));
                        } else {
                            FirebaseDatabase.getInstance().getReference().child("Like").child(feedModelArrayList.get(position).getKey()).child(sharedPreferences.getString("mo", "1234567890")).setValue(true);
                            holder.imglike.setImageDrawable(getDrawable(context, R.drawable.liked));
                        }
                        parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                long count = dataSnapshot.getChildrenCount();
                                holder.txtlikecount.setText(count + "");
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
        if (feedModelArrayList.get(position).mediatype.equals("1")) {
            holder.cdfeed.setVisibility(View.VISIBLE);
            holder.txtdes.setVisibility(View.VISIBLE);
            holder.imgpost.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(feedModelArrayList.get(position).getPost())
                    .into(holder.imgpost);
            holder.txtdes.setText(feedModelArrayList.get(position).des);
        }
        else if (feedModelArrayList.get(position).mediatype.equals("2")) {
            holder.txtdes.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.VISIBLE);
            holder.videoView.setVideoURI(Uri.parse(feedModelArrayList.get(position).post));
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(holder.videoView);
            mediaController.setMediaPlayer(holder.videoView);
            holder.videoView.setMediaController(mediaController);
            holder.txtdes.setText(feedModelArrayList.get(position).des);
        }
        else {
            holder.txtdes.setVisibility(View.VISIBLE);
            holder.txtdes.setText(feedModelArrayList.get(position).des);
        }
        FirebaseDatabase.getInstance().getReference().child("Users_List").child(feedModelArrayList.get(position).getUmo().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clsUserModel clsUserModel=snapshot.getValue(com.project.agriculturemanagmentapp.clsUserModel.class);
                   holder.txtuname.setText(clsUserModel.getUname());
                Glide.with(context)
                        .load(clsUserModel.getUrl())
                      .circleCrop()
                    .into(holder.prfpc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.txtdate.setText(feedModelArrayList.get(position).getDate());

        if (isMyFeed) {
            holder.btndelete.setVisibility(View.VISIBLE);
        }
        holder.imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, feedModelArrayList.get(position).getDes());
                intent.setType("text/plain");
                if (feedModelArrayList.get(position).mediatype.equals("1")) {
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, feedModelArrayList.get(position).getPost() + "\n" + feedModelArrayList.get(position).getDes());
                } else {
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, feedModelArrayList.get(position).getDes());
                }
                context.startActivity(Intent.createChooser(intent, "Choose App"));
            }

        });
        FirebaseDatabase.getInstance().getReference().child("Feed_Comments").child(feedModelArrayList.get(position).getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.commentcount.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.imgcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context,R.style.SheetDialog);
                View view = LayoutInflater.from(context).inflate(R.layout.lyt_comment_sheet, null, false);
                TextView addcomment = view.findViewById(R.id.postcomment);
                TextInputEditText edtcomment = view.findViewById(R.id.edtcomment);
                RecyclerView rccomment = view.findViewById(R.id.rccomment);
                FirebaseDatabase.getInstance().getReference().child("Feed_Comments").child(feedModelArrayList.get(position).getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<clsCommentModel> clsCommentModelArrayList =new ArrayList<>();
                        for (DataSnapshot datasnapshot:
                                snapshot.getChildren()) {
                            clsCommentModelArrayList.add(datasnapshot.getValue(clsCommentModel.class));
                        }
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                        linearLayoutManager.setReverseLayout(true);
                        linearLayoutManager.setStackFromEnd(true);
                        rccomment.setLayoutManager(linearLayoutManager);
                        RccommentAdapter rccommentAdapter = new RccommentAdapter(context, feedModelArrayList.get(position).getKey(),clsCommentModelArrayList);
                        rccomment.setAdapter(rccommentAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                addcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
                        String mo = sharedPreferences.getString("mo", "1234567890");
                        String key = FirebaseDatabase.getInstance().getReference().child("Feed").child(feedModelArrayList.get(position).getKey()).child("comments").push().getKey();
                        clsCommentModel clsCommentModel = new clsCommentModel(key, edtcomment.getText().toString(), sharedPreferences.getString("mo", "1234567890").toString());
                        FirebaseDatabase.getInstance().getReference().child("Feed_Comments").child(feedModelArrayList.get(position).getKey()).child(key).setValue(clsCommentModel);
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
                Dialog dg=new Dialog(context);
                dg.setContentView(R.layout.lyt_delete_dg);
                dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                AppCompatButton yes = dg.findViewById(R.id.yes);
                ImageView no = dg.findViewById(R.id.no);
                dg.setCancelable(false);
                dg.show();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dg.dismiss();
                        FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "134567890")).child("Feed").child(feedModelArrayList.get(position).getKey()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                FirebaseDatabase.getInstance().getReference().child("Feed").child(feedModelArrayList.get(position).key2).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        FirebaseStorage.getInstance().getReference().child("feedimg").child(sharedPreferences.getString("mo", "134567890")).child(feedModelArrayList.get(position).key2).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dg.dismiss();
                    }
                });
            }
        });
        holder.imgsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "134567890")).child("Saved").child("Feed").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean hassaved = snapshot.hasChild(feedModelArrayList.get(position).getKey());
                        if (hassaved) {
                            holder.imgsave.setImageDrawable(context.getDrawable(R.drawable.notbookmark));
                            FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "134567890")).child("Saved").child("Feed").child(feedModelArrayList.get(position).getKey()).removeValue();
                        } else {
                            holder.imgsave.setImageDrawable(context.getDrawable(R.drawable.bookmark));
                            FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "134567890")).child("Saved").child("Feed").child(feedModelArrayList.get(position).getKey()).setValue(feedModelArrayList.get(position));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "134567890")). child("Saved").child("Feed").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean hassaved = snapshot.hasChild(feedModelArrayList.get(position).getKey());
                if (hassaved){
                    holder.imgsave.setImageDrawable(context.getDrawable(R.drawable.bookmark));
                }
                else{
                    holder.imgsave.setImageDrawable(context.getDrawable(R.drawable.notbookmark));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.rvprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,MyProfile.class).putExtra("mo",feedModelArrayList.get(position).getUmo()));
            }
        });
        holder.rvtape.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
                    holder.imgdlike.setAnimation(anim);
                    holder.imgdlike.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
                            holder.imgdlike.setAnimation(anim);
                            holder.imgdlike.setVisibility(View.GONE);
                        }
                    },1000);
                    parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean hasliked = dataSnapshot.hasChild(sharedPreferences.getString("mo", "123456789"));

                            FirebaseDatabase.getInstance().getReference().child("Like").child(feedModelArrayList.get(position).getKey()).child(sharedPreferences.getString("mo", "1234567890")).setValue(true);
                            holder.imglike.setImageDrawable(getDrawable(context, R.drawable.liked));

                            parentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    long count = dataSnapshot.getChildrenCount();
                                    holder.txtlikecount.setText(count + "");
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
                    return super.onDoubleTap(e);
                }
                @Override
                public boolean onSingleTapConfirmed(MotionEvent event) {
                    return false;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                gestureDetector.onTouchEvent(event);
                return true;
            }


        });


    }

    @Override
    public int getItemCount() {
        return feedModelArrayList.size();
    }

    @NonNull
    @Override
    public RcFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_feed, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView prfpc, imgpost, imgshare, imgcomment;
        TextView txtuname, txtdes, txtdate, txtlikecount,commentcount;
        ImageView btndelete, imglike, imgdlike,imgsave;
        VideoView videoView;
        RelativeLayout rllike,rvprofile;
        RelativeLayout rvtape;
        CardView cdfeed;


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
            rllike = itemView.findViewById(R.id.rllike);
            imglike = itemView.findViewById(R.id.like);
            txtlikecount = itemView.findViewById(R.id.likecount);
            imgdlike = itemView.findViewById(R.id.imgdlike);
            imgsave=itemView.findViewById(R.id.imgsave);
            rvprofile=itemView.findViewById(R.id.rvprofile);
            rvtape=itemView.findViewById(R.id.rvtape);
            cdfeed=itemView.findViewById(R.id.cdfeed);
            commentcount=itemView.findViewById(R.id.commentcount);

        }
    }
}
