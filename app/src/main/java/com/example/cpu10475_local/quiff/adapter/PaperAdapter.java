package com.example.cpu10475_local.quiff.adapter;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cpu10475_local.quiff.R;
import com.example.cpu10475_local.quiff.model.DataManager;
import com.example.cpu10475_local.quiff.model.Note;
import com.example.cpu10475_local.quiff.service.Notification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaperAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private int[] layouts;
    private Context context;
    private ImageView imgType1,imgType2,imgType3,imgType4;
    int Day,Month,Year,Hour = -1,Minute = -1;
    private Note note;
    FloatingActionButton btnCreate;
    EditText txtNote;
    boolean isEdit;
    public PaperAdapter(int[] layouts, Context context,Note note) {
        this.layouts = layouts;
        this.context = context;
        if(note == null) {
            this.note = new Note();
            isEdit = false;
        }
        else {
            this.note = note;
            isEdit = true;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layouts[position],container,false);

        container.addView(view);
        view.setTag(position);
        if(position==0)
        {
            initViewPos0(view);
        }
        else if (position==1)
        {
            initViewPos1(view);
        }
        else if (position==2)
            initViewPos2(view);
        return view;
    }

    private void initViewPos1(View view) {
        txtNote = view.findViewById(R.id.txtNote);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "quicksandregular.ttf");
        txtNote.setTypeface(typeface);
        TextView txtNoteHeader = view.findViewById(R.id.txtNoteHeader);
        txtNoteHeader.setTypeface(typeface);
        txtNoteHeader.setText("NOTES");
        if(note.getOptionalInfo()!=null)
            txtNote.setText(note.getOptionalInfo());
    }

    private void initViewPos0(final View view) {
        imgType1 = view.findViewById(R.id.imgType1);
        imgType1.setImageBitmap(DataManager.getInstance().bitmaps[0]);

        imgType2 = view.findViewById(R.id.imgType2);
        imgType2.setImageBitmap(DataManager.getInstance().bitmaps[1]);


        imgType3 = view.findViewById(R.id.imgType3);
        imgType3.setImageBitmap(DataManager.getInstance().bitmaps[2]);


        imgType4 = view.findViewById(R.id.imgType4);
        imgType4.setImageBitmap(DataManager.getInstance().bitmaps[3]);
        if(note.getType()!=null)
        {
            if(note.getType().equals("business"))
                view.findViewById(R.id.imgType1Ac).setVisibility(View.VISIBLE);
            else if(note.getType().equals("study"))
                view.findViewById(R.id.imgType2Ac).setVisibility(View.VISIBLE);
            else if(note.getType().equals("eat"))
                view.findViewById(R.id.imgType3Ac).setVisibility(View.VISIBLE);
            else
                view.findViewById(R.id.imgType4Ac).setVisibility(View.VISIBLE);
        }
        EditText txtTitle = view.findViewById(R.id.txtTitleLayout1);
        if(note.getTitles()!=null)
            txtTitle.setText(note.getTitles());
        imgType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setType("business");
                view.findViewById(R.id.imgType1Ac).setVisibility(View.VISIBLE);
                view.findViewById(R.id.imgType2Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType3Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType4Ac).setVisibility(View.INVISIBLE);
            }
        });
        imgType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setType("study");
                view.findViewById(R.id.imgType1Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType2Ac).setVisibility(View.VISIBLE);
                view.findViewById(R.id.imgType3Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType4Ac).setVisibility(View.INVISIBLE);
            }
        });
        imgType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setType("eat");
                view.findViewById(R.id.imgType1Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType2Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType3Ac).setVisibility(View.VISIBLE);
                view.findViewById(R.id.imgType4Ac).setVisibility(View.INVISIBLE);
            }
        });
        imgType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setType("doctor");
                view.findViewById(R.id.imgType1Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType2Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType3Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType4Ac).setVisibility(View.VISIBLE);
            }
        });

        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast.makeText(context,s.toString(),Toast.LENGTH_SHORT).show();
                note.setTitles(s.toString());
             //   Toast.makeText(DataManager.getInstance().context,s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewPos2(View view)
    {
        final SeekBar seekBar = view.findViewById(R.id.seekBar);
        final TextView txtLevel = view.findViewById(R.id.txtLevel);
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Hour = hourOfDay;
                        Minute = minute;
                        Toast.makeText(context,Hour+":"+Minute,Toast.LENGTH_SHORT).show();
                    }
                },0,0,false);
                timePickerDialog.show();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Month*Year*Day!=0 && Minute+Hour>=0)
                {
                    if(Hour>9&&Minute>9)
                        note.setDate(Hour+":"+Minute+" "+Day+"-"+Month+"-"+Year);
                    else if(Hour<=9&&Minute>9)
                        note.setDate("0"+Hour+":"+Minute+" "+Day+"-"+Month+"-"+Year);
                    else if(Hour>9)
                        note.setDate(Hour+":"+"0"+Minute+" "+Day+"-"+Month+"-"+Year);
                    else
                        note.setDate("0"+Hour+":"+"0"+Minute+" "+Day+"-"+Month+"-"+Year);
                   // Toast.makeText(context,Hour+":"+Minute+Day+"-"+Month+"-"+Year,Toast.LENGTH_SHORT).show();
                    note.setOptionalInfo(txtNote.getText().toString());
                    note.setLevel(seekBar.getProgress());
                    if(note.getTitles()==null||note.getTitles().equals(""))
                    {
                        Toast.makeText(context,"Title can not be empty",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(note.getType()==null)
                    {
                        Toast.makeText(context,"Please choose a type",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JobScheduler scheduler = (JobScheduler)
                            context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    if (!isEdit) {
                        DataManager.getInstance().addNote(note);
                        PersistableBundle bundle = new PersistableBundle();
                        bundle.putString("title",note.getTitles());
                        bundle.putInt("level",note.getLevel());
                        bundle.putString("date",note.getDate());
                        bundle.putString("type",note.getType());
                        bundle.putInt("position",DataManager.getInstance().notes.indexOf(note));
                        JobInfo jobInfo = new JobInfo.Builder(DataManager.getInstance().notes.indexOf(note),
                                            new ComponentName(context, Notification.class))
                                            .setOverrideDeadline(calculateMilis())
                                            .setMinimumLatency(calculateMilis())
                                            .setExtras(bundle).build();
                        scheduler.schedule(jobInfo);

                        JobInfo jobInfo2 = new JobInfo.Builder(DataManager.getInstance().notes.indexOf(note)*-1,
                                new ComponentName(context, Notification.class))
                                .setOverrideDeadline(calculateMilis()-6*60*60*1000)
                                .setMinimumLatency(calculateMilis()-6*60*60*1000)
                                .setExtras(bundle).build();
                        scheduler.schedule(jobInfo2);

                    }
                    else
                        DataManager.getInstance().updateNote(note);
                    ((Activity)context).finish();
                }
            }


        });
    }
    public long calculateMilis() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        Date date = new Date();
        String temp = dateFormat.format(date);
        long targetDate = convertDate(note.getDate());
        Log.e("targetdate",Long.toString(targetDate));
        long curDate = convertDate(temp);
        Log.e("curDate",Long.toString(curDate));
        return targetDate-curDate;
    }
    public  long convertDate(String Date) {
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
        Log.e("LogYear",Integer.toString(Year));
        Log.e("LogMonth",Integer.toString(Month));
        Log.e("LogDay",Integer.toString(Day));
        Log.e("LogHour",Integer.toString(Hour));
        Log.e("LogMinute",Integer.toString(Minute));
        Log.e("LogSeparate","   ");
        long milliTime = calendar.getTimeInMillis();
        milliTime+=Hour*60*60*1000;
        milliTime+=Minute*60*1000;
        return milliTime;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

}
