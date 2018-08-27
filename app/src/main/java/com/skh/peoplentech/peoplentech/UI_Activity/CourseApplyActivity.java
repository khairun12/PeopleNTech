package com.skh.peoplentech.peoplentech.UI_Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Config.MySingleton;
import com.skh.peoplentech.peoplentech.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseApplyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String gender, title, courseId, smsBatchId, token;
    private Button apply, cancel;
    private TextView name, email, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_apply);

        mSetToolber();

        //Receive Bundle
        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            title = b.getString("name");
            courseId = b.getString("myid");
            Log.i("DEVKH", "Title:" + title);
            Log.i("DEVKH", "Course ID: " + courseId);
        }

        //Find View
        apply = (Button) findViewById(R.id.submit_course);
        cancel = (Button) findViewById(R.id.clear_course);
        name = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.userEmail);
        number = (TextView) findViewById(R.id.userMobile);

        //Post data for storing user info
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });

        //Cancel button to return to previous screen
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Load Spinner Data
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.mySpinner);
        // Spinner Drop down elements
        List<String> genderList = new ArrayList<String>();
        genderList.add("Male");
        genderList.add("Female");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        //Spinner Click
        spinner.setOnItemSelectedListener(this);

        //Now our app will get smsId of the selected course from API
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

        mTitle.setText("Batch Registration");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //Spinner selected

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        gender = parent.getItemAtPosition(position).toString().toLowerCase();
        //Toast.makeText(this, gender,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        gender = "male";
    }

    //Load data from API
    private void getData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

        //Creating a json object request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ConfigKey.APPS_URL + "batch/" + courseId,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        JSONObject batchId;
                        try {
                            JSONObject jObj = new JSONObject(response);
                            batchId = jObj.getJSONObject("batch");
                            smsBatchId = batchId.getString("smsBatchId");
                            Log.i("DEVKH", "SMS Batch Id: " + smsBatchId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

        MySingleton.getInstance(CourseApplyActivity.this).addToRequestQueue(stringRequest);

    }

    private void postData(){

        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

        //Check all fields
        final String usrName, usrEmail, usrNumber, usrGender;
        usrName = name.getText().toString();
        usrEmail = email.getText().toString();
        usrNumber = number.getText().toString();
        usrGender = gender;
        if (usrName.isEmpty() || usrEmail.isEmpty() || usrNumber.isEmpty() || usrGender.isEmpty()){
            loading.dismiss();
            Toast.makeText(CourseApplyActivity.this, "Please fill out all the details", Toast.LENGTH_SHORT).show();
        } else {

            //We need to get a CSRF token for sending data
            //This block of code will get us the CSRF token
            // Create a new HttpClient and Post Header

            /*Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        //background code here
                        HttpClient httpClient = new DefaultHttpClient();
                        // Get the CSRF token
                        try {
                            httpClient.execute(new HttpGet("https://www.piit.us/en/batch_registration/" + smsBatchId + "/" + title));
                            CookieStore cookieStore =((DefaultHttpClient)httpClient).getCookieStore();
                            List<Cookie> cookies =  cookieStore.getCookies();
                            for (Cookie cookie: cookies) {
                                Log.i("COOKIE", "My Token: " + cookie);
                                Log.i("COOKIE", "GET Value: " + cookie.getValue());
                                //token = cookie.getValue();
                                if (cookie.getName().equals("__cfduid")) {
                                    token = cookie.getValue();
                                }
                            }
                            Log.i("COOKIE", "My Var: " + token);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("IOERROR", "Get Error: " + e.toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //Log.i("DEVKH", "CSRF Number:" + token);
            thread.start();*/

            //Creating a json object request
            StringRequest postRequest = new StringRequest(Request.Method.POST, ConfigKey.BATCH_REGISTRATION,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            try {
                                JSONObject myResponse = new JSONObject(response);
                                String myCode = myResponse.getString("code");
                                switch (myCode) {
                                    case "100":
                                        Toast.makeText(CourseApplyActivity.this, "Your name is not valid", Toast.LENGTH_LONG).show();
                                        break;
                                    case "101":
                                        Toast.makeText(CourseApplyActivity.this, "Your Mobile Number is not valid", Toast.LENGTH_LONG).show();
                                        break;
                                    case "102":
                                        Toast.makeText(CourseApplyActivity.this, "Gender is not valid", Toast.LENGTH_LONG).show();
                                        break;
                                    case "200":
                                        Toast.makeText(CourseApplyActivity.this, "Your data has been successfully stored", Toast.LENGTH_LONG).show();
                                        break;
                                    case "103":
                                        Toast.makeText(CourseApplyActivity.this, "You are already registered as a student", Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        Toast.makeText(CourseApplyActivity.this, "Please fill up all required data", Toast.LENGTH_LONG).show();
                                        break;
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
                            // parseDataDemo();
                            loading.dismiss();
                            Toast.makeText(CourseApplyActivity.this, "Oops! Something went wrong", Toast.LENGTH_LONG).show();
                            Log.i("DEVKH", "Error: " + error.toString());
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("name", usrName);
                    params.put("email", usrEmail);
                    params.put("number", usrNumber);
                    params.put("gender", usrGender);
                    params.put("batch_id", smsBatchId);
                    Log.i("DEVKH", "Next Token :" + token);
                    Log.i("DEVKH", "Next Batch Id:" + smsBatchId);
                    return params;
                }
            };

            MySingleton.getInstance(CourseApplyActivity.this).addToRequestQueue(postRequest);
        }

    }

}
