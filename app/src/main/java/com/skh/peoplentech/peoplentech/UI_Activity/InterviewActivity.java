package com.skh.peoplentech.peoplentech.UI_Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.skh.peoplentech.peoplentech.Adapter.InterviewAdapter;
import com.skh.peoplentech.peoplentech.Adapter.TestimonialsAdapter;
import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Config.MyAnimation;
import com.skh.peoplentech.peoplentech.Config.MySingleton;
import com.skh.peoplentech.peoplentech.Modle.Interview;
import com.skh.peoplentech.peoplentech.Modle.Testimonials;
import com.skh.peoplentech.peoplentech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InterviewActivity extends AppCompatActivity {

    //private Activity mActivity;
    //Creating a List
    private ArrayList<Testimonials> testimonialsList;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //private RecyclerView.Adapter adapter;
    private TestimonialsAdapter adapter;

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        // setContentView(R.layout.activity_testimonials_list);
        // new MyAnimation(this).overridePendingTransitionEnter();
        //Intent intent = getIntent();
        //name = intent.getStringExtra("KEY_StringName");
        mSetToolber();
        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mTv = (TextView) findViewById(R.id.not_found_Message_tv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        testimonialsList = new ArrayList<>();

        //Calling method to get data
        getDataTestimonial();


    }

    //This method will get data from the web api
    private void getDataTestimonial() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, true);

            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest (Request.Method.GET, ConfigKey.TV_INTERVIEW_DATA_URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject videoList = response.getJSONObject("announcementList");

                                JSONObject paginate = videoList.getJSONObject("paginate");
                                int lastPage = paginate.getInt("lastPage");
                                if (lastPage < 1) {
                                    lastPage = 1;
                                }
                                for (int i =1; i<=lastPage; i++){
                                    JsonObjectRequest finalRequest = new JsonObjectRequest(Request.Method.GET, ConfigKey.TV_INTERVIEW_DATA_URL + "?page=" + i, null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject newResponse) {

                                                    loading.dismiss();

                                                    try {
                                                        JSONObject newVidList = newResponse.getJSONObject("announcementList");

                                                        //Get List of Data
                                                        Iterator<String> iter = newVidList.keys();
                                                        while (iter.hasNext()) {
                                                            String key = iter.next();
                                                            String videoId;
                                                            if (!key.equals("paginate")) {
                                                                try {
                                                                    Testimonials myData = new Testimonials();
                                                                    JSONObject insideObj = newVidList.getJSONObject(key);
                                                                    myData.setUser_name(insideObj.getString("heading"));
                                                                    myData.setVideo_file(insideObj.getString("url_address"));
                                                                    videoId = insideObj.getString("url_address").replace("https://www.youtube.com/embed/", "").trim();
                                                                    myData.setVideoId(videoId);
                                                                    testimonialsList.add(myData);
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                    loading.dismiss();
                                                                }
                                                            }
                                                        }
                                                        //Finally initializing our adapter
                                                        adapter = new TestimonialsAdapter(testimonialsList, InterviewActivity.this);
                                                        Log.i("DEVKH", "TestimonialLists Size:" + testimonialsList.size());
                                                        //Adding adapter to recyclerview
                                                        recyclerView.setAdapter(adapter);
                                                        //check list
                                                        if (testimonialsList.size() < 1){
                                                            Toast.makeText(InterviewActivity.this, "No Video Found", Toast.LENGTH_SHORT).show();
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
                                    MySingleton.getInstance(InterviewActivity.this).addToRequestQueue(finalRequest);
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
                            //parseDataDemo();
                            loading.dismiss();
                            Toast.makeText(InterviewActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });

            MySingleton.getInstance(InterviewActivity.this).addToRequestQueue(jsonArrayRequest);

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

        mTitle.setText("TV Interview");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void showNotFoundMessage(List latestOffers) {

        if (latestOffers.size() <= 0) {
            mTv.setText(R.string.not_found_text_message);
            mTv.setVisibility(View.VISIBLE);
        }

    }

}