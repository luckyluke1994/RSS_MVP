package com.example.maidaidien.rssmvp.model;

/**
 * Created by mai.dai.dien on 27/03/2017.
 */

public class RSSItem {
    private int mId;
    private String mTitle;
    private String mDescription;
    private String mDate;
    private String mImage;
    private String mLink;

    public RSSItem() {
        mId = -1;
        mTitle       = null;
        mDescription = null;
        mDate        = null;
        mImage       = null;
        mLink        = null;
    }

    public RSSItem(int id, String title, String description, String date, String image, String link) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mDate = date;
        mImage = image;
        mLink = link;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getDate() {
        return mDate;
    }

    public String getImage() {
        return mImage;
    }

    public String getLink() {
        return mLink;
    }
}
