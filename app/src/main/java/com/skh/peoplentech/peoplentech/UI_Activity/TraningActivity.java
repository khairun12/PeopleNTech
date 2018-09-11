package com.skh.peoplentech.peoplentech.UI_Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.skh.peoplentech.peoplentech.Adapter.MainListAdapter;
import com.skh.peoplentech.peoplentech.Config.MyAnimation;
import com.skh.peoplentech.peoplentech.Modle.ListItem;
import com.skh.peoplentech.peoplentech.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TraningActivity extends AppCompatActivity {

   private MainListAdapter rcAdapter;
   private RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  new MyAnimation(this).overridePendingTransitionEnter();
        setContentView(R.layout.activity_traning);

        mSetToolber();


        //lLayout = new GridLayoutManager(TraningActivity.this, 4);

        rView = (RecyclerView) findViewById(R.id.my_recycler_view);
        rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);


        //Grid View
        rView.setLayoutManager(new GridLayoutManager(this, 2, 1, false));

        //StaggeredGridView
        // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));


        callListView();
       // callGridView();
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

        mTitle.setText("Training");

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void callGridView() {
        rcAdapter=null;
        List<ListItem> rowListItem = getAllItemList();
        //Grid View
        rView.setLayoutManager(new GridLayoutManager(this, 2, 1, false));
        rcAdapter = new MainListAdapter(TraningActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);
    }

    private void callListView() {
        rcAdapter=null;
        List<ListItem> rowListItem = getAllItemList();
        // ListView
        rView.setLayoutManager(new LinearLayoutManager(this));
        rcAdapter = new MainListAdapter(TraningActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Grid View
            callListView();
            return true;
        }
        if (id == R.id.action_refresh) {
            callGridView();
            //Toast.makeText(TraningActivity.this, "Refresh App", Toast.LENGTH_LONG).show();
        }


        return super.onOptionsItemSelected(item);
    }

    private List<ListItem> getAllItemList() {

        List<ListItem> allItems = new ArrayList<>();
        allItems.add(new ListItem("All Courses", R.drawable.course));
        allItems.add(new ListItem("Upcoming New Batches", R.drawable.batch));
        allItems.add(new ListItem("Special offer", R.drawable.offer));
        allItems.add(new ListItem("Notice", R.drawable.notice));
        allItems.add(new ListItem("Upcoming Event", R.drawable.event));
        allItems.add(new ListItem("Free Consultation", R.drawable.resume));

        return allItems;
    }



}