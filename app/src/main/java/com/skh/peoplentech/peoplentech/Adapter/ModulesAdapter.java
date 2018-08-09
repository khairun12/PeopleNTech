package com.skh.peoplentech.peoplentech.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skh.peoplentech.peoplentech.Modle.Modules;
import com.skh.peoplentech.peoplentech.R;

import java.util.List;

public class ModulesAdapter extends RecyclerView.Adapter<ModulesAdapter.ViewHolder> {

    private Context context;
    private List<Modules> courseLists;

    public ModulesAdapter(List<Modules> courseLists, Context context) {

        super();
        this.courseLists = courseLists;
        this.context = context;

    }

    @Override
    public ModulesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_modules, parent, false);
        ModulesAdapter.ViewHolder viewHolder = new ModulesAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ModulesAdapter.ViewHolder holder, int position) {

        Modules course = courseLists.get(position);


        /*Picasso.with(context)
                .load(course.getC_img())
                .placeholder(R.mipmap.ic_launcher)   // optional
                .error(R.drawable.banner)      // optional
                //.resize(500,300)                        // optional
                .into(holder.bannerImageView);*/

        holder.title.setText(course.getModuleName());
        holder.details.setText(course.getModuleContent());

        Log.i("CCC",course.getModuleContent()+" "+course.getModuleName());
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

    class ViewHolder extends RecyclerView.ViewHolder {
        View card_view;
        //public ImageView bannerImageView;
        public TextView title;
        public TextView details;


        public ViewHolder(View itemView) {

            super(itemView);
            card_view = (View) itemView.findViewById(R.id.card_view_modules);
            //bannerImageView = (ImageView) itemView.findViewById(R.id.banner_imageView);
            title = (TextView) itemView.findViewById(R.id.moduleTitle);
            details = (TextView) itemView.findViewById(R.id.modulesDetails);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (details.getVisibility() == View.GONE){
                        details.setVisibility(View.VISIBLE);
                        title.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.devk_up,0);
                    } else {
                        details.setVisibility(View.GONE);
                        title.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.devk_down,0);
                    }
                }
            });


        }
    }




}