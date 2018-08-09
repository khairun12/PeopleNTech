package com.skh.peoplentech.peoplentech.UI_Activity;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.R;

import java.util.List;


public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        inisialize();


        Bundle b = this.getIntent().getExtras();
        array = b.getStringArray("WEB_A_ARRAY");

        toolberTitel = array[0];
        URL = array[1];
        mSetToolber();
        // APPS_URL = "https://www.facebook.com/peoplentech/";

        Pbar = (ProgressBar) findViewById(R.id.pB1);
        Pbar.setVisibility(ProgressBar.VISIBLE);

        if (toolberTitel.equals("About Us")) {
            pageLode();
            mTv.setVisibility(View.GONE);
        } else {
            if (ConfigKey.isNetworkAvailable(WebActivity.this)) {
                pageLode();
                mTv.setVisibility(View.GONE);
            } else {
                showNotFoundMessage();
                Pbar.setVisibility(ProgressBar.GONE);
            }

        }






       /* mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {

                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(true);
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                // wv.reload();
                              //  pageLode();
                                mySwipeRefreshLayout.setRefreshing(false);

                            }
                        }, 600);


                    }
                }
        );*/


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


    private void url_lod() {


        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                if (isFirstTime && progress < 100) {

                    Pbar.setProgress(progress);

                }


                if (progress == 100) {
                    Pbar.setVisibility(ProgressBar.GONE);
                    isFirstTime = false;
                    // mySwipeRefreshLayout.setVisibility(View.GONE);
                }
            }


        });

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wv.loadUrl(URL);
    }


    private void pageLode() {
        url_lod();
    }


    private void inisialize() {

        // mySwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        wv = (WebView) findViewById(R.id.wv);

        mTv = (TextView) findViewById(R.id.not_found_Message_tv);
        //  relativeLayout = (RelativeLayout) findViewById(R.id.message_relativeLayout);

    }


    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }


    private void showNotFoundMessage() {


        // TextView mTv = (TextView) findViewById(R.id.not_found_Message_tv);

        mTv.setText("No Intrnet Connection");

        mTv.setVisibility(View.VISIBLE);


    }

    private static final String LOG_NAME = WebActivity.class.getName();
    private WebView wv;
    private TextView mTv;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private Toolbar toolbar;

    private int l;
    private ProgressBar Pbar;
    private boolean isFirstTime = true;
    private String[] array;
    private String toolberTitel, URL;
    private RelativeLayout relativeLayout;

}
