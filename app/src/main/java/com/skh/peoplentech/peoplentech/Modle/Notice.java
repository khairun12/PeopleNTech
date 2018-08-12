package com.skh.peoplentech.peoplentech.Modle;

/**
 * Created by samir on 4/12/2017.
 */

public class Notice {
   /* //"id":1,"notice_title":"Holiday Notice (Sunday, Dec 25, 2016)","notice_dt":"
    Please be informed that, due to Holy Christmas as per the decision of our management all of our Bangladesh facilities will remain closed on Sunday, Dec 25, 2016. All classes scheduled for these days will be suspended.<\/p>\n

    Classes and all regular official activities will resume sharp Monday, Dec 26, 2016 ,on normal schedule.<\/p>\n

    Merry Christmas to you all.Stay safe.<\/p>","image_file":"20161224171629_unnamed.jpg"
*/
   private String id;
   private String notice_title;
   private String notice_dt;

   public Notice (String title, String details){

   }

   public Notice () {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_dt() {
        return notice_dt;
    }

    public void setNotice_dt(String notice_dt) {
        this.notice_dt = notice_dt;
    }

}
