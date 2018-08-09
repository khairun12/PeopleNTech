package com.skh.peoplentech.peoplentech.Modle;

/**
 * Created by samir on 4/4/2017.
 */

public class CourseM {
    private String c_id;
    private String c_Tittel;
    private String c_Duration;
    private String c_description;
    private String c_apply_url;

    public CourseM() {
    }

    public CourseM(String c_id, String c_Tittel, String c_Duration, String c_description, String c_apply_url) {
        this.c_id = c_id;
        this.c_Tittel = c_Tittel;
        this.c_Duration = c_Duration;
        this.c_description = c_description;
        this.c_apply_url = c_apply_url;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_Tittel() {
        return c_Tittel;
    }

    public void setC_Tittel(String c_Tittel) {
        this.c_Tittel = c_Tittel;
    }

    public String getC_Duration() {
        return c_Duration;
    }

    public void setC_Duration(String c_Duration) {
        this.c_Duration = c_Duration;
    }

    public String getC_description() {
        return c_description;
    }

    public void setC_description(String c_description) {
        this.c_description = c_description;
    }

    public String getC_apply_url() {
        return c_apply_url;
    }

    public void setC_apply_url(String c_apply_url) {
        this.c_apply_url = c_apply_url;
    }
}
