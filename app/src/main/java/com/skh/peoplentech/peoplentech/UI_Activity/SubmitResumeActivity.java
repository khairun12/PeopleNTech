package com.skh.peoplentech.peoplentech.UI_Activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import com.skh.peoplentech.peoplentech.Config.ConfigKey;
import com.skh.peoplentech.peoplentech.Config.DocumentsContract;
import com.skh.peoplentech.peoplentech.Config.FileChooser;
import com.skh.peoplentech.peoplentech.Config.FilePath;
import com.skh.peoplentech.peoplentech.Config.MySingleton;
import com.skh.peoplentech.peoplentech.Config.RealPathUtils;
import com.skh.peoplentech.peoplentech.Config.VolleyMultipartRequest;
import com.skh.peoplentech.peoplentech.Modle.CourseList;
import com.skh.peoplentech.peoplentech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SubmitResumeActivity extends AppCompatActivity {
    private TextView tvChosen_file, tVsuccessfull;
    private EditText eTFullName, eTEmail, eTMobielNum, eTQualification, eTCoverLetter;
    private Button bTChosen_file, bTSubmit;
    private boolean fileSelected = false;
    private boolean uploadedOne = false;

    /**********  File Path *************/
    //String uploadFilePath = "/mnt/sdcard/";
    String uploadFileName = "";
    private String uploadFilePathName = "";
    String allowFile[] = {".pdf", ".doc", ".docx"};


    private static final String File_TAG_NAME = "uploaded_file";
    private static final int PICK_FILE_REQUEST = 1;
    Activity mActivity;

    String successMessage = "Thank You.Your Registration is Successful.\nWe Will Contact with you shortly.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_resume);
        mActivity = this;
        mSetToolber();

        inisilize();

        //try {


            final FileChooser s = new FileChooser(this).setFileListener(new FileChooser.FileSelectedListener() {
                @Override
                public void fileSelected(File file) {
                    String extension = file.getName().substring(file.getName().lastIndexOf("."));
                    if (chackAllowFile(extension)) {
                        tvChosen_file.setText(file.getName());
                        tvChosen_file.setTextColor(Color.BLACK);
                        fileSelected = true;
                        uploadFileName = file.getName();
                        uploadFilePathName = file.getAbsolutePath();
                    } else {
                        Toast.makeText(mActivity, "Invalid File", Toast.LENGTH_LONG).show();
                        tvChosen_file.setText("Invalid File");
                        tvChosen_file.setTextColor(Color.RED);
                    }


                    Log.i("SKH", "getName:" + file.getName() + " getAbsolutePath:" + file.getAbsolutePath() + " extension: " + extension);
                }
            });


            bTChosen_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*File myFile = new File("");
                try {
                   // FileOpen.openFile(getApplicationContext(), myFile);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                showFileChooser();
              //  setTvChosen_file();
                //setTvChosen_file1();
               // new FileOpen(activity).onCreateDialog(1);
               // tvChosen_file.setText(uploadFilePath+""+uploadFileName);*/


                    s.showDialog();

                }
            });

/*

        } catch (Exception e) {
            logMessage("Error:" + e);
        }
*/


        bTSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allValidate()) {
                    saveResume();
                }


            }
        });


        eTFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (uploadedOne) {
                    tVsuccessfull.setText("");
                    tVsuccessfull.setVisibility(View.GONE);
                    uploadedOne = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private boolean chackAllowFile(String e) {
        for (String s : allowFile) {
            if (s.toLowerCase().equals(e.toLowerCase())) {
                return true;
            }
        }

        return false;
    }


    private void logMessage(String message) {
        Log.i("SKH", message);
    }

    private boolean allValidate() {


        if (eTFullName.getText().toString().length() == 0) {
            //Toast.makeText(getApplicationContext(), "Name cannot be Blank", Toast.LENGTH_LONG).show();
            eTFullName.setError("Name cannot be Blank");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(eTEmail.getText().toString()).matches()) {
            //Validation for Invalid Email Address
            Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
            eTEmail.setError("Invalid Email");
            return false;
        } else if (eTMobielNum.getText().toString().length() == 0) {
            //Toast.makeText(getApplicationContext(), "Name cannot be Blank", Toast.LENGTH_LONG).show();
            eTMobielNum.setError("Mobiel Number cannot be Blank");
            return false;
        } else if (eTQualification.getText().toString().length() == 0) {
            //Toast.makeText(getApplicationContext(), "Name cannot be Blank", Toast.LENGTH_LONG).show();
            eTQualification.setError("Qualification cannot be Blank");
            return false;
        } else if (eTCoverLetter.getText().toString().length() == 0) {
            //Toast.makeText(getApplicationContext(), "Name cannot be Blank", Toast.LENGTH_LONG).show();
            eTCoverLetter.setError("CoverLetter cannot be Blank");
            return false;
        } else if (!fileSelected) {
            //Toast.makeText(getApplicationContext(), "Name cannot be Blank", Toast.LENGTH_LONG).show();
            tvChosen_file.setText("Please file Chosen");
            tvChosen_file.setTextColor(Color.RED);
            return false;
        }


        return true;
    }

    private void saveResume() {
        // loading or check internet connection or something...
        // ... then

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, ConfigKey.Resume__URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);

                try {
                    String status = "";//= jsonArray.equals("status");
                    String message = "";// = result.getString("message");
                    Log.i("Messsage", resultResponse.toString());
                    JSONArray jsonArray = new JSONArray(resultResponse);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject json = null;
                        try {
                            json = jsonArray.getJSONObject(i);
                            status = json.getString("success");
                            message = json.getString("message");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                    Log.i("Messsage", resultResponse.toString());
                    if (status.equals("1")) {
                        tVsuccessfull.setText(successMessage);
                        tVsuccessfull.setVisibility(View.VISIBLE);

                        setAllFildEmpty();
                        uploadedOne = true;

                        // tell everybody you have succed upload image and post strings
                        Log.i("Messsage:", message);
                    } else {
                        Log.i("Messsage:", message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
//full_name`,`email`,`number`,`qualification`,`cover_letter`,`resume`

                String full_name = eTFullName.getText().toString().trim();
                String email = eTEmail.getText().toString().trim();
                String number = eTMobielNum.getText().toString().trim();
                String qualification = eTQualification.getText().toString().trim();
                String cover_letter = eTCoverLetter.getText().toString().trim();
                String resume = uploadFileName;


                params.put("full_name", full_name);
                params.put("email", email);
                params.put("number", number);
                params.put("qualification", qualification);
                params.put("cover_letter", cover_letter);
                params.put("resume", resume);

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from RadiusImageView
                params.put(File_TAG_NAME, new VolleyMultipartRequest.DataPart(uploadFileName, getByteFile(uploadFilePathName), "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));


                return params;
            }


        };

        MySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    private void setAllFildEmpty() {
        eTFullName.setText("");
        eTEmail.setText("");
        eTMobielNum.setText("");
        eTQualification.setText("");
        eTCoverLetter.setText("");

        tvChosen_file.setText(R.string.on_file_chosen);
        fileSelected = false;

    }


    public static byte[] getByteFile(String path) {
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }


    private void inisilize() {

        eTFullName = (EditText) findViewById(R.id.fullname_et);
        eTEmail = (EditText) findViewById(R.id.email_et);
        eTMobielNum = (EditText) findViewById(R.id.mobile_num_et);
        eTQualification = (EditText) findViewById(R.id.qualification_et);
        eTCoverLetter = (EditText) findViewById(R.id.cover_letter_et);

        tvChosen_file = (TextView) findViewById(R.id.file_chosen_tv);
        tVsuccessfull = (TextView) findViewById(R.id.successfull_tv);

        bTChosen_file = (Button) findViewById(R.id.file_chosen_btn);
        bTSubmit = (Button) findViewById(R.id.submit_btn);
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

        mTitle.setText("Submit Resume");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
