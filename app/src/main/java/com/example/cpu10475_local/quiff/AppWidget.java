package com.example.cpu10475_local.quiff;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.opengl.Visibility;
import android.view.View;
import android.widget.RemoteViews;

import com.example.cpu10475_local.quiff.model.DataManager;
import com.example.cpu10475_local.quiff.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        if (DataManager.getInstance().bitmaps == null)
            DataManager.getInstance().init(context);
        Note cur = findCurMin();
        if (cur == null) {
            views.setTextViewText(R.id.appwidget_text, "You dont have any appointment");
            views.setTextViewText(R.id.appwidget_date,"");
            views.setViewVisibility(R.id.appwidget_announcement, View.INVISIBLE);
            views.setImageViewBitmap(R.id.appwidget_img,DataManager.getInstance().bitmaps[4]);
        }
        else {
            views.setTextViewText(R.id.appwidget_text, cur.getTitles());
            views.setTextViewText(R.id.appwidget_date,cur.getDate());
            views.setViewVisibility(R.id.appwidget_announcement, View.VISIBLE);
            if(cur.getType().equals("study"))
                views.setImageViewBitmap(R.id.appwidget_img,DataManager.getInstance().bitmaps[1]);
            else if(cur.getType().equals("business"))
                views.setImageViewBitmap(R.id.appwidget_img,DataManager.getInstance().bitmaps[0]);
            else if(cur.getType().equals("eat"))
                views.setImageViewBitmap(R.id.appwidget_img,DataManager.getInstance().bitmaps[2]);
            else if(cur.getType().equals("doctor"))
                views.setImageViewBitmap(R.id.appwidget_img,DataManager.getInstance().bitmaps[3]);

        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    public static Note findCurMin()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        Date date = new Date();
        String cur = dateFormat.format(date);
        long curLong = DataManager.getInstance().convertDate(cur);
        if (DataManager.getInstance().notes.size() ==0)
            return null;
        Note temp = DataManager.getInstance().notes.get(0);
        long min = DataManager.getInstance().convertDate(temp.getDate())-curLong;
        for(int i=1;i<DataManager.getInstance().notes.size();i++)
        {
            Note index = DataManager.getInstance().notes.get(i);
            long j = DataManager.getInstance().convertDate(index.getDate())-curLong;
            if(j>=0)
            {
                if (j<min ||min<0)
                {
                    temp = index;
                    min = j;
                }
            }
        }
        return temp;
    }
}

