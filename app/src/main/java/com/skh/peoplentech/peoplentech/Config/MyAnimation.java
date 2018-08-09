package com.skh.peoplentech.peoplentech.Config;

import android.app.Activity;

import com.skh.peoplentech.peoplentech.R;

/**
 * Created by samir on 4/5/2017.
 */

public class MyAnimation {

   private Activity activity;

    public MyAnimation(Activity activity) {
        this.activity = activity;
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    public void overridePendingTransitionEnter() {
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    public void overridePendingTransitionExit() {
        activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
