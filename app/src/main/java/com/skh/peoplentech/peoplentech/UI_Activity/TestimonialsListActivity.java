package com.skh.peoplentech.peoplentech.UI_Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.skh.peoplentech.peoplentech.Adapter.TestimonialsAdapter;
import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Config.MyAnimation;
import com.skh.peoplentech.peoplentech.Config.MySingleton;
import com.skh.peoplentech.peoplentech.Modle.Modules;
import com.skh.peoplentech.peoplentech.Modle.Testimonials;
import com.skh.peoplentech.peoplentech.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestimonialsListActivity extends AppCompatActivity {
    private String SelectAll = "Select All";

    //Creating a List of superheroes
    private ArrayList<Testimonials> testimonialsList;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //private RecyclerView.Adapter adapter;
    private TestimonialsAdapter adapter;

    private List<String> languageList = new ArrayList<>();
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        // setContentView(R.layout.activity_testimonials_list);
       // new MyAnimation(this).overridePendingTransitionEnter();
        Intent intent = getIntent();
        name = intent.getStringExtra("activity");
        mSetToolber();
        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        testimonialsList = new ArrayList<>();

        //Calling method to get data
        //getDataTestimonial(ConfigKey.USA_ENG_TESTIMONIAL_DATA_URL + "?page=1");
        if (name.equals("bangla")){
            getDataTestimonial(ConfigKey.BANGLADESH_TESTIMONIAL_DATA_URL);
        }
    }

    //This method will get data from the web api
    private void getDataTestimonial(final String myURL) {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, true);

        if (name.equals("usa")){

            //Check if parent activity is USA
            //Creating a json array request

            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest (Request.Method.GET, myURL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Dismissing progress dialog
                            //loading.dismiss();
                            //JSONObject fullData;
                            try {
                                //fullData = new JSONObject(response);
                                JSONObject videoList = response.getJSONObject("announcementList");

                                JSONObject paginate = videoList.getJSONObject("paginate");
                                int lastPage = paginate.getInt("lastPage");
                                if (lastPage < 1) {
                                    lastPage = 1;
                                }
                                for (int i =1; i<=lastPage; i++){
                                    JsonObjectRequest finalRequest = new JsonObjectRequest(Request.Method.GET, myURL + "?page=" + i, null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject newResponse) {

                                                    loading.dismiss();
                                                    //JSONObject newFullObj;

                                                    try {
                                                        //newFullObj = new JSONObject(newResponse);
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
                                                        adapter = new TestimonialsAdapter(testimonialsList, TestimonialsListActivity.this);
                                                        Log.i("DEVKH", "TestimonialLists Size:" + testimonialsList.size());
                                                        //Adding adapter to recyclerview
                                                        recyclerView.setAdapter(adapter);
                                                        //check list
                                                        if (testimonialsList.size() < 1){
                                                            Toast.makeText(TestimonialsListActivity.this, "No Video Found", Toast.LENGTH_SHORT).show();
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
                                                }
                                            });
                                    MySingleton.getInstance(TestimonialsListActivity.this).addToRequestQueue(finalRequest);
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
                            Toast.makeText(TestimonialsListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });

            MySingleton.getInstance(TestimonialsListActivity.this).addToRequestQueue(jsonArrayRequest);

        } else if (name.equals("bangla")){
            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest (Request.Method.GET, myURL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Dismissing progress dialog
                            //loading.dismiss();
                            //JSONObject fullData;
                            try {
                                //fullData = new JSONObject(response);
                                JSONObject videoList = response.getJSONObject("announcementList");

                                JSONObject paginate = videoList.getJSONObject("paginate");
                                int lastPage = paginate.getInt("lastPage");
                                if (lastPage < 1) {
                                    lastPage = 1;
                                }
                                for (int i =1; i<=lastPage; i++){
                                    JsonObjectRequest finalRequest = new JsonObjectRequest(Request.Method.GET, myURL + "?page=" + i, null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject newResponse) {

                                                    loading.dismiss();
                                                    //JSONObject newFullObj;

                                                    try {
                                                        //newFullObj = new JSONObject(newResponse);
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
                                                        adapter = new TestimonialsAdapter(testimonialsList, TestimonialsListActivity.this);
                                                        Log.i("DEVKH", "TestimonialLists Size:" + testimonialsList.size());
                                                        //Adding adapter to recyclerview
                                                        recyclerView.setAdapter(adapter);
                                                        //check list
                                                        if (testimonialsList.size() < 1){
                                                            Toast.makeText(TestimonialsListActivity.this, "No Video Found", Toast.LENGTH_SHORT).show();
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
                                                }
                                            });
                                    MySingleton.getInstance(TestimonialsListActivity.this).addToRequestQueue(finalRequest);
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
                            Toast.makeText(TestimonialsListActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });

            MySingleton.getInstance(TestimonialsListActivity.this).addToRequestQueue(jsonArrayRequest);
        }
    }

    //This method will parse json data
    private void parseData1() {
        Log.i("SS", "ab:* Demo data" + "*");
        for (int i = 0; i < 3; i++) {
            Testimonials course = new Testimonials();


            //  course.setC_name("Demo Data "+i);
            // course.setC_img("demo");

            testimonialsList.add(course);
        }

        //Finally initializing our adapter
        adapter = new TestimonialsAdapter(testimonialsList, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    private void parseDataDemo() {
        String v = getResources().getString(R.string.json_testimonials);
        //*v = v.replace("[[","[");
        Log.i("SS", "ab1:*" + v.toString() + "*");
        v = v.replace("]]", "]");
        Log.i("SS", "ab2:*" + v.toString() + "*");
        //calling method to parse json array*//*
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(v);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        //parseDatatesTimonial(jsonArray);


    }

    //This method will parse json data
    /*private void parseDatatesTimonial(JSONArray array) {
        Log.i("SS", "ab:*" + array.toString() + "*");
        for (int i = 0; i < array.length(); i++) {
            Testimonials testimonials = new Testimonials();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                testimonials.setUser_name(json.getString(ConfigKey.TAG_user_name));
                testimonials.setEmail(json.getString(ConfigKey.TAG_email));
                testimonials.setLanguage(json.getString(ConfigKey.TAG_language));
                testimonials.setVideo_file(json.getString(ConfigKey.TAG_video_file));
                String d = testimonials.getVideo_file();//.replace("https:\\/\\/www.youtube.com\\/embed\\/","");
                d = d.replace("https://www.youtube.com/embed/", "");
                testimonials.setVideo_file(d);

                if (!languageList.contains(testimonials.getLanguage())) {
                    languageList.add(testimonials.getLanguage());
                }
                //languageList.add();


            } catch (JSONException e) {
                e.printStackTrace();
            }


            testimonialsList.add(testimonials);
        }

        languageList.add(0, SelectAll);

        //Finally initializing our adapter
        adapter = new TestimonialsAdapter(testimonialsList, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, languageList);

// Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// attaching data adapter to spinner
        spinner.setAdapter(spinnerAdapter);
    }*/


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

        if (name.equals("usa")){
            mTitle.setText("USA Video Testimonials");
        } else {
            mTitle.setText("Bangladesh Video Testimonials");
        }


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    ArrayAdapter<String> spinnerAdapter;
    Spinner spinner;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (name.equals("usa")) {

            getMenuInflater().inflate(R.menu.testimonial_menu, menu);

            MenuItem item = menu.findItem(R.id.spinner);
            spinner = (Spinner) MenuItemCompat.getActionView(item);

            //Add static data to list
            languageList.add("English");
            languageList.add("Bengali");
            languageList.add("Arabic");
            languageList.add("Russian");
            languageList.add("Nepali");

            String[] NUMBERS = new String[languageList.size()];
            int i = 0;

            for (String s : languageList) {
                NUMBERS[i] = s;
                Log.i("SKH", NUMBERS[i]);
                i++;
            }
            Log.i("SKH", " s " + languageList.size());
            //   String [] NUMBERS= languageList.toArray();


            spinnerAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, NUMBERS);

// Specify the layout to use when the list of choices appears
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// attaching data adapter to spinner
            spinner.setAdapter(spinnerAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    testimonialsList.clear();
                    String selectedItem = parent.getItemAtPosition(position).toString();

                    //New
                    switch (selectedItem) {
                        case "English":
                            getDataTestimonial(ConfigKey.USA_ENG_TESTIMONIAL_DATA_URL);
                            break;
                        case "Bengali":
                            getDataTestimonial(ConfigKey.USA_BAN_TESTIMONIAL_DATA_URL);
                            break;
                        case "Arabic":
                            getDataTestimonial(ConfigKey.USA_ARB_TESTIMONIAL_DATA_URL);
                            break;
                        case "Russian":
                            getDataTestimonial(ConfigKey.USA_RUS_TESTIMONIAL_DATA_URL);

                            break;
                        case "Nepali":
                            getDataTestimonial(ConfigKey.USA_NEP_TESTIMONIAL_DATA_URL);
                            break;
                    }

                /*Log.i("SKH", selectedItem);
                if (SelectAll.equals(selectedItem)) {
                    selectedItem = "";
                }
                filter(testimonialsList, selectedItem);

                adapter.setFilter(filter(testimonialsList, selectedItem));
                if (selectedItem.equals("Add new category")) {
                    // do your stuff
                }*/
                } // to close the onItemSelected

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            return true;
        } else {
            return false;
        }

    }

    /*ArrayList<Testimonials> filter(List<Testimonials> models, String query) {
        query = query.toLowerCase();
        final ArrayList<Testimonials> filteredModelList = new ArrayList<>();
        for (Testimonials model : models) {
            final String text = model.getLanguage().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }*/


}