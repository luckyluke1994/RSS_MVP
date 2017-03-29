package com.example.maidaidien.rssmvp.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by mai.dai.dien on 28/03/2017.
 */

public class NewsProvider extends ContentProvider {
    // The URI Matcher used by this content provider.
    public static final UriMatcher sUriMatcher = buildUriMatcher();
    private NewsDbHelper mOpenHelper;

    public static final int ALLNEWS = 100;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NewsContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, NewsContract.PATH_ALLNEWS, ALLNEWS);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new NewsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case ALLNEWS:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        NewsContract.AllNewsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        long _id;
        switch (match) {
            case ALLNEWS:
                _id = db.insert(NewsContract.AllNewsEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = NewsContract.AllNewsEntry.buildAllNewsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if (null == selection) selection = "1";
        switch (match) {
            case ALLNEWS:
                rowsDeleted = db.delete(NewsContract.AllNewsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case ALLNEWS:
                rowsUpdated = db.update(NewsContract.AllNewsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCound = 0;
        long _id;

        switch (match) {
            case ALLNEWS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        _id = db.insert(NewsContract.AllNewsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCound++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCound;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
