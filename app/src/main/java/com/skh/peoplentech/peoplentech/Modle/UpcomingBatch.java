package com.skh.peoplentech.peoplentech.Modle;

/**
 * Created by samir on 4/12/2017.
 */

public class UpcomingBatch {

    ///upc_id":1,"course_name":"Certified Web Development Specialist","location":"Good Luck Center 151\/7 Green Road, 7th Floor Dhaka 1205","campus":"Bangladesh","type":"Weekday","starting_date":"2016-01-15"

    private String upc_id;
    private String course_name;
    private String file;
    private String last_date;
    private String content;
    private String starting_date;

    public UpcomingBatch(){}


    public String getUpc_id() {
        return upc_id;
    }

    public void setUpc_id(String upc_id) {
        this.upc_id = upc_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }
}
