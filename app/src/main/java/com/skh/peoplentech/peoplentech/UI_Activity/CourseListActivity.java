package com.skh.peoplentech.peoplentech.UI_Activity;

import android.app.ProgressDialog;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.skh.peoplentech.peoplentech.Adapter.CourseListAdapter;
import com.skh.peoplentech.peoplentech.Adapter.CourseListAdapter1;
import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Config.MySingleton;
import com.skh.peoplentech.peoplentech.Modle.CourseData;
import com.skh.peoplentech.peoplentech.Modle.CourseList;
import com.skh.peoplentech.peoplentech.Modle.CourseM;
import com.skh.peoplentech.peoplentech.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //Creating a List
    private List<CourseList> courseLists;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CourseListAdapter adapter;

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

        //Initializing our list
        courseLists = new ArrayList<>();

        //Calling method to get data
         getData();

    }

    //This method will get data from the web api
    private void getData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, true);

        //Creating a json array request
        /**
         * DEVKH
         * **/
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ConfigKey.json_course_list_DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismissing progress dialog
                        loading.dismiss();
                        JSONArray jsonArray = null;
                        try {
                            //jsonArray = new JSONArray(v);
                            //Our Volley Code Starts here
                            JSONObject jObj = new JSONObject(response);
                            jsonArray = jObj.getJSONArray("courses");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        parseData(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                    }
                });

        MySingleton.getInstance(CourseListActivity.this).addToRequestQueue(stringRequest);
    }

    /**
     * DEVKH
     * @param array
     */
    //This method will parse json data
    private void parseData(JSONArray array) {
        Log.i("SS", "myarray:*" + array.toString() + "*");
        for (int i = 0; i < array.length(); i++) {
            CourseList course = new CourseList();
            JSONObject json;
            String body;
            try {
                json = array.getJSONObject(i);
                course.setC_id(json.getString(ConfigKey.TAG_c_id));
                body = json.getString(ConfigKey.TAG_c_name);
                //new
                /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    body = String.valueOf(Html.fromHtml(body, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    body = String.valueOf(Html.fromHtml(body));
                }*/
                course.setC_name(body);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            courseLists.add(course);
        }

        //Finally initializing our adapter
        adapter = new CourseListAdapter(courseLists, this);
        Log.i("DEVKH","courseLists Size:"+courseLists.size());
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        if (courseLists.size() < 1) {
            Toast.makeText(CourseListActivity.this, "No Course Found. Check Your Internet Connection", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        final List<CourseList> filteredModelList = filter(courseLists, newText);
        /**
         * Test
         */

        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.course_list_memu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            searchView.setOnQueryTextListener(this);
            /**
             * Test
             */
            //adapter.setFilter(courseLists);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


    private List<CourseList> filter(List<CourseList> models, String query) {
        query = query.toLowerCase();

        //Log.i("DEVKH",query +" models Size:"+models.size());
        final List<CourseList> filteredModelList = new ArrayList<>();
        for (CourseList model : models) {
            final String text = model.getC_name().toLowerCase();
            //Log.i("DEVKH",text);
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }

        //Log.i("DEVKH","Size:"+filteredModelList.size());
        return filteredModelList;
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

        mTitle.setText("Courses");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}