package com.manddprojectconsulant.videostram.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.preference.PreferenceScreen;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.manddprojectconsulant.videostram.Activities.DashboardActivity;
import com.manddprojectconsulant.videostram.Model.ModelforDashboard;
import com.manddprojectconsulant.videostram.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterforDashboard extends RecyclerView.Adapter<AdapterforDashboard.Myholder> {

    List<ModelforDashboard> list;
    Context context;
    String title;

    public OnItemClickListener onItemClickListener;

    public AdapterforDashboard(DashboardActivity context, List<ModelforDashboard> list) {
        this.context = context;
        this.list = list;


    }


    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.designforvideo, parent, false);

        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        title=list.get(position).getTitle();

        holder.titleView.setText(title);
        holder.duration.setText(list.get(position).getDuration());




        //holder.imageviewforthumbail.setImageURI(list.get(position).getVideouri());

        Glide.with(context)
                .load(list.get(position).getThumb())
                .into(holder.imageviewforthumbail);








     /*   holder.imageviewforthumbail.initialize("AIzaSyCq8wvReQ0aJSzPEeVHlp5xx74XahjAX1g", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(String.valueOf(list.get(position).getVideouri()));

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        //print or show error when thumbnail load failed
                        Log.e("TAG", "Youtube Thumbnail Error");
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //print or show error when initialization failed
                Log.e("TAG", "Youtube Initialization Failure");

            }
        });



*/


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {

        ImageView imageviewforthumbail;
        CircleImageView profilelogo;
        TextView titleView, duration;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            imageviewforthumbail = itemView.findViewById(R.id.imageforthumbnail);
           // profilelogo = itemView.findViewById(R.id.profileimage);
            titleView = itemView.findViewById(R.id.title_text);
            duration = itemView.findViewById(R.id.duration_text);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v,title);
                }
            });
        }

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int pos, View v,String title);
    }

}
