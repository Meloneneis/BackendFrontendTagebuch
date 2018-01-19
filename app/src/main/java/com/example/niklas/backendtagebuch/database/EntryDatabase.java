package com.example.niklas.backendtagebuch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;

import com.example.niklas.backendtagebuch.model.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Yannick on 18.01.18.
 */

public class EntryDatabase extends SQLiteOpenHelper {
    public static EntryDatabase INSTANCE = null;
    public static final String DB_NAME = "ENTRIES";
    public static final int VERSION = 4;
    public static final String TABLE_NAME= "entries";
    public static final String TITLE_COLUMN = "title";
    public static final String ID_COLUMN =  "ID";
    public static final String DATE_COLUMN = "date";
    public EntryDatabase(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static EntryDatabase getInstance(final Context context){
        if(INSTANCE==null){
            INSTANCE=new EntryDatabase(context);
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_NAME + " (" + ID_COLUMN + " INTEGER PRIMARY KEY, " + TITLE_COLUMN + " TEXT NOT NULL, " + DATE_COLUMN + " INTEGER NOT NULL)";
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
        values.put(DATE_COLUMN,entry.getDate().getTimeInMillis() / 1000);

        long newID = database.insert(TABLE_NAME,null,values);
        database.close();
        return readEntry(newID);

    }

    public Entry readEntry(final long id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String []{ID_COLUMN,TITLE_COLUMN,DATE_COLUMN}, ID_COLUMN + " = ?", new String[]{String.valueOf(id)},null,null,null );
        Entry entry = null;

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            entry = new Entry(cursor.getString(cursor.getColumnIndex(TITLE_COLUMN)));
            entry.setId(cursor.getLong(cursor.getColumnIndex(ID_COLUMN)));

            Calendar calendar = null;
            if(!cursor.isNull(cursor.getColumnIndex(DATE_COLUMN))){
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(cursor.getInt(cursor.getColumnIndex(DATE_COLUMN)) * 1000);
            }
            entry.setDate(calendar);
        }

        database.close();
        return entry;
    }

    public List<Entry> readAllEntries(){
        List<Entry> entries = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.moveToFirst()){
             do{
                Entry entry = readEntry(cursor.getLong(cursor.getColumnIndex(ID_COLUMN)));
                if(entry != null){
                    entries.add(entry);
                }
             }while(cursor.moveToNext());
        }

        database.close();
        return entries;
    }

    public Entry updateEntry(final Entry entry){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COLUMN, entry.getTitle());
        values.put(DATE_COLUMN, entry.getDate() == null ? null: entry.getDate().getTimeInMillis() /1000);
        database.update(TABLE_NAME, values, ID_COLUMN + " = ?", new String[]{String.valueOf(entry.getId())} );
        database.close();
        return this.readEntry(entry.getId());

    }

    public void deleteEntry(final Entry entry){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, ID_COLUMN+" = ?", new String[]{String.valueOf(entry.getId())});
        database.close();
    }

    public void deleteallEntries(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM "+TABLE_NAME);
        database.close();
    }

    public Cursor getAllEntriesAsCursor(){
        return this.getReadableDatabase().rawQuery("SELECT " + ID_COLUMN + " as _id, " + TITLE_COLUMN + "," + DATE_COLUMN + " FROM " + TABLE_NAME, null);
    }

    public Entry getFirstEntry(){
        List<Entry> entries = this.readAllEntries();
        if(entries.size() > 0){
            return entries.get(0);
        }
        return  null;
    }

}
