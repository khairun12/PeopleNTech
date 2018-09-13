package com.skh.peoplentech.peoplentech.UI_Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Config.MyFilePath;
import com.skh.peoplentech.peoplentech.Config.MySingleton;
import com.skh.peoplentech.peoplentech.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import retrofit.http.POST;

public class CVActivity extends AppCompatActivity {

    EditText name, email, mobile, addQuery;
    Button choose, upload;
    Spinner spinner;

    public int PDF_REQ_CODE = 1;

    private Uri uri;

    boolean checkFileUpload;

    String PdfPathHolder, applicantName, PdfID, applicantEmail, applicantNumber, spinnerValue, applicationQuery ;

    int ijk = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);

        mSetToolber();

        name = (EditText) findViewById(R.id.cv_name);
        addQuery = (EditText) findViewById(R.id.queryText);
        email = (EditText) findViewById(R.id.cv_email);
        mobile = (EditText) findViewById(R.id.cv_mobile);
        choose = (Button) findViewById(R.id.btn_choose);
        upload = (Button) findViewById(R.id.btn_submit);
        spinner =(Spinner) findViewById(R.id.typeSpinner);

        //Check permission
        //AllowRunTimePermission();

        //Add data to spinner
        // Spinner Drop down elements
        ArrayList<String> areas = new ArrayList<String>();
        areas.add("--Select Branch--");
        areas.add("USA");
        areas.add("Bangladesh");

        //Select PDF
        /**
         * postponed CV activity
         * work on later
         *
         */
        /*choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);
            }
        });*/

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigKey.isNetworkAvailable(CVActivity.this)) {
                    consultationFunction();
                } else {
                    Toast.makeText(CVActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        /**
         * finished cv activity
         */

        //Spinner Adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(CVActivity.this, R.layout.my_spinner, areas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //on click
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String item = parent.getItemAtPosition(position).toString();
                if (item.equals("USA")){
                    spinnerValue = "1";
                } else if (item.equals("Bangladesh")){
                    spinnerValue = "2";
                } else {
                    spinnerValue = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //Upload File
    public void consultationFunction() {


        applicantName = name.getText().toString().trim();
        applicantEmail = email.getText().toString().trim();
        applicantNumber = mobile.getText().toString().trim();
        applicationQuery = addQuery.getText().toString().trim();

        if (applicantNumber.isEmpty() || applicantEmail.isEmpty() || applicantName.isEmpty() || applicationQuery.isEmpty()
                || TextUtils.isEmpty(spinnerValue)){
            Toast.makeText(this, "Please fill up all required fields", Toast.LENGTH_LONG).show();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(applicantEmail).matches()){
            Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_LONG).show();
        } else {

            //Showing a progress dialog
            final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, true);

            //Creating a json array request
            StringRequest jsonRequest = new StringRequest(Request.Method.POST, ConfigKey.FREE_CONSULTATION,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            try{
                                JSONObject myResponse = new JSONObject(response);
                                int code = myResponse.getInt("code");

                                if (code == 200){
                                    Toast.makeText(CVActivity.this, "Your query has been successfully received", Toast.LENGTH_SHORT).show();
                                } else if (code == 405){
                                    Toast.makeText(CVActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CVActivity.this, "Please enter valid email and phone number", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(CVActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("name", applicantName);
                    params.put("email", applicantEmail);
                    params.put("phone", applicantNumber);
                    params.put("branch", spinnerValue);
                    params.put("querys", applicationQuery);
                    //Log.i("DEVKH", "Next Token :" + token);
                    return params;
                }
            };
            MySingleton.getInstance(CVActivity.this).addToRequestQueue(jsonRequest);
        }

        /*if (checkFileUpload) {

            upload.setClickable(true);
            upload.setFocusable(true);
            PdfPathHolder = MyFilePath.getPath(CVActivity.this, uri);
        }*/

        /**
         * function for uploading PDF
         */

        /*if (PdfPathHolder == null) {

            Toast.makeText(this, "Please Select a File & try again.", Toast.LENGTH_LONG).show();

        } else {

            try {

                PdfID = UUID.randomUUID().toString();

                new MultipartUploadRequest(CVActivity.this, PdfID, ConfigKey.APPS_URL + "/case-file-store/")
                        .addFileToUpload(PdfPathHolder, "cv")
                        .addParameter("branch", spinnerValue)
                        .addParameter("name", applicantName)
                        .addParameter("email", applicantEmail)
                        .addParameter("phone", applicantNumber)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload();

                ijk++;

            } catch (Exception exception) {

                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }

            if (ijk > 1) {

                Toast.makeText(this, "File Uploading in Process", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(AddPdfActivity.this,PdfShowActivity.class);
                //startActivity(intent);
            }

        }*/
    }

    /**
     * Required for PDF upload
     *
     */

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            //selectFileText.setText("Your PDF file is Selected");
            checkFileUpload = true;

            //get name of file
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    Objects.requireNonNull(cursor).close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }
            choose.setText(displayName);
        }
    }*/

    //Setup tool bar

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

        mTitle.setText("Free Consultation");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void AllowRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(CVActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            Toast.makeText(CVActivity.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(CVActivity.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, PDF_REQ_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    //Toast.makeText(AddPdfActivity.this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(CVActivity.this,"Permission Cancelled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
