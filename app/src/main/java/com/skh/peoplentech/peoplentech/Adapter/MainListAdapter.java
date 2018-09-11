package com.skh.peoplentech.peoplentech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Modle.ListItem;
import com.skh.peoplentech.peoplentech.R;
import com.skh.peoplentech.peoplentech.UI_Activity.CVActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.CourseListActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.DummyFragment;
import com.skh.peoplentech.peoplentech.UI_Activity.NewsEventActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.NoticeActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.OfferActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.SubmitResumeActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.UpcomingBatchActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.WebActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.WebViewActivity;

import java.util.List;

/**
 * Created by samir on 3/6/2017.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.RecyclerViewHolders> {

    private List<ListItem> itemList;
    private Context context;

    public MainListAdapter(Context context, List<ListItem> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

       //View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_view_row, null);
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_row_1, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getImage());



    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


    class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView countryName;
        public ImageView countryPhoto;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            countryName = (TextView) itemView.findViewById(R.id.name_textView);
            countryPhoto = (ImageView) itemView.findViewById(R.id.banner_imageView);
        }

        @Override
        public void onClick(View view) {
           // Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();

            switch (getAdapterPosition()) {
                case 0:
                    context.startActivity(new Intent(context, CourseListActivity.class));
                    break;


                case 1:

                    context.startActivity(new Intent(context, UpcomingBatchActivity.class));
                    break;
                case 2:
                    context.startActivity(new Intent(context, OfferActivity.class));
                   /* String[] WEB_A_ARRAY ={"Upcoming New Batches", ConfigKey.upcoming_class_URL1};
                    goWebpage(WEB_A_ARRAY);*/
                   // context.startActivity(new Intent(context, DummyFragment.class));
                    break;

                case 3:
                   /*
                    //news_events_piit_URL
                    String[] WEB_A_ARRAY1 ={"Upcoming Events", ConfigKey.news_events_piit_URL};
                    goWebpage(WEB_A_ARRAY1);
*/
                     context.startActivity(new Intent(context, NoticeActivity.class));
                    break;

                case 4:
                     
                    context.startActivity(new Intent(context, NewsEventActivity.class));
                    
                    break;

                case 5:

                    context.startActivity(new Intent(context, CVActivity.class));
                    break;
            }
        }
    }


}