package com.skh.peoplentech.peoplentech.UI_Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Config.MyFilePath;
import com.skh.peoplentech.peoplentech.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

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
        AllowRunTimePermission();

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
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfUploadFunction();
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //Upload File
    public void PdfUploadFunction() {


        applicantName = name.getText().toString().trim();
        applicantEmail = email.getText().toString().trim();
        applicantNumber = mobile.getText().toString().trim();
        applicationQuery = addQuery.getText().toString().trim();

        if (checkFileUpload) {

            upload.setClickable(true);
            upload.setFocusable(true);
            PdfPathHolder = MyFilePath.getPath(CVActivity.this, uri);
        }

        if (PdfPathHolder == null) {

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

        }
    }

    @Override
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
    }

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

        mTitle.setText("Submit Resume");

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
