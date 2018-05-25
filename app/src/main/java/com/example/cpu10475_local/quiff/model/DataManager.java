package com.example.cpu10475_local.quiff.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cpu10475_local.quiff.R;
import com.example.cpu10475_local.quiff.database.NoteBaseHelper;
import com.example.cpu10475_local.quiff.database.NoteCursorWrapper;
import com.example.cpu10475_local.quiff.database.NoteDbSchema;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private SQLiteDatabase mDatabase;
    public ArrayList<Note> notes;
    public Context context;
    public Bitmap[] bitmaps;
    private DataManager() {


    }
    public void init(Context context)
    {
        mDatabase = new NoteBaseHelper(context.getApplicationContext()).getWritableDatabase();
        notes = (ArrayList<Note>) getNotes();
        this.context = context;
        bitmaps = new Bitmap[4];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;
        BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.business, options);
        BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.study, options);
        BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.eat, options);
        options.inSampleSize=2;
        BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.doctor, options);
        options.inJustDecodeBounds=false;
        bitmaps[0] =  BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.business, options);
        bitmaps[1] = BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.study, options);
        bitmaps[2] =  BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.eat, options);
        bitmaps[3] = BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.doctor, options);
    }
    private static ContentValues getContentValues(Note note)
    {
        ContentValues values = new ContentValues();

        values.put(NoteDbSchema.NoteTable.Cols.Titles,note.getTitles());
        values.put(NoteDbSchema.NoteTable.Cols.Date,note.getDate());
        values.put(NoteDbSchema.NoteTable.Cols.Type,note.getType());
        values.put(NoteDbSchema.NoteTable.Cols.OptionalInfo,note.getOptionalInfo());
        values.put(NoteDbSchema.NoteTable.Cols.Level,note.getLevel());
        return values;
    }
    public void addNote(Note note)
    {
        notes.add(note);
        ContentValues contentValues = getContentValues(note);
        mDatabase.insert(NoteDbSchema.NoteTable.NAME,null,contentValues);
    }
    public void deleteNote(Note note)
    {
        mDatabase.delete(NoteDbSchema.NoteTable.NAME,NoteDbSchema.NoteTable.Cols.Titles+" = ?",new String[]{note.getTitles()});
    }
    public void updateNote(Note note)
    {
        String title = note.getTitles();
        ContentValues contentValues = getContentValues(note);
        mDatabase.update(NoteDbSchema.NoteTable.NAME,contentValues,
                NoteDbSchema.NoteTable.Cols.Titles+" = ?",new String[]{title});
    }
    private NoteCursorWrapper queryNotes(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(NoteDbSchema.NoteTable.NAME,null,whereClause,whereArgs,null,null,null);
        return  new NoteCursorWrapper(cursor);
    }
    private List<Note> getNotes()
    {
        List<Note> notes= new ArrayList<>();
        NoteCursorWrapper cursor = queryNotes(null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return notes;
    }
    private  static class DataHolder
    {
        private static final DataManager INSTANCE = new DataManager();
    }
    public static DataManager getInstance()
    {
        return DataHolder.INSTANCE;
    }
}
