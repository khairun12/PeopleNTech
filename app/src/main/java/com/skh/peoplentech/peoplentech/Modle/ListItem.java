package com.skh.peoplentech.peoplentech.Modle;

/**
 * Created by samir on 3/6/2017.
 */

public class ListItem {
    private String name;
    private int image;

    public ListItem(String s, int logo) {
        name = s;
        image = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
