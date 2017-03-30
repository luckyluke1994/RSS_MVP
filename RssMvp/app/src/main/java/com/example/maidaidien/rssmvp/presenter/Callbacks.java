package com.example.maidaidien.rssmvp.presenter;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by mai.dai.dien on 29/03/2017.
 */

public class Callbacks {
    public interface NewsView {
        Context getAppContext();
        void onLoadFinish(Cursor data);
    }

    public interface Action {
        Context getAppContext();
        void onLoadFinish(Cursor data);
    }
}
