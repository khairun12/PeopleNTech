package com.skh.peoplentech.peoplentech.UI_Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    //Creating a List of superheroes
    private List<Notice> latestOffers;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Activity mActivity;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        mSetToolber();
        mActivity = this;
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

            //mTv.setText("No Intrnet Connection");
            mTv.setVisibility(View.VISIBLE);

        }

        //adapter = new NoticeAdapter(latestOffers, this);

        //Adding adapter to recyclerview
      // recyclerView.setAdapter(adapter);
    }

    //This method will get data from the web api
    private void getData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ConfigKey.json_notic_URL1,
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
                        parseData(jsonArray);
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
/*

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
*/

        MySingleton.getInstance(mActivity).addToRequestQueue(jsonArrayRequest);
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
                course.setImage_file(ConfigKey.Notice_Image_DATA_URL + json.getString(ConfigKey.TAG_notice_image_file));


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

        mTitle.setText("Notice Board");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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