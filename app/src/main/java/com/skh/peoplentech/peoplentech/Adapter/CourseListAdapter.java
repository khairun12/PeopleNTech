package com.skh.peoplentech.peoplentech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Modle.CourseList;
import com.skh.peoplentech.peoplentech.R;
import com.skh.peoplentech.peoplentech.UI_Activity.CourseDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 3/11/2017.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private Context context;
    private List<CourseList> courseLists;
    //int lastPosition = -1;

    public CourseListAdapter(List<CourseList> courseLists, Context context) {

        super();

        this.courseLists = courseLists;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_row1, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CourseList course = courseLists.get(position);


        /*Picasso.with(context)
                .load(course.getC_img())
                .placeholder(R.mipmap.ic_launcher)   // optional
                .error(R.drawable.banner)      // optional
                //.resize(500,300)                        // optional
                .into(holder.bannerImageView);*/

        holder.nameTextView.setText(course.getC_name());
        holder.id_tv.setText(course.getC_id());

        Log.i("CCC",course.getC_id()+" "+course.getC_name());
/*

        Animation animation = AnimationUtils.loadAnimation(context,(position > lastPosition) ?
                R.anim.up_from_bottom : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
*/


    }

    @Override
    public int getItemCount() {

        return courseLists.size();
    }

    public void setFilter(List<CourseList> courseLists) {
        this.courseLists = new ArrayList<>();
        this.courseLists.addAll(courseLists);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View card_view;
        //public ImageView bannerImageView;
        public TextView nameTextView;
        public TextView id_tv;
        private Button apply;


        public ViewHolder(View itemView) {

            super(itemView);
            card_view = (View) itemView.findViewById(R.id.card_view);
            //bannerImageView = (ImageView) itemView.findViewById(R.id.banner_imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.tittel_tv);
            id_tv = (TextView) itemView.findViewById(R.id.id_tv);
            apply = (Button) itemView.findViewById(R.id.apply_btn);


            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        CourseList clickedDataItem = courseLists.get(pos);
                        //   Toast.makeText(v.getContext(), "You clicked ID: " + clickedDataItem.getC_id(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, CourseDetailsActivity.class);

                        //String[] array ={"Facebook","https://www.facebook.com/peoplentech/"};
                        //String[] array = {clickedDataItem.getC_name(),  "https://master.piit.us/api/en/batch/" + clickedDataItem.getC_id()};
                        String name = clickedDataItem.getC_name();
                        String url = clickedDataItem.getC_id();
                        intent.putExtra("name", Html.fromHtml(name));
                        intent.putExtra("url", url);
                        intent.putExtra("from", "course/");
                        context.startActivity(intent);
                        // context.startActivity(intent);
                    }
                }
            });

            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        CourseList clickedDataItem = courseLists.get(pos);
                        //   Toast.makeText(v.getContext(), "You clicked ID: " + clickedDataItem.getC_id(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, CourseDetailsActivity.class);

                        String name = clickedDataItem.getC_name();
                        String url = clickedDataItem.getC_id();
                        intent.putExtra("name", name);
                        intent.putExtra("url", url);
                        intent.putExtra("from", "course/");
                        context.startActivity(intent);
                    }
                }
            });

        }
    }




}