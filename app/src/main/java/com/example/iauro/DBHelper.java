package com.example.iauro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.iauro.model.PhotoData;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper noddDataHelper;

    private static final String DATABASE_NAME = "iauro_db";
    private static final String TABLE_PHOTOS = "TABLE_PHOTOS";
    private static final String KEY_ID = "KEY_ID";
    private static final String KEY_TITLE = "KEY_TITLE";
    private static final String KEY_URL = "KEY_URL";

    public static DBHelper getInstance(final Context context) {
        if (noddDataHelper == null) {
            noddDataHelper = new DBHelper(context);
        }
        return noddDataHelper;
    }


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_URL + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }

    private void createAllTables(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(TABLE_PHOTOS);
        } catch (Exception e) {
            // handle exception
        }
    }

    public void addPhotos(List<PhotoData> photoDataList) {
        final SQLiteDatabase db = this.getWritableDatabase();
        //db.enableWriteAheadLogging();
        db.beginTransactionNonExclusive();

        try {
            for (PhotoData photoData : photoDataList) {
                ContentValues values = new ContentValues();
                values.put(KEY_TITLE, photoData.getTitle());
                values.put(KEY_URL, photoData.getUrl());
                long res = db.insertWithOnConflict(TABLE_PHOTOS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                values.clear();
            }
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            createAllTables(db);
        } finally {
            db.endTransaction();
        }
    }

    public List<PhotoData> getAllPhotoData() {
        List<PhotoData> photosList = new ArrayList<PhotoData>();
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_PHOTOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {
            do {
                PhotoData contact = new PhotoData();
                contact.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                contact.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                contact.setUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
                // Adding contact to list  
                photosList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list  
        return photosList;
    }

}
