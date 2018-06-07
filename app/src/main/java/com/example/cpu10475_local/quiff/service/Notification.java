package com.example.cpu10475_local.quiff.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.ScaleDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.example.cpu10475_local.quiff.DetailActivity;
import com.example.cpu10475_local.quiff.R;
import com.example.cpu10475_local.quiff.model.DataManager;
import com.example.cpu10475_local.quiff.model.Note;

public class Notification extends JobService {
    Note note;


    public Notification() {

    }
    @Override
    public boolean onStartJob(JobParameters params) {
        note = new Note();
        note.setTitles(params.getExtras().getString("title"));
        note.setDate(params.getExtras().getString("date"));
        note.setType(params.getExtras().getString("type"));
        android.app.Notification.Builder builder = new android.app.Notification.Builder(this).setContentTitle(note.getTitles())
                                                    .setContentText(note.getDate()).setVibrate(new long[]{1000,1000,1000,1000})
                                                    .setAutoCancel(true);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);
        if(note.getType().equals("business")) {

            builder.setSmallIcon(R.drawable.ic_stat_name);
            builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.business));
        }
        else if (note.getType().equals("eat")) {
            builder.setSmallIcon(R.drawable.ic_);
            builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.eat));
        }
        else if(note.getType().equals("study")) {
            builder.setSmallIcon(R.drawable.ic_study);
            builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.study));
        }
        else {
            builder.setSmallIcon(R.drawable.ic_doctor);
            builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.doctor));
        }
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("hashcode",params.getExtras().getInt("hashcode"));


        intent.putExtra("title",params.getExtras().getString("title"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        android.app.Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(params.getExtras().getInt("hashcode"),notification);
        jobFinished(params,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
