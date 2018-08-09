package com.skh.peoplentech.peoplentech.UI_Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.skh.peoplentech.peoplentech.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {

    private String[] array;
    private String toolberTitel,tittel,ImgURL,details;

    private TextView tVtittel,tVDetails;
    private ImageView imgV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle b = this.getIntent().getExtras();
        array = b.getStringArray("WEB_A_ARRAY");

        toolberTitel = array[0];
        tittel = array[1];
        ImgURL = array[2];
        details = array[3];
        inisilize();

        tVtittel.setText(tittel);
        tVDetails.setText(Html.fromHtml(details));
        //tVDetails.setText(details);






        Picasso.with(this)
                .load(ImgURL)
                .placeholder(R.mipmap.ic_launcher)   // optional
                .error(R.drawable.banner)      // optional
                //.resize(500,300)                        // optional
                .into(imgV);
        mSetToolber();

    }

    private void inisilize() {
        tVtittel = (TextView) findViewById(R.id.tittel_tv);
        tVDetails = (TextView) findViewById(R.id.details_tv);
        imgV = (ImageView) findViewById(R.id.image_imgv);
    }


    private void mSetToolber() {
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        getSupportActionBar().setTitle(null);

        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // topToolBar.setLogo(R.drawable.logo);
        // topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        TextView mTitle = (TextView) topToolBar.findViewById(R.id.toolbar_title);
        ImageButton backArrow = (ImageButton) topToolBar.findViewById(R.id.back_arrow_Img_btn);

        mTitle.setText(toolberTitel);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
