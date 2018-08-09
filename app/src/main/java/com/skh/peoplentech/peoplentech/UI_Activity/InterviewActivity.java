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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import java.util.List;

public class InterviewActivity extends AppCompatActivity {
    private String SelectAll = "Select All";
    private Activity mActivity;
    //Creating a List of superheroes
    private List<Interview> testimonialsList;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //private RecyclerView.Adapter adapter;
    private InterviewAdapter adapter;

    private TextView mTv;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        // setContentView(R.layout.activity_testimonials_list);
        // new MyAnimation(this).overridePendingTransitionEnter();
        mActivity = this;
        Intent intent = getIntent();
        name = intent.getStringExtra("KEY_StringName");
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


        /*if (ConfigKey.isNetworkAvailable(this)) {

            mTv.setVisibility(View.GONE);
        } else {

            mTv.setText("No Intrnet Connection");
            mTv.setVisibility(View.VISIBLE);

        }*/
    }

    //This method will get data from the web api
    private void getDataTestimonial() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ConfigKey.json_tv_interview_DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog
                        loading.dismiss();

                        String v = response + "";
                        v = v.replace("[[", "[");
                        Log.i("SS", "ab1:*" + v.toString() + "*");
                        v = v.replace("]]", "]");
                        Log.i("SS", "ab2:*" + v.toString() + "*");
                        //calling method to parse json array
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(v);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        parseDatatesTimonial(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("SS", "ERROR" + error.toString() + "*");
                        parseDataDemo();
                        //  parseData1();
                        loading.dismiss();
                    }
                });
//String d = "[{\"vid\":1,\"v_title\":\"PeopleNTech, Abubakar Hanip\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/y4g0vpQgGro\"},{\"vid\":3,\"v_title\":\"Report on Chief executive of People N Tech, Abu Bakar Hanip\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/9nEDIg2GaDk\"},{\"vid\":4,\"v_title\":\"Farhana Hanip, president of PeopleNTech\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/5bJ-s4dO0xA\"},{\"vid\":5,\"v_title\":\"Abu Hanip from People n Tech Interview Part 6\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/K2m-dkHmGWw\"},{\"vid\":6,\"v_title\":\"Abu Hanip from People n Tech Interview Part 5\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/ZR8Z2cXCv5w\"},{\"vid\":7,\"v_title\":\"Abu Hanip from People n Tech Interview Part 4\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/U4KxBL2WXEM\"},{\"vid\":8,\"v_title\":\"Abu Hanip from People n Tech Interview Part 3\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/DoCNUxFDjP4\"},{\"vid\":9,\"v_title\":\"Abu Hanip from People n Tech Interview Part 2\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/LYsxRoKgvkM\"},{\"vid\":10,\"v_title\":\"Abu Hanip from People n Tech Interview Part 1\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/xdwzzDOshDY\"},{\"vid\":11,\"v_title\":\"Shimana perie,Part - 5, ( Abu Bakar Hanip, People N Tech )\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/8fkuYuMBWOU\"},{\"vid\":12,\"v_title\":\"Shimana Perie,Part - 4, ( Abu Bakar Hanip, People N Tech )\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/6TTLQ1griuA\"},{\"vid\":13,\"v_title\":\"Shimana Perie,Part - 3, ( Abu bakar Hanip, People N Tech )\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/xFoaN8W1Pq8\"},{\"vid\":14,\"v_title\":\"Shimana Perie,Part - 2, ( Abu Bakar Hanip, People N Tech )\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/HrL-qwYLUjw\"},{\"vid\":15,\"v_title\":\"Shimana Perie, Part - 1, ( Abu Bakar Hanip, People n Tech )\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/UZrOOJiF7JY\"},{\"vid\":16,\"v_title\":\"Asian TV Investment Talk Show of PeopleNTech CEO, Mr. Abubokor Hanip \",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/N3sVNeDqONg\"},{\"vid\":17,\"v_title\":\"PeopleNTech Bangladesh - Talk Show CEO & Founder Abu Bokor Hanip (part-3)\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/L68TwzN-qD8\"},{\"vid\":18,\"v_title\":\"PeopleNTech Bangladesh -Live Talk Show of CEO & Founder Abu Bokor Hanip (part-2)\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/-oURfN5eb0I\"},{\"vid\":19,\"v_title\":\"PeopleNTech Bangladesh - Talk Show CEO & Founder Abu Bokor Hanip (part-1)\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/k-sfBhPrW38\"},{\"vid\":20,\"v_title\":\"Live Talk show on Cyber Security 03\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/ihmemtd6zUA\"},{\"vid\":21,\"v_title\":\"Live Talk show on Cyber Security 02\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/BaDuJAanTS8\"},{\"vid\":22,\"v_title\":\"Live Talk show on Cyber Security 01\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/pDiWiCx-rZ0\"},{\"vid\":23,\"v_title\":\"Live talk Show in ATN News Presented Engr. Abubokor Hanip\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/Oj8WDFbqb_w\"},{\"vid\":24,\"v_title\":\"Live talk show on Asian TV Presented Abubokor Hanip, Founder & CEO of PeopleNTech Part-02\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/bb8h4Gdvs1U\"},{\"vid\":25,\"v_title\":\"Live talk show on Asian TV Presented Abubokor Hanip, Founder & CEO of PeopleNTech Part -01\",\"video_link\":\"https:\\/\\/www.youtube.com\\/embed\\/oO1-pJlGK_o\"}]";

        MySingleton.getInstance(mActivity).addToRequestQueue(jsonArrayRequest);
    }

    //This method will parse json data
    private void parseData1() {
        Log.i("SS", "ab:* Demo data" + "*");
        for (int i = 0; i < 3; i++) {
            Interview course = new Interview();


            //  course.setC_name("Demo Data "+i);
            // course.setC_img("demo");

            testimonialsList.add(course);
        }

        //Finally initializing our adapter
        adapter = new InterviewAdapter(testimonialsList, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    private void parseDataDemo() {
        String v = getResources().getString(R.string.json_tv_interview);
      /*  /*//*v = v.replace("[[","[");
        Log.i("SS", "ab1:*" + v.toString() + "*");
        v = v.replace("]]", "]");
        Log.i("SS", "ab2:*" + v.toString() + "*");
        //calling method to parse json array*//**//**/
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(v);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Toast.makeText(getApplicationContext(), "No Intrnet Connection", Toast.LENGTH_LONG).show();
        parseDatatesTimonial(jsonArray);


    }

    //This method will parse json data
    private void parseDatatesTimonial(JSONArray array) {
        Log.i("SS", "ab:*" + array.toString() + "*");
        for (int i = 0; i < array.length(); i++) {
            Interview interview = new Interview();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                interview.setTAG_vid(json.getString(ConfigKey.TAG_vid));
                interview.setTAG_v_title(json.getString(ConfigKey.TAG_v_title));
                interview.setTAG_video_link(json.getString(ConfigKey.TAG_video_link));


                String d = interview.getTAG_video_link();//.replace("https:\\/\\/www.youtube.com\\/embed\\/","");
                d = d.replace("https://www.youtube.com/embed/", "");
                interview.setTAG_video_link(d);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            testimonialsList.add(interview);
        }


        //Finally initializing our adapter
        adapter = new InterviewAdapter(testimonialsList, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        showNotFoundMessage(testimonialsList);

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

    private void showNotFoundMessage(List latestOffers) {

        if (latestOffers.size() <= 0) {
            mTv.setText(R.string.not_found_text_message);
            mTv.setVisibility(View.VISIBLE);
        }

    }

}