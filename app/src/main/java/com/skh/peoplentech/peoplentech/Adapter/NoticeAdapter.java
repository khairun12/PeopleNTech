package com.skh.peoplentech.peoplentech.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Modle.LatestOffer;
import com.skh.peoplentech.peoplentech.Modle.Notice;
import com.skh.peoplentech.peoplentech.R;
import com.skh.peoplentech.peoplentech.UI_Activity.CourseDetailsActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by samir on 4/8/2017.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {


    private Context context;
    private List<Notice> courseLists;

    public NoticeAdapter(List<Notice> latestOffers, Activity offerActivity) {
        this.courseLists = latestOffers;
        this.context = offerActivity;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_notice, parent, false);
        NoticeAdapter.ViewHolder viewHolder = new NoticeAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {

        Notice course = courseLists.get(position);

        holder.noticeTitle.setText(course.getNotice_title());
        holder.noticeContent.setText(course.getNotice_dt());

    }

    @Override
    public int getItemCount() {

        return courseLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView noticeTitle;
        public CardView myCard;
        public TextView detailsBtn;
        public TextView noticeContent;
        public TextView contentText;
        public String contentString;


        public ViewHolder(View itemView) {

            super(itemView);

            noticeTitle = (TextView) itemView.findViewById(R.id.notice_title);
            myCard = (CardView) itemView.findViewById(R.id.my_card_notice);
            detailsBtn = (TextView) itemView.findViewById(R.id.details_notice);
            noticeContent = (TextView) itemView.findViewById(R.id.notice_content);


            detailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        Notice Item = courseLists.get(pos);
                        //   Toast.makeText(v.getContext(), "You clicked ID: " + clickedDataItem.getC_id(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, DetailsActivity.class);

                        //String[] array ={"Facebook","https://www.facebook.com/peoplentech/"};
                      //  String[] array = {Item.getNotice_title(), ConfigKey.URL1 + "notice-details-piit.php?notice=" + clickedDataItem.getNotice_title()};

                        String[] array = {"Notices Details",Item.getNotice_title(),Item.getImage_file(),Item.getNotice_dt()};
                        intent.putExtra("WEB_A_ARRAY", array);
                        context.startActivity(intent);
                        // context.startActivity(intent);
                    }*/
                    /*AlertDialog.Builder mBuilder = new AlertDialog.Builder(context.getApplicationContext());
                    View mView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.card_popup,null);
                    //final EditText reset_email = (EditText) mView.findViewById(R.id.resetEmail);
                    //final Button send_pass = (Button) mView.findViewById(R.id.resetPass);


                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    send_pass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!reset_email.getText().toString().isEmpty()){

                                Toast.makeText(LoginActivity.this,

                                        "Well Done",

                                        Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                            }else{

                                Toast.makeText(LoginActivity.this,

                                        "Failed",

                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });*/
                    if (noticeContent.getVisibility() == View.GONE){
                        noticeContent.setVisibility(View.VISIBLE);
                    } else {
                        noticeContent.setVisibility(View.GONE);
                    }

                }
            });

        }
    }
}
