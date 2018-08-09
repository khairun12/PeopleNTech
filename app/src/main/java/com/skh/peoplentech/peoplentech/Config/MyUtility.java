package com.skh.peoplentech.peoplentech.Config;

/**
 * Created by samir on 4/19/2017.
 */

public class MyUtility {

    public static String r(String s){
        return  s.substring(0, Math.min(s.length(), 100)); //return 100 char
    }
}
