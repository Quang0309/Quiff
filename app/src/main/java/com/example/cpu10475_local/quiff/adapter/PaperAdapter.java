package com.example.cpu10475_local.quiff.adapter;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
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

public class PaperAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private int[] layouts;
    private Context context;
    private ImageView imgType1,imgType2,imgType3,imgType4;
    int Day,Month,Year,Hour = -1,Minute = -1;
    private Note note;
    FloatingActionButton btnCreate;
    EditText txtNote;
    public PaperAdapter(int[] layouts, Context context) {
        this.layouts = layouts;
        this.context = context;
        note = new Note();
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
                    Toast.makeText(context,Hour+":"+Minute+Day+"-"+Month+"-"+Year,Toast.LENGTH_SHORT).show();
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
                    DataManager.getInstance().addNote(note);
                    ((Activity)context).finish();
                }
            }
        });
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
