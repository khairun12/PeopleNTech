package com.skh.peoplentech.peoplentech.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Modle.CourseM;
import com.skh.peoplentech.peoplentech.Modle.LatestOffer;
import com.skh.peoplentech.peoplentech.R;
import com.skh.peoplentech.peoplentech.UI_Activity.CourseDetailsActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.DetailsActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.OfferActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.WebActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.ls.LSException;

import java.util.List;

/**
 * Created by samir on 4/8/2017.
 */

public class LatestOfferAdapter extends RecyclerView.Adapter<LatestOfferAdapter.ViewHolder> {


    private Context context;
    private List<LatestOffer> courseLists;

    public LatestOfferAdapter(List<LatestOffer> latestOffers, Activity offerActivity) {
        this.courseLists = latestOffers;
        this.context = offerActivity;
    }

    @Override
    public LatestOfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_offer_list_row, parent, false);
        LatestOfferAdapter.ViewHolder viewHolder = new LatestOfferAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LatestOfferAdapter.ViewHolder holder, int position) {

        LatestOffer course = courseLists.get(position);


        holder.tittel_tv.setText(course.getOfferTittel());
        holder.id_tv.setText(course.getOfferId());

        String image_url =course.getOfferBanner() ;
        Log.i("SKH_V", image_url);
        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.ic_image_demo)   // optional
                .error(R.drawable.ic_image_demo)      // optional
                //.resize(500,300)                        // optional
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return courseLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tittel_tv;
        public TextView id_tv;
        public ImageView imageView;


        public ViewHolder(View itemView) {

            super(itemView);


            tittel_tv = (TextView) itemView.findViewById(R.id.name_textView);
            id_tv = (TextView) itemView.findViewById(R.id.idtv);
            imageView = (ImageView) itemView.findViewById(R.id.banner_imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        LatestOffer Item = courseLists.get(pos);
                        //   Toast.makeText(v.getContext(), "You clicked ID: " + clickedDataItem.getC_id(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, DetailsActivity.class);

                        //String[] array ={"Facebook","https://www.facebook.com/peoplentech/"};
                     //   String[] array = {clickedDataItem.getOfferTittel(), ConfigKey.URL1 + "json_latest_offer_details.php?id=" + clickedDataItem.getOfferId()};
                        String[] array = {"Notices Details",Item.getOfferTittel(),Item.getOfferBanner(),Item.getOfferDescription()};
                        intent.putExtra("WEB_A_ARRAY", array);
                        context.startActivity(intent);
                        // context.startActivity(intent);
                    }
                }
            });


        }
    }
}
