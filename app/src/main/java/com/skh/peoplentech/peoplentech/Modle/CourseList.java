package com.skh.peoplentech.peoplentech.Modle;

/**
 * Created by samir on 3/9/2017.
 */

public class CourseList {

    private String c_id;
    //private String type;
    private String c_name;
    //private String c_img;

    public CourseList(){}

    public CourseList(String c_id, String c_name) {
        this.c_id = c_id;
        this.c_name = c_name;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

   /* public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    } */

    /*@Override
    public String toString() {
        return "CourseList{" +
                "c_id='" + c_id + '\'' +
                ", type='" + type + '\'' +
                ", c_name='" + c_name + '\'' +
                ", c_img='" + c_img + '\'' +
                '}';
    }*/
}
