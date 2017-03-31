package com.example.maidaidien.rssmvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by mai.dai.dien on 29/03/2017.
 */

public class Utils {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() == null
                || !conMgr.getActiveNetworkInfo().isConnected()
                || !conMgr.getActiveNetworkInfo().isAvailable()) {
            return false;
        }
        return true;
    }

    public static ProgressDialog getProgressDialog(Context context, int theme) {
        ProgressDialog progressDialog = new ProgressDialog(context, theme);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
