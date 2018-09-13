package com.skh.peoplentech.peoplentech.Config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by samir on 3/9/2017.
 */

public class ConfigKey {

    //APPS_URL of my API


    public static String URL_SOFTWARE = "http://peoplentech.net";
    public static String URL_consulting = "http://peoplentech.com";
    public static String URL_ROOT = "https://master.piit.us/";
    public static String APPS_URL = URL_ROOT + "api/en/"; // /"http://10.16.11.19/piit_us/";  //http://10.16.10.23/www.piit.us/admin/upload/course/
    public static String URL1 = APPS_URL;//"http://10.16.10.30/www.piit.us/apps/";  //http://10.16.10.23/www.piit.us/admin/upload/course/

    public static final String Image_DATA_URL = URL_ROOT + "admin/upload/course/";
    public static final String offer_Image_DATA_URL = URL_ROOT + "admin/upload/offer/";
    public static final String Notice_Image_DATA_URL = URL_ROOT + "admin/upload/notice/";
    public static final String News_Event_Image_DATA_URL = URL_ROOT + "admin/upload/news/";
    public static final String Resume_upload_DATA_Location = URL_ROOT + "admin/upload/resume";

    public static final String Resume__URL = APPS_URL + "upload_resume.php";
    public static final String FREE_CONSULTATION = URL_ROOT + "api/freeConsultation";

    public static final String json_course_list_DATA_URL = APPS_URL + "all_courses";
    public static final String json_course_details_DATA_URL = APPS_URL + "json_course_details.php";
    public static final String json_json_testimonials_DATA_URL = APPS_URL + "json_testimonials.php";
    public static final String json_tv_interview_DATA_URL = APPS_URL + "json_tv_interview.php";
    public static final String submit_resume_DATA_URL = APPS_URL + "json_submit_resume.php";
    public static final String json_notic_URL1 = APPS_URL + "getPages/notice";  //Notices List
    public static final String about_piit_DATA_URL = APPS_URL + "about-piit.php";
    public static final String TESTIMONIAL_DATA_URL = APPS_URL + "getPages/video_testimonial/";

    //URL for USA testimonial
    public static final String USA_ENG_TESTIMONIAL_DATA_URL = TESTIMONIAL_DATA_URL + "english/0";
    public static final String USA_BAN_TESTIMONIAL_DATA_URL = TESTIMONIAL_DATA_URL + "bengali/1";
    public static final String USA_ARB_TESTIMONIAL_DATA_URL = TESTIMONIAL_DATA_URL + "arabic/2";
    public static final String USA_RUS_TESTIMONIAL_DATA_URL = TESTIMONIAL_DATA_URL + "russian/3";
    public static final String USA_NEP_TESTIMONIAL_DATA_URL = TESTIMONIAL_DATA_URL + "nepali/4";

    //URL for Bangladesh testimonial
    public static final String BANGLADESH_TESTIMONIAL_DATA_URL = APPS_URL + "getPages/bangladesh-video-testimonial";

    // public static final String ceo_message_piit_DATA_URL        = APPS_URL + "ceo-message-piit.php";
    public static final String company_profile_piit_DATA_URL = APPS_URL + "company-profile-piit.php";
    public static final String leadership_team_piit_DATA_URL = APPS_URL + "leadership-team-piit.php";
    public static final String contact_piit_DATA_URL = APPS_URL + "contact-piit.php";
    public static final String upcoming_class_URL = URL1 + "json_upcoming-class-piit.php";
    public static final String news_events_piit_URL = URL1 + "news-events-piit.php";  //Upcoming Events
    public static final String upcoming_class_URL1 = URL1 + "upcoming-class-piit.php";  //Upcoming Class/batches
    public static final String Reg_upcomming_batches_URL1 = URL1 + "upcoming-batch-reg-piit_api.php";  //Upcoming Class/batches

    //devkhs upcoming evet
    public static final String json_upcomming_event_DATA_URL = APPS_URL + "getPages/upcoming_events";
    // devkh tv interview
    public static final String TV_INTERVIEW_DATA_URL = APPS_URL + "getPages/tv_interviews";


    public static final String json_json_upcomming_batches_DATA_URL = APPS_URL + "batches/2";

    //latest offer
    public static final String json_latest_offer_DATA_URL = APPS_URL + "getPages/latest_offer";


    //Apply for course
    public static final String APPLY_FOR_COURSE = "https://www.piit.us/en/batch_reg/post";

///json_latest_offer_details.php?id=1


    public static final String HTML_ABOUT_URL = "file:///android_asset/about.html";
    public static final String HTML_COUNTACT_US_URL = "file:///android_asset/html_p/contact-piit.html";
    // public static final String HTML_CEO_MESSAGE_URL     =  "file:///android_asset/html_p/ceo-message-piit.html";
    public static final String HTML_CEO_MESSAGE_URL = "file:///android_asset/ceo_message.html";
    public static final String HTML_meet_our_team_URL = "file:///android_asset/meet_our_team.html";


    //Tags for my JSON json_course_list
    public static final String TAG_c_id = "id";
    public static final String TAG_c_name = "heading";

    public static final String TAG_Coursec_img = "c_img";
    public static final String TAG_Coursec_cc_name = "cc_name";
    public static final String TAG_Course_cc_details = "cc_details";


    // json_testimonials
    public static final String TAG_id = "id";
    public static final String TAG_user_name = "user_name";
    public static final String TAG_email = "email";
    public static final String TAG_language = "language";
    public static final String TAG_video_file = "video_file";
    public static final String TAG_status = "status";


    //{"vid":1,"v_title":"PeopleNTech, Abubakar Hanip","video_link":"https:\/\/www.youtube.com\/embed\/y4g0vpQgGro"
    // json_testimonials
    public static final String TAG_vid = "vid";
    public static final String TAG_v_title = "v_title";
    public static final String TAG_video_link = "video_link";

    public static final String TAG_offer_ID = "id";
    public static final String TAG_offer_offer_title = "offer_title";
    public static final String TAG_offer_offer_dt = "offer_dt";
    public static final String TAG_offer_image_file = "image_file";


    // json_ Notices
    public static final String TAG_Notice_ID = "id";
    public static final String TAG_notice_title = "notice_title";
    public static final String TAG_notice_dt = "notice_dt";
    public static final String TAG_notice_image_file = "image_file";


    // json_ Upcommeing Batch
    public static final String TAG_UB_upc_id = "id";
    public static final String TAG_UB_course_name = "heading";
    public static final String TAG_UB_location = "location";
    public static final String TAG_UB_campus = "campus";
    public static final String TAG_UB_type = "type";
    public static final String TAG_UB_starting_date = "starting_date";
    public static final String TAG_CONTENT = "content";
    public static final String TAG_FILE = "file";
    public static final String TAG_LAST_DATE = "last_date_of_admission";

    //Image URL
    public static final String IMAGE_URL_TEACHER = "https://piit.us/storage/";

    //batch registration

    public static final String BATCH_REGISTRATION = "https://master.piit.us/api/batch-registration";

    // json_ News Event
    public static final String TAG_NE_ne_id = "ne_id";
    public static final String TAG_NE_ne_name = "ne_name";
    public static final String TAG_NE_ne_details = "ne_details";
    public static final String TAG_NE_ne_link = "ne_link";
    public static final String TAG_NE_ne_img = "ne_img";


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}