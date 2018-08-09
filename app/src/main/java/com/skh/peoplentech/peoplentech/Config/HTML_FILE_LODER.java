package com.skh.peoplentech.peoplentech.Config;

import android.app.Activity;
import android.content.Intent;

import com.skh.peoplentech.peoplentech.UI_Activity.HtnlFileLoderActivity;
import com.skh.peoplentech.peoplentech.UI_Activity.WebActivity;

/**
 * Created by samir on 4/19/2017.
 */

public class HTML_FILE_LODER {
    private static Intent intent;

    public static void goWebpage(Activity mActivity, String[] web_a_array) {

        intent = new Intent(mActivity, WebActivity.class);
        intent.putExtra("WEB_A_ARRAY", web_a_array);
        mActivity.startActivity(intent);
    }

    public static void openHtmlFile(Activity mActivity,String[] web_a_array) {
        intent = new Intent(mActivity, HtnlFileLoderActivity.class);
        intent.putExtra("KEY_StringName", web_a_array[0]);
        intent.putExtra("KEY_String_file_url", web_a_array[1]);
        mActivity.startActivity(intent);
    }

}
