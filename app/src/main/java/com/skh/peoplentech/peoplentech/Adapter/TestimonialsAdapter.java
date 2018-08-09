package com.skh.peoplentech.peoplentech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skh.peoplentech.peoplentech.Modle.Testimonials;
import com.skh.peoplentech.peoplentech.R;
import com.skh.peoplentech.peoplentech.UI_Activity.VideoPlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * DEVKH Overrides Aug 2018
 */

public class TestimonialsAdapter extends RecyclerView.Adapter<TestimonialsAdapter.ViewHolder> {

   private Context context;
   private ArrayList<Testimonials> testimonialsList;

    public TestimonialsAdapter(ArrayList<Testimonials> testimonialsList, Context context) {

        super();

        this.testimonialsList = testimonialsList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.testimonials_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Testimonials testimonials = testimonialsList.get(position);
        String image_url = "http://img.youtube.com/vi/" + testimonials.getVideoId() + "/default.jpg";

        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.ic_image_demo)   // optional
                .error(R.drawable.ic_image_demo)      // optional
                //.resize(500,300)                        // optional
                .into(holder.bannerImageView);

        holder.nameTextView.setText(testimonials.getUser_name());
        //holder.video_url_tv.setText(testimonials.getVideoId());


    }

    @Override
    public int getItemCount() {

        return testimonialsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView bannerImageView;
        public TextView nameTextView;
        public CardView card_view;


        public ViewHolder(View itemView) {

            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view) ;
            bannerImageView = (ImageView) itemView.findViewById(R.id.banner_imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_textView);
            //video_url_tv = (TextView) itemView.findViewById(R.id.video_url_tv);

            //video_url_tv.setVisibility(View.GONE);
            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        Testimonials clickedDataItem = testimonialsList.get(pos);
                        //   Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getVideo_file(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        intent.putExtra("KEY_StringName", clickedDataItem.getUser_name());
                        intent.putExtra("KEY_String_file_url", clickedDataItem.getVideoId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });

        }
    }


    public void setFilter(List<Testimonials> countryModels) {
        testimonialsList = new ArrayList<>();
        testimonialsList.addAll(countryModels);
        notifyDataSetChanged();
    }


}