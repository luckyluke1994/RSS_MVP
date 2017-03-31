package com.example.maidaidien.rssmvp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mai.dai.dien on 28/03/2017.
 */

public class NewsDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "news.db";

    public NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ALLNEWS_TABLE = "CREATE TABLE " + NewsContract.AllNewsEntry.TABLE_NAME + " (" +
                NewsContract.AllNewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NewsContract.AllNewsEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                NewsContract.AllNewsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                NewsContract.AllNewsEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                NewsContract.AllNewsEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                NewsContract.AllNewsEntry.COLUMN_LINK + " TEXT NOT NULL );";

        final String SQL_CREATE_FOOTBALLNEWS_TABLE = "CREATE TABLE " + NewsContract.FootballNewsEntry.TABLE_NAME + " (" +
                NewsContract.FootballNewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NewsContract.FootballNewsEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                NewsContract.FootballNewsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                NewsContract.FootballNewsEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                NewsContract.FootballNewsEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                NewsContract.FootballNewsEntry.COLUMN_LINK + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_ALLNEWS_TABLE);
        db.execSQL(SQL_CREATE_FOOTBALLNEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NewsContract.AllNewsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NewsContract.FootballNewsEntry.TABLE_NAME);
        onCreate(db);
    }
}
