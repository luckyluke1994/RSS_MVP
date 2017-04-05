package com.example.maidaidien.rssmvp.presenter;

import android.content.Context;
import android.database.Cursor;

import com.example.maidaidien.rssmvp.Utils;
import com.example.maidaidien.rssmvp.model.ModelHelper;
import com.example.maidaidien.rssmvp.model.RSSItem;

/**
 * Created by mai.dai.dien on 29/03/2017.
 */

public class NewsPresenter implements Callbacks.Action {
    private Callbacks.NewsView mNewsView;
    private ModelHelper mModelHelper;

    public NewsPresenter(Callbacks.NewsView view, ModelHelper model) {
        this.mNewsView = view;
        this.mModelHelper = model;
        this.mModelHelper.setOnLoadFinish(this);
    }

    @Override
    public void refresh() {
        if (Utils.isNetworkAvailable(getAppContext())) {
            mNewsView.showLoading();
            mModelHelper.start();
            mModelHelper.refresh();
        }
    }

    @Override
    public void openNewsDetails(RSSItem newsItem) {
        mNewsView.openNewsDetailsUi(newsItem);
    }

    @Override
    public void stopService() {
        Utils.stopService(getAppContext());
    }

    @Override
    public void onLoadFinish(Cursor data) {
        mNewsView.onLoadFinish(data);
        if (data.getCount() != 0) {
            mNewsView.dismissLoading();
        }
    }

    @Override
    public Context getAppContext() {
        return this.mNewsView.getAppContext();
    }
}
