package com.example.cpu10475_local.quiff.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cpu10475_local.quiff.database.NoteDbSchema.NoteTable;

public class NoteBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "noteBase.db";
    public NoteBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ NoteTable.NAME + "("+" _id integer primary key autoincrement,  "+
                                                        NoteTable.Cols.Titles+", "+NoteTable.Cols.Date+", "+NoteTable.Cols.Type
                                                        +", "+ NoteTable.Cols.OptionalInfo
                                                        +", "+ NoteTable.Cols.Level+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
