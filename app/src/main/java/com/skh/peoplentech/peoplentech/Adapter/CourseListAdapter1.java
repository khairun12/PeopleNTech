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
import com.skh.peoplentech.peoplentech.Modle.CourseM;
import com.skh.peoplentech.peoplentech.R;
import com.skh.peoplentech.peoplentech.UI_Activity.CourseDetailsActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.WebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 3/11/2017.
 */

public class CourseListAdapter1 extends RecyclerView.Adapter<CourseListAdapter1.ViewHolder> {

    private Context context;
    private List<CourseM> courseLists;

    public CourseListAdapter1(List<CourseM> courseLists, Context context) {

        super();

        this.courseLists = courseLists;
        this.context = context;

       /* for(CourseM c:this.courseLists){
            Log.i("NAME",c.getC_Tittel());
        }*/

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_row1, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CourseM course = courseLists.get(position);


        holder.tittel_tv.setText(course.getC_Tittel());
        holder.id_tv.setText(course.getC_id());
        holder.description_tv.setText(course.getC_description());
        holder.description_tv.setText("UI Designg, Activity, Services, Google MAP API, WEB Services,Notification");

        holder.duration_tv.setText(course.getC_Duration());

    }

    @Override
    public int getItemCount() {

        return courseLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tittel_tv;
        public TextView id_tv;
        public TextView duration_tv;
        public TextView description_tv;
        public Button apply_btn;


        public ViewHolder(View itemView) {

            super(itemView);


            tittel_tv = (TextView) itemView.findViewById(R.id.tittel_tv);
            id_tv = (TextView) itemView.findViewById(R.id.id_tv);
            duration_tv = (TextView) itemView.findViewById(R.id.duration_tv);
            description_tv = (TextView) itemView.findViewById(R.id.description_tv);
            apply_btn = (Button) itemView.findViewById(R.id.apply_btn);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        CourseM clickedDataItem = courseLists.get(pos);
                        //   Toast.makeText(v.getContext(), "You clicked ID: " + clickedDataItem.getC_id(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, CourseDetailsActivity.class);

                        //String[] array ={"Facebook","https://www.facebook.com/peoplentech/"};
                        String[] array = {clickedDataItem.getC_Tittel(), ConfigKey.APPS_URL + "json_course_details.php?id=" + clickedDataItem.getC_id()};
                        intent.putExtra("WEB_A_ARRAY", array);
                        context.startActivity(intent);
                        // context.startActivity(intent);
                    }
                }
            });

            apply_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    CourseM clickedDataItem = courseLists.get(pos);
                    //   Toast.makeText(v.getContext(), "You clicked ID: " + clickedDataItem.getC_id(), Toast.LENGTH_SHORT).show();


                    String url ="https://docs.google.com/forms/d/e/1FAIpQLSdMiDWjqCwqvPlSRjdVviM_S6mTZheerwZCqRUOPmZ89rIyzw/viewform?c=0&w=1";

                    String[] web_a_array = {clickedDataItem.getC_Tittel(), url};
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("WEB_A_ARRAY",web_a_array);
                    context.startActivity(intent);

                }
            });

        }
    }


    public void setFilter(List<CourseM> courseLists) {
        this.courseLists = new ArrayList<>();
        this.courseLists.addAll(courseLists);
        notifyDataSetChanged();
    }


}