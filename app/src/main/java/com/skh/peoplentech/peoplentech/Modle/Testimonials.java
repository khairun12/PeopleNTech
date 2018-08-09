package com.skh.peoplentech.peoplentech.Modle;

/**
 * Created by samir on 3/16/2017.
 */

public class Testimonials {
    private String id;
    private String user_name;
    private String email;
    private String language;
    private String video_file;
    private String videoId;

    public Testimonials(){

    }

    public Testimonials(String userName, String videoId){
        this.user_name = userName;
        this.videoId = videoId;
    }

  //  "id":2,"user_name":" Abul Kalam Azad Liton ","email":"megh101@yahoo.com.au","phone":null,"testimonial_type":1,"language":"Bangla","video_file":"https:\/\/www.youtube.com\/embed\/o_j8tfd6rTg","videothumb_file":"","photo_file":"","image_file":"","testimonial_message":null,"date_submit":null,"status":"Deactive"},

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVideo_file() {
        return video_file;
    }

    public void setVideo_file(String video_file) {
        this.video_file = video_file;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}
