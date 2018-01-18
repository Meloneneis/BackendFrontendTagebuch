package com.example.niklas.backendtagebuch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.niklas.backendtagebuch.model.Entry;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Yannick on 18.01.18.
 */

public class EntryDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "ENTRIES";
    private static final int VERSION = 1;
    private static final String TABLE_NAME= "entries";
    private static final String TITLE_COLUMN = "title";
    private static final String ID_COLUMN =  "ID";
    private static final String DATE_COLUMN = "date";
    public EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + " (" + ID_COLUMN + " INTEGER PRIMARY KEY, " + TITLE_COLUMN + " TEXT NOT NULL, " + DATE_COLUMN + " INTEGER DEFAULT NULL)";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTable);
        onCreate(db);
    }

    public Entry createEntry(final Entry entry){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COLUMN,entry.getTitle());
        
    }

    public Entry readEntry(final long id){

    }

    public List<Entry> readAllEntries(){

    }

}
