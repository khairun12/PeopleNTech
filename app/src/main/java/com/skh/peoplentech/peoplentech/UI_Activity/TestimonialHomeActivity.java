package com.skh.peoplentech.peoplentech.UI_Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.skh.peoplentech.peoplentech.R;

public class TestimonialHomeActivity extends AppCompatActivity {

    public CardView usa_video, ban_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimonial_home);



        usa_video = (CardView) findViewById(R.id.cardUsa);
        ban_video = (CardView) findViewById(R.id.cardBangladesh);


        usa_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestimonialHomeActivity.this,TestimonialsListActivity.class);
                intent.putExtra("activity", "usa");
                startActivity(intent);
            }
        });


        ban_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestimonialHomeActivity.this,TestimonialsListActivity.class);
                intent.putExtra("activity", "bangla");
                startActivity(intent);
            }
        });
    }



}
