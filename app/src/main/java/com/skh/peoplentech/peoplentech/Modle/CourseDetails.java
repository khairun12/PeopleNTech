package com.skh.peoplentech.peoplentech.Modle;

/**
 * Created by samir on 3/11/2017.
 */

public class CourseDetails {
    private  String c_id;
    private  String type;
    private  String c_name;
    private  String c_img;
    private  String cc_name;
    private  String cc_details;

    ///"c_id":1,"type":"LongCourse","c_name":"Software Testing with Quick Test Pro (QTP)\/(UFT)","c_img":"20160503181622_QTP.jpg","cc_name":"MODULE - 1: Computer components (02 hours)","cc_details":"

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public String getCc_name() {
        return cc_name;
    }

    public void setCc_name(String cc_name) {
        this.cc_name = cc_name;
    }

    public String getCc_details() {
        return cc_details;
    }

    public void setCc_details(String cc_details) {
        this.cc_details = cc_details;
    }

    @Override
    public String toString() {
        return "CourseDetails{" +
                "c_id='" + c_id + '\'' +
                ", type='" + type + '\'' +
                ", c_name='" + c_name + '\'' +
                ", c_img='" + c_img + '\'' +
                ", cc_name='" + cc_name + '\'' +
                ", cc_details='" + cc_details + '\'' +
                '}';
    }
}
