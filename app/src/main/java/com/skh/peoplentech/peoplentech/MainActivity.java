package com.skh.peoplentech.peoplentech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skh.peoplentech.peoplentech.Config.ConfigKey;

import com.skh.peoplentech.peoplentech.Config.HTML_FILE_LODER;
import com.skh.peoplentech.peoplentech.Config.MyAnimation;
import com.skh.peoplentech.peoplentech.UI_Activity.*;

public class MainActivity extends AppCompatActivity {
    private Activity mActivity;
    private Button b1, b2, b3, b4, b5, b6;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Intent intent;

    private String[] WEB_A_ARRAY = new String[3];

    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mActivity = this;
        initialize();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("PeopleNTech");

        initNavigationDrawer();
        //((Animatable) b2.getCompoundDrawables()[1]).isRunning();
/*runOnUiThread(new Runnable() {
    @Override
    public void run() {
        while (true){
            ((Animatable) b2.getCompoundDrawables()[1]).start();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
});*/

       /* AnimationDrawable d =(AnimationDrawable)b2.getCompoundDrawables()[2];
        d.start();*/

/*
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in);

        b2.setAnimation(animation);
        animation.start();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out);
                b2.startAnimation(fadeOut);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });*/

        getDesplayHightWidth();

    }

    private void initialize() {
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        b1.setOnClickListener(click);
        b2.setOnClickListener(click);
        b3.setOnClickListener(click);
        b4.setOnClickListener(click);
        //b1.getHeight();

    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:

                    startActivity(new Intent(MainActivity.this, TraningActivity.class));

                    break;
                case R.id.button2:
                    WEB_A_ARRAY[0] = "PeopleNTech Software";
                    WEB_A_ARRAY[1] = ConfigKey.URL_SOFTWARE;
                    HTML_FILE_LODER.goWebpage(mActivity, WEB_A_ARRAY);
                   // Toast.makeText(mActivity, "Service pnt Consulting", Toast.LENGTH_LONG).show();

                    break;
                case R.id.button3:
                    WEB_A_ARRAY[0] = "PeopleNTech Consulting";
                    WEB_A_ARRAY[1] = ConfigKey.URL_consulting;
                    HTML_FILE_LODER.goWebpage(mActivity, WEB_A_ARRAY);

                  //  Toast.makeText(mActivity, "PnT Software", Toast.LENGTH_LONG).show();

                    break;

                case R.id.button4:

                    WEB_A_ARRAY[0] = "About Us";
                    WEB_A_ARRAY[1] = ConfigKey.HTML_ABOUT_URL;
                    HTML_FILE_LODER.openHtmlFile(mActivity, WEB_A_ARRAY);

                    break;

            }
        }
    };


    private void getDesplayHightWidth() {
        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
     /*
        int screen_height = outMetrics.heightPixels;
        int screen_width = outMetrics.widthPixels;

        Log.i("SKH", "screen_height:" + screen_height + " screen_width:" + screen_width + " b1.getHeight():" + b1.getLayoutParams().height);
        Log.i("SKH", "b1.getHeight():" + b1.getLayoutParams().height + "b1.width():" + b1.getLayoutParams().width);
        Log.i("SKH", "b2.getHeight():" + b2.getLayoutParams().height + "b2.width():" + b2.getLayoutParams().width);
        Log.i("SKH", "b3.getHeight():" + b3.getLayoutParams().height + "b3.width():" + b3.getLayoutParams().width);
        Log.i("SKH", "b4.getHeight():" + b4.getLayoutParams().height + "b4.width():" + b4.getLayoutParams().width);
        */
    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.home:
                        //  Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.tv_interview:
                        drawerLayout.closeDrawers();

                        intent = new Intent(mActivity, InterviewActivity.class);
                        intent.putExtra("KEY_StringName", "TV Interview");

                        mActivity.startActivity(intent);
                        break;
                    case R.id.testimonial:
                        drawerLayout.closeDrawers();

                        intent = new Intent(mActivity, TestimonialHomeActivity.class);
                        //intent.putExtra("KEY_StringName", "Testimonial");
                        mActivity.startActivity(intent);

                        break;

                    case R.id.ceo_message:

                        drawerLayout.closeDrawers();

                        WEB_A_ARRAY[0] = "CEO Message";
                        WEB_A_ARRAY[1] = ConfigKey.HTML_CEO_MESSAGE_URL;
                        HTML_FILE_LODER.openHtmlFile(mActivity, WEB_A_ARRAY);


                        break;
                    /*case R.id.meet_our_team:

                        drawerLayout.closeDrawers();
                        WEB_A_ARRAY[0] ="Meet Our Team";
                        WEB_A_ARRAY[1] = ConfigKey.leadership_team_piit_DATA_URL;

                        if (ConfigKey.isNetworkAvailable(mActivity)) {
                            HTML_FILE_LODER.goWebpage(mActivity, WEB_A_ARRAY);

                        } else {

                            HTML_FILE_LODER.openHtmlFile(mActivity, WEB_A_ARRAY);
                        }

                        break;*/
                    case R.id.contact_us:

                        drawerLayout.closeDrawers();

                        WEB_A_ARRAY[0] ="Contact Us";
                        WEB_A_ARRAY[1] = ConfigKey.HTML_COUNTACT_US_URL;

                        startActivity(new Intent(mActivity,ContactUsActivity.class).putExtra("WEB_A_ARRAY", WEB_A_ARRAY));


                        break;
                    case R.id.fb_page:

                        if (ConfigKey.isNetworkAvailable(mActivity)) {

                            WEB_A_ARRAY[0] ="Facebook";
                            WEB_A_ARRAY[1] = "https://www.facebook.com/peoplentech/";
                            HTML_FILE_LODER.goWebpage(mActivity, WEB_A_ARRAY);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        }


                        drawerLayout.closeDrawers();
                     break;

                    case R.id.about_us:

                        drawerLayout.closeDrawers();

                            WEB_A_ARRAY[0] = "About Us";
                            WEB_A_ARRAY[1] = ConfigKey.HTML_ABOUT_URL;
                            HTML_FILE_LODER.openHtmlFile(mActivity, WEB_A_ARRAY);

                        break;

                }
                return true;
            }
        });

        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView) header.findViewById(R.id.tv_email);
        tv_email.setText("A Global Leader In IT");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    /*@Override
    public void onBackPressed() {
        exit();

    }*/

    public void exit( ){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("You want to exit !");
        LayoutInflater inflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.text_titel, null);
        alertDialogBuilder.setCustomTitle(view);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void exit1(){


        final Dialog dialog = new Dialog(mActivity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_exit_dialog);



        TextView text = (TextView) dialog.findViewById(R.id.txt_dia);
        text.setText("You want to exit !");
        Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });


        Button btn_no = (Button) dialog.findViewById(R.id.btn_no);
        // if button is clicked, close the custom dialog
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}