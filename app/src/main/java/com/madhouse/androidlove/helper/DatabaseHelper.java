package com.madhouse.androidlove.helper;

import java.io.File;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContextWrapper;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

public class DatabaseHelper extends ContextWrapper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "time_tracker.db";
    private static final String TIME_RECORD_COLUMN_TIME = "time";
    private static final String TIME_RECORD_COLUMN_NOTES = "notes";
    private static final String TIME_RECORD_TABLE_NAME = "time_record";

    private TimeTrackerOpenHelper openHelper;
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context);
        openHelper = new TimeTrackerOpenHelper(this);
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

    public File getDatabasePath(String name) {
        // has external storage
        // TODO 如果用户没给存储权限，会闪退
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String sdCardPath = Environment.getExternalStorageDirectory().toString() + "/com.madhouse.androidlove";
            String dbPath = sdCardPath + "/" + name;
            File sdCardPathFile = new File(sdCardPath);
            if (!sdCardPathFile.exists())
                sdCardPathFile.mkdirs();
            File dbPathFile = new File(dbPath);
            if (!dbPathFile.exists()) {
                try {
                    dbPathFile.createNewFile();
                } catch (IOException e) {
                    Log.d(null, e.getMessage(), e);
                }
            }
            return dbPathFile;
        } else {
            return super.getDatabasePath(name);
        }
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory,
                                               DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
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
