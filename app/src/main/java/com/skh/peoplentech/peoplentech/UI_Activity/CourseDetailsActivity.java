package com.skh.peoplentech.peoplentech.UI_Activity;


import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.skh.peoplentech.peoplentech.Adapter.CourseListAdapter;
import com.skh.peoplentech.peoplentech.Adapter.ModulesAdapter;
import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Config.MySingleton;
import com.skh.peoplentech.peoplentech.Modle.CourseList;
import com.skh.peoplentech.peoplentech.Modle.Modules;
import com.skh.peoplentech.peoplentech.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CourseDetailsActivity extends AppCompatActivity {

    private String[] array;
    private String name, id, from;
    private String toolberTitel,URL;
    private boolean isCourse;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ModulesAdapter adapter;
    private List<Modules> moduleLists, upcomingLists;
    private ImageView teacherImg;

    TextView course_title,course_r_price,course_d_price, course_t_hour,course_s_date, course_l_date,
    course_techer_content,course_t_name, course_t_deg,course_dev_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        //inisialize();


        Bundle b = this.getIntent().getExtras();
        //array = b.getStringArray("WEB_A_ARRAY");
        name = b.getString("name");
        id = b.getString("url");
        from = b.getString("from");

        //Check if data is from Course or Upcoming list
        isCourse = from.equals("course/");

        toolberTitel = name;
        URL = ConfigKey.APPS_URL + from + id;
        mSetToolber();

        /**
         * DEVKH Starts Here
         */
        course_title = (TextView) findViewById(R.id.dev_tittle);
        course_r_price = (TextView) findViewById(R.id.dev_regular_price);
        course_d_price = (TextView) findViewById(R.id.dev_discount_price);
        course_t_hour = (TextView) findViewById(R.id.dev_total_hour);
        course_s_date = (TextView) findViewById(R.id.dev_start_date);
        course_l_date = (TextView) findViewById(R.id.dev_last_date);
        course_dev_content = (TextView) findViewById(R.id.dev_content);

        // teacher
        course_techer_content = (TextView) findViewById(R.id.dev_teacher_content);
        course_t_name = (TextView) findViewById(R.id.dev_teacher_name);
        course_t_deg = (TextView) findViewById(R.id.dev_teacher_deg);
        teacherImg = (ImageView) findViewById(R.id.imgInstructor);

        //Layout
        linearLayout = (LinearLayout) findViewById(R.id.otherLayout);

        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.devK_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        moduleLists = new ArrayList<>();
        upcomingLists = new ArrayList<>();
        getData();
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
                onBackPressed();
            }
        });
    }

    /**
     * DEVKH
     */

    private void getData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

        //Creating a json object request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismissing progress dialog
                        loading.dismiss();

                        //calling method to parse json array
                        JSONObject jsonObject;
                        JSONObject teacher;
                        JSONObject modules;
                        try {
                            //jsonArray = new JSONArray(v);
                            //Our Volley Code Starts here
                            //Check Course or Upcoming batch
                            if (isCourse) {

                                linearLayout.setVisibility(View.GONE);
                                JSONObject jObj = new JSONObject(response);
                                jsonObject = jObj.getJSONObject("course");
                                modules = jObj.getJSONObject("courseModules");

                                String body;

                                body = jsonObject.getString("content");
                                //new
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    body = String.valueOf(Html.fromHtml(body, Html.FROM_HTML_MODE_LEGACY));
                                } else {
                                    body = String.valueOf(Html.fromHtml(body));
                                }
                                //teacher = jObj.getJSONObject("teacher");

                                //set text here
                                //course details
                                course_title.setText(jsonObject.getString("heading"));
                                course_dev_content.setText(body);

                                //Recyclerview here
                                Iterator<String> iter = modules.keys();
                                while (iter.hasNext()){
                                    String key = iter.next();
                                    try {
                                        Modules myModules = new Modules();
                                        JSONObject insideObj = modules.getJSONObject(key);
                                        myModules.setModuleName(insideObj.getString("heading"));
                                        //Convert Html
                                        String modulebody;
                                        modulebody = insideObj.getString("content");
                                        //new
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                            modulebody = String.valueOf(Html.fromHtml(modulebody, Html.FROM_HTML_MODE_LEGACY));
                                        } else {
                                            modulebody = String.valueOf(Html.fromHtml(modulebody));
                                        }
                                        myModules.setModuleContent(modulebody);
                                        //insert it into the list
                                        moduleLists.add(myModules);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //Finally initializing our adapter
                                adapter = new ModulesAdapter(moduleLists, CourseDetailsActivity.this);
                                Log.i("DEVKH","courseLists Size:"+moduleLists.size());
                                //Adding adapter to recyclerview
                                recyclerView.setAdapter(adapter);

                            } else {

                                linearLayout.setVisibility(View.VISIBLE);
                                JSONObject jObj = new JSONObject(response);
                                jsonObject = jObj.getJSONObject("batch");
                                teacher = jObj.getJSONObject("teacher");
                                JSONObject course = jObj.getJSONObject("course");
                                modules = course.getJSONObject("modules");

                                String imageURL;
                                //set text here
                                //course details
                                course_title.setText(jsonObject.getString("heading"));
                                course_dev_content.setText(jsonObject.getString("content"));
                                course_r_price.setText(jsonObject.getString("regularPrice"));
                                course_d_price.setText(jsonObject.getString("discountPrice"));
                                course_t_hour.setText(jsonObject.getString("designation"));
                                course_s_date.setText(jsonObject.getString("startingDate"));
                                course_l_date.setText(jsonObject.getString("lastDateOfAdmission"));

                                course_techer_content.setText(teacher.getString("content"));
                                course_t_name.setText(teacher.getString("heading"));
                                course_t_deg.setText(teacher.getString("designation"));
                                imageURL = ConfigKey.IMAGE_URL_TEACHER + teacher.getString("file");

                                Picasso.with(CourseDetailsActivity.this)
                                        .load(imageURL)
                                        .placeholder(R.drawable.testperson)   // optional
                                        .error(R.drawable.testperson)      // optional
                                        //.resize(500,300)                        // optional
                                        .into(teacherImg);
                                //Toast.makeText(CourseDetailsActivity.this,"Des:"+jsonObject.getString("designation"),Toast.LENGTH_LONG).show();

                                /**
                                 * DEVKH continues
                                 * RecyclerView
                                 */

                                //Recyclerview here
                                Iterator<String> iter = modules.keys();
                                while (iter.hasNext()){
                                    String key = iter.next();
                                    try {
                                        Modules thisModules = new Modules();
                                        JSONObject insideObj = modules.getJSONObject(key);
                                        thisModules.setModuleName(insideObj.getString("heading"));
                                        //Convert Html
                                        String modulebody;
                                        modulebody = insideObj.getString("content");
                                        //new
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                            modulebody = String.valueOf(Html.fromHtml(modulebody, Html.FROM_HTML_MODE_LEGACY));
                                        } else {
                                            modulebody = String.valueOf(Html.fromHtml(modulebody));
                                        }
                                        thisModules.setModuleContent(modulebody);
                                        //insert it into the list
                                        upcomingLists.add(thisModules);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //Finally initializing our adapter
                                adapter = new ModulesAdapter(upcomingLists, CourseDetailsActivity.this);
                                Log.i("DEVKH","upcomingLists Size:"+upcomingLists.size());
                                //Adding adapter to recyclerview
                                recyclerView.setAdapter(adapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //assert jsonArray != null;
                        //parseData(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // parseData1();
                       // parseDataDemo();
                        loading.dismiss();
                    }
                });
/*

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
*/

        MySingleton.getInstance(CourseDetailsActivity.this).addToRequestQueue(stringRequest);
    }


    /*private void url_lod(){


        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                if(isFirstTime && progress < 100 ){

                    Pbar.setProgress(progress);

                }


                if(progress == 100) {
                    Pbar.setVisibility(ProgressBar.GONE);
                    isFirstTime = false;
                    // mySwipeRefreshLayout.setVisibility(View.GONE);
                }
            }




        });

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                // Handle the error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });

        wv.loadUrl(URL);
    }





    private void pageLode(){
        url_lod();

    }


    private void inisialize() {

      //  mySwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        //wv = (WebView) findViewById(R.id.wv);
        //mTv = (TextView) findViewById(R.id.not_found_Message_tv);

        //  relativeLayout = (RelativeLayout) findViewById(R.id.message_relativeLayout);

    }


    private void showNotFoundMessage() {


        // TextView mTv = (TextView) findViewById(R.id.not_found_Message_tv);

        mTv.setText("No Intrnet Connection");

        mTv.setVisibility(View.VISIBLE);


    }*/

    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {

            // Let the system handle the back button
            super.onBackPressed();
    }


}
