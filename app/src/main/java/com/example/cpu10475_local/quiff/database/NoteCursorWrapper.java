package com.example.cpu10475_local.quiff.database;

import android.database.Cursor;
import android.database.CursorWrapper;


import com.example.cpu10475_local.quiff.database.NoteDbSchema.NoteTable;

import com.example.cpu10475_local.quiff.model.Note;

public class NoteCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Note getNote()
    {
        String title = getString(getColumnIndex(NoteTable.Cols.Titles));
        String date = getString(getColumnIndex(NoteTable.Cols.Date));
        String type = getString(getColumnIndex(NoteTable.Cols.Type));
        String optionalInfo = getString(getColumnIndex(NoteTable.Cols.OptionalInfo));
        int level = getInt(getColumnIndex(NoteTable.Cols.Level));

        Note temp = new Note(title,date,type,optionalInfo,level);
        return temp;
    }
}
