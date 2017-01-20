package com.madhouse.androidlove.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "timeTracker.db";
    private static final String TIME_RECORD_COLUMN_TIME = "time";
    private static final String TIME_RECORD_COLUMN_NOTES = "notes";
    private static final String TIME_RECORD_TABLE_NAME = "time_record";

    private TimeTrackerOpenHelper openHelper;
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        openHelper = new TimeTrackerOpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public void saveTimeRecord(String time, String notes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIME_RECORD_COLUMN_TIME, time);
        contentValues.put(TIME_RECORD_COLUMN_NOTES, notes);
        database.insert(TIME_RECORD_TABLE_NAME, null, contentValues);
    }

    public Cursor getAllTimeRecords() {
        return database.rawQuery("select * from " + TIME_RECORD_TABLE_NAME, null);
    }


    private class TimeTrackerOpenHelper extends SQLiteOpenHelper {

        private static final String CREATE_TIME_RECORD = "create table time_record (" +
                "_id integer primary key autoincrement" +
                ", time text" +
                ", notes text" +
                ")";

        public TimeTrackerOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TIME_RECORD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists time_record");
            onCreate(db);
        }
    }


}
