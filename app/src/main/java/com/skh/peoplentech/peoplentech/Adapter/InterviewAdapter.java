package com.skh.peoplentech.peoplentech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skh.peoplentech.peoplentech.Modle.Interview;
import com.skh.peoplentech.peoplentech.Modle.RadiusImageView;
import com.skh.peoplentech.peoplentech.Modle.Testimonials;
import com.skh.peoplentech.peoplentech.R;
import com.skh.peoplentech.peoplentech.UI_Activity.VideoPlayerActivity;
import com.skh.peoplentech.peoplentech.com.skh.CustomImageRoundCorner;
import com.skh.peoplentech.peoplentech.com.skh.ImageRoundCorners;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 4/2/2017.
 */

public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.ViewHolder> {

    Context context;

    List<Interview> testimonialsList;

    public InterviewAdapter(List<Interview> testimonialsList, Context context) {

        super();

        this.testimonialsList = testimonialsList;
        this.context = context;

    }

    @Override
    public InterviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.testimonials_list_row, parent, false);
        InterviewAdapter.ViewHolder viewHolder = new InterviewAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InterviewAdapter.ViewHolder holder, int position) {

        Interview testimonials = testimonialsList.get(position);
        String image_url = "http://img.youtube.com/vi/" + testimonials.getTAG_video_link() + "/default.jpg";
        Log.i("SKH_V", image_url);
        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.ic_image_demo)   // optional
                .error(R.drawable.ic_image_demo)      // optional
                //.resize(500,300)                        // optional
                .into(holder.bannerImageView)    ;

      /*  Picasso.with(context).load(image_url).fit().transform(new CustomImageRoundCorner())
                .into(holder.bannerImageView);
*/
        holder.nameTextView.setText(testimonials.getTAG_v_title());

        holder.video_url_tv.setText(testimonials.getTAG_video_link());
       // RadiusImageView.getRadiusImageView(holder.bannerImageView);


    }

    @Override
    public int getItemCount() {

        return testimonialsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView bannerImageView;
        public TextView nameTextView;
        public TextView name_textView1;
        // public TextView language_tv;
        public TextView video_url_tv;


        public ViewHolder(View itemView) {

            super(itemView);
            //card_view = (View) itemView.findViewById(R.id.card_view) ;
            bannerImageView = (ImageView) itemView.findViewById(R.id.banner_imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_textView);
            //name_textView1 = (TextView) itemView.findViewById(R.id.name_textView1);
            //  language_tv = (TextView) itemView.findViewById(R.id.language_tv) ;
            //video_url_tv = (TextView) itemView.findViewById(R.id.video_url_tv);

            name_textView1.setVisibility(View.GONE);
            video_url_tv.setVisibility(View.GONE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        Interview clickedDataItem = testimonialsList.get(pos);
                        //   Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getVideo_file(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        intent.putExtra("KEY_StringName", clickedDataItem.getTAG_v_title());
                        intent.putExtra("KEY_String_file_url", clickedDataItem.getTAG_video_link());
                        context.startActivity(intent);
                    }
                }
            });

        }
    }


    public void setFilter(List<Interview> countryModels) {
        testimonialsList = new ArrayList<>();
        testimonialsList.addAll(countryModels);
        notifyDataSetChanged();
    }


}