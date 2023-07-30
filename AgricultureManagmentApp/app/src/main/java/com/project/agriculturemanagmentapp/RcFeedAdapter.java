package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RcFeedAdapter extends FirebaseRecyclerAdapter<clsFeedModel,RcFeedAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    boolean isMyFeed;
    public RcFeedAdapter(@NonNull FirebaseRecyclerOptions<clsFeedModel> options,Context context,boolean isMyFeed) {
        super(options);
        this.context=context;
        this.isMyFeed=isMyFeed;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcFeedAdapter.ViewHolder holder, int position, @NonNull clsFeedModel model) {

if (model.mediatype.equals("1")){
    holder.imgpost.setVisibility(View.VISIBLE);
    Glide.with(context)
            .load(model.getPost())
            .into(holder.imgpost);
    holder.txtdes.setText(model.des);

}
     else if (model.mediatype.equals("2")) {
          holder.videoView.setVisibility(View.VISIBLE);
          holder.videoView.setVideoURI(Uri.parse(model.post));
          MediaController mediaController=new MediaController(context);
          mediaController.setAnchorView(holder.videoView);
          mediaController.setMediaPlayer(holder.videoView);
          holder.videoView.setMediaController(mediaController);
         // mediaController.requestFocus();
        //  holder.videoView.start();
          holder.txtdes.setText(model.des);

}
     else{
       holder.txtdes.setText(model.des);
}
        SharedPreferences sharedPreferences= context.getSharedPreferences("data",Context.MODE_PRIVATE);
        holder.txtuname.setText(model.getUname());
        holder.txtdate.setText(model.getDate());
        if (isMyFeed){
            holder.btndelete.setVisibility(View.VISIBLE);
        }
        Glide.with(context)
                .load(model.getPrfpc())
                .circleCrop()
                .into(holder.prfpc);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","134567890")).child("Feed").child(model.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        FirebaseDatabase.getInstance().getReference().child("Feed").child(model.key2).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                FirebaseStorage.getInstance().getReference().child("feedimg").child(sharedPreferences.getString("mo","134567890")).child(model.key2).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
        View view= LayoutInflater.from(context).inflate(R.layout.lytfeed,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView prfpc,imgpost;
        TextView txtuname,txtdes,txtdate;
        ImageView btndelete;
        VideoView videoView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prfpc=itemView.findViewById(R.id.profilepc);
            txtuname=itemView.findViewById(R.id.txtuname);
            imgpost=itemView.findViewById(R.id.imgfeed);
            txtdate=itemView.findViewById(R.id.txtdate);
            btndelete=itemView.findViewById(R.id.btndelete);
            txtdes=itemView.findViewById(R.id.txtdes);
            videoView=itemView.findViewById(R.id.videoview);
        }
    }
}
