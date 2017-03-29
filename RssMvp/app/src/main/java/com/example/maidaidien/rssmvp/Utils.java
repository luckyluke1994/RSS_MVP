package com.example.maidaidien.rssmvp;

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
}
