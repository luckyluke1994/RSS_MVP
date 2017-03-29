package com.example.maidaidien.rssmvp.model;

import android.net.Uri;

/**
 * Created by mai.dai.dien on 29/03/2017.
 */

public class ModelHelper {
    private String mUrl;
    private Uri mUri;

    public ModelHelper(String url, Uri uri) {
        this.mUrl = url;
        this.mUri = uri;
    }
}
