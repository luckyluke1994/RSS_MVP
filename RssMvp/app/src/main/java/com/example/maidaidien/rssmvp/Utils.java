package com.example.maidaidien.rssmvp;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Process;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

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

    public static void stopService(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();

        Iterator<ActivityManager.RunningAppProcessInfo> iter = runningAppProcesses.iterator();

        while(iter.hasNext()){
            ActivityManager.RunningAppProcessInfo next = iter.next();

            String pricessName = context.getPackageName() + ":service";

            if(next.processName.equals(pricessName)){
                Process.killProcess(next.pid);
                break;
            }
        }
    }
}
