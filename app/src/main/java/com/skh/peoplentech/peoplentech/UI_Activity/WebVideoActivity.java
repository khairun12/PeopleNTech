package com.skh.peoplentech.peoplentech.UI_Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.skh.peoplentech.peoplentech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebVideoActivity extends AppCompatActivity {
    private WebView wv;
    private String name;
    private String file_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        name = intent.getStringExtra("KEY_StringName");
        file_url = intent.getStringExtra("KEY_String_file_url");
        mSetToolber();

        wv = (WebView) findViewById(R.id.WebView01);

      /*  wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.facebook.com/peoplentech/");
        if(name.equals("Facebook Page")){
            wv.loadUrl("https://www.facebook.com/peoplentech/");
        }else {*/

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = "<br /><br />Read the handouts please for tomorrow.<br /><br /><!--homework help homework" +
                "help help with homework homework assignments elementary school high school middle school" +
                "// --><font color='#60c000' size='4'><strong>Please!</strong></font>" +
                "<img src='http://www.homeworknow.com/hwnow/upload/images/tn_star300.gif'  />";


        String t = "{\n" +
                "    \"products\":[{\"c_id\":1,\"type\":\"LongCourse\",\"c_name\":\"Software Testing with Quick Test Pro (QTP)\\/(UFT)\",\"c_s_details\":\"This Software Testing with QuickTestPro\\/Unified Functional Testing Automation course is an intensive hands-on course is designed to provide an introduction to the complexities of software testing. \",\"c_details\":\"\n" +
                "This Software Testing with QuickTestPro\\/Unified Functional Testing Automation course is an intensive hands-on course is designed to provide an introduction to the complexities of software testing with QuickTestPro\\/Unified Functional Testing Automationand delivers testing skills that participants can immediately apply back on the job. Using a real-world case study, students will encounter issues, decisions, and testing experiences comparable to those in real-world work environment. Working through a series of discussion-based exercises students will develop a workable strategy for performance testing an application\\/system. The focus of the exercises is on analysis of a situation and understanding the planning and design issues associated with QTP. This course will also focus on problem analysis, tuning, debugging, or tools. This course extends beyond the simple use of a tool to look at the nature of software testing, the challenges surrounding testing and how to overcome these challenges to make the performance testing more effective. This course focuses on what QTP\\/Unified Functional Testing Automation testing really is, how it should be carried out and how it should be managed.<\\/p>\\n\n" +
                "\n" +
                "<strong>Intended Audience:<\\/strong><\\/p>\\n\n" +
                "\n" +
                "This course is intended for those who want to work as Software Test Analyst\\/QA Analyst\\/ QuickTestPro or Unified Functional Testing Automation Engineer.<\\/p>\\n\n" +
                "\n" +
                "Prerequisites:<\\/strong><\\/p>\\n\n" +
                "\n" +
                "Students are required to have at least Associates degree along with a good knowledge of Microsoft Word, Excel, Access, PowerPoint, and Internet.<\\/p>\",\"c_fee\":\"\",\"c_fee_dicount\":null,\"c_img\":\"20160503181622_QTP.jpg\",\"brochure\":\"brochure.pdf\",\"meta_key\":\"\",\"meta_des\":\"\",\"cc\":\"Course Outline\",\"dates\":null,\"time\":null,\"shift\":null,\"venue\":\"Campus or Online\"}]}";


        String v = "<iframe width=\"847\" height=\"450\" src=\"https://www.youtube.com/embed/PujyMdjHweA\" frameborder=\"0\" allowfullscreen></iframe>";

        String videoLink = "https://www.youtube.com/embed/PujyMdjHweA";
        String html_text = "<style type=\"text/css\">\n" +
                ".video-container {\n" +
                "position: relative;\n" +
                "padding-bottom: 56.25%;\n" +
                "padding-top: 30px; height: 0; overflow: hidden;\n" +
                "}\n" +
                " \n" +
                ".video-container iframe,\n" +
                ".video-container object,\n" +
                ".video-container embed {\n" +
                "position: absolute;\n" +
                "top: 0;\n" +
                "left: 0;\n" +
                "width: 100%;\n" +
                "height: 100%;\n" +
                "}\n" +
                "</style>\n" +
                "<div class=\"video-container\"><iframe width=\"853\" height=\"480\" " +
                "src=\"" +
                file_url +
                "\" frameborder=\"0\" allowfullscreen</iframe</div>\n";


        String image_html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\" \"http://www.w3.org/TR/html40/loose.dtd\">\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Movie1</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n" +
                "    <meta name=\"author\" content=\"\">\n" +
                "    <meta name=\"generator\" content=\"SWiSHmax http://www.swishzone.com\">\n" +
                "    <meta name=\"description\" content=\"Movie1\">\n" +
                "    <!-- Created by SWiSHmax - Flash Made Easy - www.swishzone.com -->\n" +
                "  </head>\n" +
                "  <body bgcolor=\"#FFFFFF\">\n" +
                "    <center>\n" +
                "      <object\n" +
                "        classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\"\n" +
                "        codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,79,0\"\n" +
                "        id=\"Movie1\"\n" +
                "        width=\"1086\" height=\"194\"\n" +
                "      >\n" +
                "        <param name=\"movie\" value=\"Movie1.swf\">\n" +
                "        <param name=\"bgcolor\" value=\"#FFFFFF\">\n" +
                "        <param name=\"quality\" value=\"high\">\n" +
                "        <param name=\"allowscriptaccess\" value=\"samedomain\">\n" +
                "        <embed\n" +
                "          type=\"application/x-shockwave-flash\"\n" +
                "          pluginspage=\"http://www.macromedia.com/go/getflashplayer\"\n" +
                "          name=\"Movie1\"\n" +
                "          width=\"1086\" height=\"194\"\n" +
                "          src=\"Movie1.swf\"\n" +
                "          bgcolor=\"#FFFFFF\"\n" +
                "          quality=\"high\"\n" +
                "          swliveconnect=\"true\"\n" +
                "          allowscriptaccess=\"samedomain\"\n" +
                "        >\n" +
                "          <noembed>\n" +
                "          </noembed>\n" +
                "        </embed>\n" +
                "      </object>\n" +
                "    </center>\n" +
                "  </body>\n" +
                "</html>\n";
        //wv.loadDataWithBaseURL("", html, mimeType, encoding, "");
        // wv.loadDataWithBaseURL("", vvv(t), mimeType, encoding, "");
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wv.loadDataWithBaseURL("", html_text, mimeType, encoding, "");
        //  }


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

    private String vvv(String jsonStr) {


        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("products");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String id = c.getString("c_id");
                    String name = c.getString("type");
                    String email = c.getString("c_name");
                    String address = c.getString("c_details");
                    Log.i("sd", "Json parsing: " + address);
                    return address;

                }
            } catch (final JSONException e) {
                Log.e("sd", "Json parsing error: " + e.getMessage());


            }

        }
        return "test";
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        wv = null;
    }

    @Override
    public void onBackPressed() {


        finish();
    }
}
