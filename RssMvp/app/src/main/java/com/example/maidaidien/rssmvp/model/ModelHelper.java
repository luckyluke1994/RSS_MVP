package com.example.maidaidien.rssmvp.model;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.maidaidien.rssmvp.Constant;
import com.example.maidaidien.rssmvp.presenter.Callbacks;
import com.example.maidaidien.rssmvp.service.NewsService;

/**
 * Created by mai.dai.dien on 29/03/2017.
 */

public class ModelHelper implements LoaderManager.LoaderCallbacks<Cursor> {
    private String mUrl;
    private Uri mUri;
    private Callbacks.Action mOnLoadFinish;
    private LoaderManager mLoaderManager;
    private int mLoaderId;

    private static final String[] ALLNEWS_COLUMN = {
            NewsContract.AllNewsEntry.TABLE_NAME + "." + NewsContract.AllNewsEntry._ID,
            NewsContract.AllNewsEntry.COLUMN_TITLE,
            NewsContract.AllNewsEntry.COLUMN_DESCRIPTION,
            NewsContract.AllNewsEntry.COLUMN_DATE,
            NewsContract.AllNewsEntry.COLUMN_IMAGE,
            NewsContract.AllNewsEntry.COLUMN_LINK
    };

    public ModelHelper(String url, Uri uri, LoaderManager loaderManager, int loaderId) {
        this.mUrl = url;
        this.mUri = uri;
        this.mLoaderManager = loaderManager;
        this.mLoaderId = loaderId;
    }

    public void setOnLoadFinish(Callbacks.Action onLoadFinish) {
        this.mOnLoadFinish = onLoadFinish;
    }

    public void start() {
        mLoaderManager.initLoader(mLoaderId, null, this);
    }

    public void refresh() {
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        Intent intent = new Intent(getContext(), NewsService.class);
        intent.putExtra(Constant.LINK_EXTRA, mUrl);
        intent.putExtra(Constant.URI_EXTRA, mUri.toString());
        getContext().startService(intent);
    }

    private void loadDataFromDatabase() {
    }

    private Context getContext() {
        return this.mOnLoadFinish.getAppContext();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case Constant.AllNewsLoaderId:
                return new CursorLoader(getContext(),
                        NewsContract.AllNewsEntry.CONTENT_URI,
                        ALLNEWS_COLUMN,
                        null,
                        null,
                        null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mOnLoadFinish.onLoadFinish(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
