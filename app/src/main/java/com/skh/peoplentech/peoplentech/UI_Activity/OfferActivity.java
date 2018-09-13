package com.skh.peoplentech.peoplentech.UI_Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.JsonObjectRequest;
import com.skh.peoplentech.peoplentech.Adapter.LatestOfferAdapter;
import com.skh.peoplentech.peoplentech.Adapter.NoticeAdapter;
import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Config.MySingleton;
import com.skh.peoplentech.peoplentech.Modle.LatestOffer;
import com.skh.peoplentech.peoplentech.Modle.Notice;
import com.skh.peoplentech.peoplentech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OfferActivity extends AppCompatActivity {
    //Creating a List of superheroes
    private List<Notice> latestOffers;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NoticeAdapter adapter;
    //private Activity mActivity;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        mSetToolber();
        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        latestOffers = new ArrayList<>();

        //Calling method to get data


        mTv = (TextView) findViewById(R.id.not_found_Message_tv);

        if (ConfigKey.isNetworkAvailable(this)) {
            getData();
            mTv.setVisibility(View.GONE);
        } else {

            mTv.setText("No Internet Connection");
            mTv.setVisibility(View.VISIBLE);

        }


    }

    //This method will get data from the web api
    private void getData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, true);

        //Creating a json array request
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(ConfigKey.json_notic_URL1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Dismissing progress dialog
                        //loading.dismiss();

                        /*String v = response + "";
                        v = v.replace("[[", "[");
                        Log.i("SS", "ab1:*" + v.toString() + "*");
                        v = v.replace("]]", "]");
                        Log.i("SS", "ab2:*" + v.toString() + "*");
                        //calling method to parse json array
                        JSONArray jsonArray = null;*/
                        try {
                            JSONObject videoList = response.getJSONObject("announcementList");

                            JSONObject paginate = videoList.getJSONObject("paginate");
                            int lastPage = paginate.getInt("lastPage");
                            if (lastPage < 1) {
                                lastPage = 1;
                            }
                            for (int i =1; i<=lastPage; i++){
                                JsonObjectRequest finalRequest = new JsonObjectRequest(Request.Method.GET, ConfigKey.json_latest_offer_DATA_URL + "?page=" + i, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject newResponse) {

                                                //loading.dismiss();

                                                try {
                                                    JSONObject newVidList = newResponse.getJSONObject("announcementList");

                                                    //Get List of Data
                                                    Iterator<String> iter = newVidList.keys();
                                                    while (iter.hasNext()) {
                                                        String key = iter.next();
                                                        String videoId;
                                                        if (!key.equals("paginate")) {
                                                            try {
                                                                Notice myData = new Notice();
                                                                JSONObject insideObj = newVidList.getJSONObject(key);
                                                                myData.setNotice_title(insideObj.getString("heading"));
                                                                //myData.setNotice_dt(insideObj.getString("content"));
                                                                //Convert Html
                                                                String modulebody;
                                                                modulebody = insideObj.getString("content");
                                                                //new
                                                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                                                    modulebody = String.valueOf(Html.fromHtml(modulebody, Html.FROM_HTML_MODE_LEGACY));
                                                                } else {
                                                                    modulebody = String.valueOf(Html.fromHtml(modulebody));
                                                                }
                                                                myData.setNotice_dt(modulebody);
                                                                //videoId = insideObj.getString("url_address").replace("https://www.youtube.com/embed/", "").trim();
                                                                //myData.setVideoId(videoId);
                                                                latestOffers.add(myData);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                                loading.dismiss();
                                                            }
                                                        }
                                                    }
                                                    //Finally initializing our adapter
                                                    adapter = new NoticeAdapter(latestOffers, OfferActivity.this);
                                                    //Log.i("DEVKH", "TestimonialLists Size:" + testimonialsList.size());
                                                    //Adding adapter to recyclerview
                                                    recyclerView.setAdapter(adapter);
                                                    loading.dismiss();
                                                    //check list
                                                    if (latestOffers.size() < 1){
                                                        Toast.makeText(OfferActivity.this, "No Offer Found", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                loading.dismiss();
                                            }
                                        });
                                MySingleton.getInstance(OfferActivity.this).addToRequestQueue(finalRequest);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // parseData1();
                        //  parseDataDemo();
                        loading.dismiss();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }



    private void parseDataDemo() {
        String v = getResources().getString(R.string.json_course_list);
        /*v = v.replace("[[","[");
        Log.i("SS","ab1:*"+v.toString()+"*");
        v = v.replace("]]","]");
        Log.i("SS","ab2:*"+v.toString()+"*");
        //calling method to parse json array*/
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(v);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        parseData(jsonArray);


    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        Log.i("SS", "ab:*" + array.toString() + "*");
        for (int i = 0; i < array.length(); i++) {
            Notice course = new Notice();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                course.setId(json.getString(ConfigKey.TAG_Notice_ID));
                course.setNotice_title(json.getString(ConfigKey.TAG_notice_title));
                course.setNotice_dt(json.getString(ConfigKey.TAG_notice_dt));
                //course.setImage_file(ConfigKey.Notice_Image_DATA_URL + json.getString(ConfigKey.TAG_notice_image_file));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            latestOffers.add(course);
        }

        //Finally initializing our adapter
        adapter = new NoticeAdapter(latestOffers, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        showNotFoundMessage(latestOffers);

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

        mTitle.setText("Special Offer");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showNotFoundMessage(List latestOffers){

        if (latestOffers.size() <= 0){
            TextView mTv = (TextView) findViewById(R.id.not_found_Message_tv);
            mTv.setVisibility(View.VISIBLE);
        }

    }
}