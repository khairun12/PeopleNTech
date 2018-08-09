package com.skh.peoplentech.peoplentech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Modle.UpcomingBatch;
import com.skh.peoplentech.peoplentech.R;
import com.skh.peoplentech.peoplentech.UI_Activity.CourseApplyActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.CourseDetailsActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.WebActivity;

import java.util.List;

/**
 * Created by samir on 3/11/2017.
 */

public class UpcomingBatchCourseListAdapter extends RecyclerView.Adapter<UpcomingBatchCourseListAdapter.ViewHolder> {

    private Context context;
    private List<UpcomingBatch> courseLists;

    public UpcomingBatchCourseListAdapter(List<UpcomingBatch> courseLists, Context context) {

        super();

        this.courseLists = courseLists;
        this.context = context;



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_batch_course_lislist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UpcomingBatch course = courseLists.get(position);


        holder.tittel_tv.setText(course.getCourse_name());
        holder.id_tv.setText(course.getUpc_id());
        //holder.location_tv.setText(course.getContent());
        //holder.type_tv.setText(course.getType());
        holder.starting_date_tv.setText(course.getStarting_date());
        holder.campus_tv.setText(course.getLast_date());



    }

    @Override
    public int getItemCount() {

        return courseLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tittel_tv;
        public TextView id_tv;
        public TextView location_tv;
        //public TextView type_tv;
        public TextView starting_date_tv;
        public TextView campus_tv;
        public Button details_btn;
        public Button reg_btn;


        public ViewHolder(View itemView) {

            super(itemView);


            tittel_tv         = (TextView) itemView.findViewById(R.id.tittel_tv);
            id_tv             = (TextView) itemView.findViewById(R.id.id_tv);

            //location_tv       = (TextView) itemView.findViewById(R.id.location_tv);
            //type_tv           = (TextView) itemView.findViewById(R.id.type_tv);
            starting_date_tv  = (TextView) itemView.findViewById(R.id.starting_date_tv);
            campus_tv         = (TextView) itemView.findViewById(R.id.campus_tv);


            details_btn = (Button) itemView.findViewById(R.id.details_btn);
            reg_btn = (Button) itemView.findViewById(R.id.reg_btn);



            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        UpcomingBatch clickedDataItem = courseLists.get(pos);
                        //   Toast.makeText(v.getContext(), "You clicked ID: " + clickedDataItem.getC_id(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, CourseDetailsActivity.class);

                        //String[] array ={"Facebook","https://www.facebook.com/peoplentech/"};
                        String[] array = {clickedDataItem.getCourse_name(), ConfigKey.APPS_URL + "json_course_details.php?id=" + clickedDataItem.getUpc_id()};
                        intent.putExtra("WEB_A_ARRAY", array);
                        context.startActivity(intent);
                        // context.startActivity(intent);
                    }
                }
            });*/


            details_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        UpcomingBatch clickedDataItem = courseLists.get(pos);
                        //   Toast.makeText(v.getContext(), "You clicked ID: " + clickedDataItem.getC_id(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, CourseDetailsActivity.class);

                        //String[] array ={"Facebook","https://www.facebook.com/peoplentech/"};
                        String[] array = {clickedDataItem.getCourse_name(), ConfigKey.APPS_URL + "batch/" + clickedDataItem.getUpc_id()};
                        String name = clickedDataItem.getCourse_name();
                        String url = clickedDataItem.getUpc_id();
                        intent.putExtra("name", name);
                        intent.putExtra("url", url);
                        intent.putExtra("from", "batch/");
                        context.startActivity(intent);
                        // context.startActivity(intent);
                    }
                }
            });

            //16
            reg_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //int pos = getAdapterPosition();
                    //UpcomingBatch clickedDataItem = courseLists.get(pos);
                    //   Toast.makeText(v.getContext(), "You clicked ID: " + clickedDataItem.getC_id(), Toast.LENGTH_SHORT).show();


                    //String url = ConfigKey.Reg_upcomming_batches_URL1+"?id=" + clickedDataItem.getUpc_id();

                    //String[] web_a_array = {clickedDataItem.getCourse_name(), url};
                    Intent intent = new Intent(context, CourseApplyActivity.class);
                    //intent.putExtra("WEB_A_ARRAY",web_a_array);
                    context.startActivity(intent);

                }
            });

        }
    }
}