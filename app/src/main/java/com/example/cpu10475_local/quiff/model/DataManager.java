package com.example.cpu10475_local.quiff.model;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.cpu10475_local.quiff.R;
import com.example.cpu10475_local.quiff.database.NoteBaseHelper;
import com.example.cpu10475_local.quiff.database.NoteCursorWrapper;
import com.example.cpu10475_local.quiff.database.NoteDbSchema;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataManager {
    private SQLiteDatabase mDatabase;
    public ArrayList<Note> notes;
    public Context context;
    public Bitmap[] bitmaps;
    public int numItem;
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
        numItem = notes.size();
//        Log.e("Title",notes.get(0).getTitles());
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
        int j = mDatabase.update(NoteDbSchema.NoteTable.NAME,contentValues,
                NoteDbSchema.NoteTable.Cols.Titles+" = ?",new String[]{title});
        if(j==0)
            Toast.makeText(context.getApplicationContext(),"Can not change the name of a note",Toast.LENGTH_SHORT).show();
        for(int i=0;i<notes.size();i++)
        {
            if(notes.get(i).getTitles().equals(note.getTitles()))
            {
                notes.set(i,note);
                Log.e("option",notes.get(i).getOptionalInfo());
            }
        }
    }
    private NoteCursorWrapper queryNotes(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(NoteDbSchema.NoteTable.NAME,null,whereClause,whereArgs,null,null,null);
        return  new NoteCursorWrapper(cursor);
    }
    private List<Note> getNotes()
    {
        List<Note> notes= new ArrayList<>();
        List<Note> outofdate = new ArrayList<>();
        NoteCursorWrapper cursor = queryNotes(null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
               if(isOutOfDate(cursor.getNote()))
                    outofdate.add(cursor.getNote());
               else
                    notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
            deleteOutOfDateNote(outofdate);
        }
        return notes;
    }

    private void deleteOutOfDateNote(List<Note> outofdate) {
        for(int i=0;i<outofdate.size();i++) {
            deleteNote(outofdate.get(i));
            /*JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            ArrayList<JobInfo> pendingJobs= (ArrayList<JobInfo>) jobScheduler.getAllPendingJobs();
            for(int j=0;i<pendingJobs.size();j++)
            {
                if(notes.indexOf(outofdate.get(i)) == pendingJobs.get(j).getId())
                {
                    jobScheduler.cancel(pendingJobs.get(j).getId());
                    jobScheduler.cancel(pendingJobs.get(j).getId()*-1);
                }
            }*/
        }
    }
    public void deleteNotification(Note note)
    {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ArrayList<JobInfo> pendingJobs= (ArrayList<JobInfo>) jobScheduler.getAllPendingJobs();
        for(int i=0;i<pendingJobs.size();i++)
        {
            if(notes.indexOf(note) == pendingJobs.get(i).getId())
            {
                jobScheduler.cancel(pendingJobs.get(i).getId());
                jobScheduler.cancel(pendingJobs.get(i).getId()*-1);
            }
        }
    }
    private boolean isOutOfDate(Note note) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        Date date = new Date();
        String temp = dateFormat.format(date);
        long targetDate = convertDate(note.getDate());
        long curDate = convertDate(temp);
        if(targetDate-curDate<=0)
            return true;
        return false;
    }
    public  long convertDate(String Date) {
        String dateTime[] = Date.split(" ");
        String date[] = dateTime[1].split("-");
        String time[] = dateTime[0].split(":");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        int Day = day;
        int Month = month;
        int Year = year;
        int Hour = Integer.parseInt(time[0]);
        int Minute = Integer.parseInt(time[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        //  Log.e("month",Integer.toString(month));
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();
        milliTime+=Hour*60*60*1000;
        milliTime+=Minute*60*1000;
        return milliTime;
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
