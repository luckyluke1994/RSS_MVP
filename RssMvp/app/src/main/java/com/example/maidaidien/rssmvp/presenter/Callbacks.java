package com.example.maidaidien.rssmvp.presenter;

import android.content.Context;
import android.database.Cursor;

import com.example.maidaidien.rssmvp.model.RSSItem;

/**
 * Created by mai.dai.dien on 29/03/2017.
 */

public class Callbacks {
    public interface NewsView {
        Context getAppContext();
        void onLoadFinish(Cursor data);
        void showLoading();
        void dismissLoading();
        void openNewsDetailsUi(RSSItem newsItem);
    }

    public interface Action {
        Context getAppContext();
        void onLoadFinish(Cursor data);
        void refresh();
        void openNewsDetails(RSSItem newsItem);
        void stopService();
    }

    public interface OnNewsItemClicked {
        void onNewsItemClicked(RSSItem newsItem);
    }
}
