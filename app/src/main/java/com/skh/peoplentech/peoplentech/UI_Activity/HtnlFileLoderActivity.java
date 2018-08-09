package com.skh.peoplentech.peoplentech.UI_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.skh.peoplentech.peoplentech.R;


public class HtnlFileLoderActivity extends AppCompatActivity {
    private WebView wvAboutUs;
    String name = "";
    String file_url = "";

    @SuppressLint("Recycle")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htnl_file_loder);
        Intent intent = getIntent();
        name = intent.getStringExtra("KEY_StringName");
        file_url = intent.getStringExtra("KEY_String_file_url");
        mSetToolber();
        wvAboutUs = (WebView) findViewById(R.id.wvAboutUs);
        wvAboutUs.getSettings().setBuiltInZoomControls(true);
//"file:///android_asset/contact_us.html"
        // uri loding in webview
        wvAboutUs.loadUrl(file_url);

        // url loding in webview
        //wvAboutUs.loadUrl("http://androidlift.info/");
        wvAboutUs.clearCache(true);
        wvAboutUs.clearHistory();
        wvAboutUs.getSettings().setJavaScriptEnabled(true);
        wvAboutUs.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
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

        mTitle.setText(name);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}