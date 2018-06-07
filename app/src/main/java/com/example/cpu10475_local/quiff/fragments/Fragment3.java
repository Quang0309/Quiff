package com.example.cpu10475_local.quiff.fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cpu10475_local.quiff.CreateNoteActivity;
import com.example.cpu10475_local.quiff.R;
import com.example.cpu10475_local.quiff.model.DataManager;
import com.example.cpu10475_local.quiff.model.Note;
import com.example.cpu10475_local.quiff.service.Notification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fragment3 extends Fragment {
    boolean isCheck =false;
    FloatingActionButton btnCreate;
    SeekBar seekBar;
    TextView txtLevel;
    Note note;
    int Day,Month,Year,Hour = -1,Minute = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = ((CreateNoteActivity)getActivity()).getNote();

        if(note == null)
            note = new Note();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add3,container,false);
        seekBar = view.findViewById(R.id.seekBar);
        txtLevel = view.findViewById(R.id.txtLevel);
        CalendarView calendarView = view.findViewById(R.id.calendarView);

        ImageView imgClock = view.findViewById(R.id.imgClock);
        btnCreate = view.findViewById(R.id.btnCreate);
        if(note.getDate()!=null)
        {
            calendarView.setDate(convertDate(note.getDate()));
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Day = dayOfMonth;
                Month = month;
                Year = year;
                Month++;
                //note.setDate(Day+"-"+Month+"-"+Year);
                //Toast.makeText(context,Day+"-"+Month+"-"+Year,Toast.LENGTH_SHORT).show();
            }
        });
        if(note.getLevel()!=-1) {
            seekBar.setProgress(note.getLevel());
            if(seekBar.getProgress()==3) {
                txtLevel.setText("very important");

            }
            else if(seekBar.getProgress()==2)
                txtLevel.setText("important");
            else if(seekBar.getProgress()==1)
                txtLevel.setText("normal");
            else if(seekBar.getProgress()==0)
                txtLevel.setText("take it easy");
        }
        else
            seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==3) {
                    txtLevel.setText("very important");

                }
                else if(progress==2)
                    txtLevel.setText("important");
                else if(progress==1)
                    txtLevel.setText("normal");
                else if(progress==0)
                    txtLevel.setText("take it easy");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        imgClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Hour = hourOfDay;
                        Minute = minute;
                        Toast.makeText(getContext(),Hour+":"+Minute,Toast.LENGTH_SHORT).show();
                    }
                },0,0,false);
                timePickerDialog.show();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Minute+Hour>=0)
                {
                    if (Month*Year*Day == 0)
                    {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                        Date date = new Date();
                        String temp = dateFormat.format(date);
                        String dateTime[] = temp.split(" ");
                        String dateTemp[] = dateTime[1].split("-");
                        Day = Integer.parseInt(dateTemp[0]);
                        Month = Integer.parseInt(dateTemp[1]);
                        Year = Integer.parseInt(dateTemp[2]);
                    }
                    if(Hour>9&&Minute>9)
                        note.setDate(Hour+":"+Minute+" "+Day+"-"+Month+"-"+Year);
                    else if(Hour<=9&&Minute>9)
                        note.setDate("0"+Hour+":"+Minute+" "+Day+"-"+Month+"-"+Year);
                    else if(Hour>9)
                        note.setDate(Hour+":"+"0"+Minute+" "+Day+"-"+Month+"-"+Year);
                    else
                        note.setDate("0"+Hour+":"+"0"+Minute+" "+Day+"-"+Month+"-"+Year);
                    // Toast.makeText(context,Hour+":"+Minute+Day+"-"+Month+"-"+Year,Toast.LENGTH_SHORT).show();
                    //note.setOptionalInfo(txtNote.getText().toString());
                    note.setLevel(seekBar.getProgress());
                    if(note.getTitles()==null||note.getTitles().equals(""))
                    {
                        Toast.makeText(getContext(),"Title can not be empty",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(note.getType()==null)
                    {
                        Toast.makeText(getContext(),"Please choose a type",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JobScheduler scheduler = (JobScheduler)
                            getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    if (!((CreateNoteActivity)getActivity()).isEdit()) {
                        DataManager.getInstance().addNote(note);
                        PersistableBundle bundle = new PersistableBundle();
                        bundle.putString("title",note.getTitles());
                        bundle.putInt("level",note.getLevel());
                        bundle.putString("date",note.getDate());
                        bundle.putString("type",note.getType());
                        bundle.putInt("hashcode",note.hashCode());

                        JobInfo jobInfo = new JobInfo.Builder(note.hashCode(),
                                new ComponentName(getContext(), Notification.class))
                                .setOverrideDeadline(calculateMilis())
                                .setMinimumLatency(calculateMilis())
                                .setExtras(bundle).build();
                        scheduler.schedule(jobInfo);

                        JobInfo jobInfo2 = new JobInfo.Builder(note.hashCode()*-1,
                                new ComponentName(getContext(), Notification.class))
                                .setOverrideDeadline(calculateMilis()-6*60*60*1000)
                                .setMinimumLatency(calculateMilis()-6*60*60*1000)
                                .setExtras(bundle).build();
                        scheduler.schedule(jobInfo2);

                    }
                    else
                        DataManager.getInstance().updateNote(note);
                    ((Activity)getContext()).finish();
                }
                else
                    Toast.makeText(getContext(),"Please choose a time",Toast.LENGTH_SHORT).show();
            }


        });
        return  view;
    }
    private long calculateMilis() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        Date date = new Date();
        String temp = dateFormat.format(date);
        long targetDate = convertDate(note.getDate());
        /*Log.e("targetdate",Long.toString(targetDate));*/
        long curDate = convertDate(temp);
        /*  Log.e("curDate",Long.toString(curDate));*/
        return targetDate-curDate;
    }
    private long convertDate(String Date) {
        String dateTime[] = Date.split(" ");
        String date[] = dateTime[1].split("-");
        String time[] = dateTime[0].split(":");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        Day = day;
        Month = month;
        Year = year;
        Hour = Integer.parseInt(time[0]);
        Minute = Integer.parseInt(time[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        //  Log.e("month",Integer.toString(month));
        calendar.set(Calendar.DAY_OF_MONTH, day);
        /*Log.e("LogYear",Integer.toString(Year));
        Log.e("LogMonth",Integer.toString(Month));
        Log.e("LogDay",Integer.toString(Day));
        Log.e("LogHour",Integer.toString(Hour));
        Log.e("LogMinute",Integer.toString(Minute));
        Log.e("LogSeparate","   ");*/
        long milliTime = calendar.getTimeInMillis();
        milliTime+=Hour*60*60*1000;
        milliTime+=Minute*60*1000;
        return milliTime;
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        // super.setMenuVisibility(menuVisible);
        if (!menuVisible) {
            // Log.e("not see","no no");
            if (isCheck) {

                isCheck =false;
            }

        }
        if(menuVisible) {
            //Log.e("Visible", "no no");
            isCheck = true;
            note = ((CreateNoteActivity)getActivity()).getNote();
            if (note == null)
                note = new Note();
        }
    }
}
