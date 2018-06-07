package com.example.cpu10475_local.quiff.fragments;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cpu10475_local.quiff.CreateNoteActivity;
import com.example.cpu10475_local.quiff.R;
import com.example.cpu10475_local.quiff.helper.RecyclerItemTouchHelper;
import com.example.cpu10475_local.quiff.model.DataManager;
import com.example.cpu10475_local.quiff.model.Note;

public class Fragment1 extends Fragment  {
    ImageView imgType1,imgType2,imgType3,imgType4;
    Note note;
    boolean isCheck = false;
    public static final String TYPE = "type";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = ((CreateNoteActivity)getActivity()).getNote();
        if (note == null)
            note = new Note();

        if(savedInstanceState!=null)
            note.setType(savedInstanceState.getString(TYPE));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_add1,container,false);
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
            if(note.getType().equals("business")) {
               // view.findViewById(R.id.imgType1Ac).setVisibility(View.VISIBLE);
                imgType1.setBackgroundResource(R.drawable.choose_image);
            }
            else if(note.getType().equals("study")) {
               // view.findViewById(R.id.imgType2Ac).setVisibility(View.VISIBLE);
                imgType2.setBackgroundResource(R.drawable.choose_image);
            }
            else if(note.getType().equals("eat")) {
                //view.findViewById(R.id.imgType3Ac).setVisibility(View.VISIBLE);
                imgType3.setBackgroundResource(R.drawable.choose_image);
            }
            else {
               // view.findViewById(R.id.imgType4Ac).setVisibility(View.VISIBLE);
                imgType4.setBackgroundResource(R.drawable.choose_image);
            }
        }

        EditText txtTitle = view.findViewById(R.id.txtTitleLayout1);
        if(note.getTitles()!=null)
            txtTitle.setText(note.getTitles());
        imgType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setType("business");
                imgType1.setBackgroundResource(R.drawable.choose_image);
                imgType2.setBackgroundResource(R.drawable.not_choose_image);
                imgType3.setBackgroundResource(R.drawable.not_choose_image);
                imgType4.setBackgroundResource(R.drawable.not_choose_image);
                /*view.findViewById(R.id.imgType1Ac).setVisibility(View.VISIBLE);
                view.findViewById(R.id.imgType2Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType3Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType4Ac).setVisibility(View.INVISIBLE);*/
            }
        });
        imgType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setType("study");
                imgType2.setBackgroundResource(R.drawable.choose_image);
                imgType1.setBackgroundResource(R.drawable.not_choose_image);
                imgType3.setBackgroundResource(R.drawable.not_choose_image);
                imgType4.setBackgroundResource(R.drawable.not_choose_image);
               /* view.findViewById(R.id.imgType1Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType2Ac).setVisibility(View.VISIBLE);
                view.findViewById(R.id.imgType3Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType4Ac).setVisibility(View.INVISIBLE);*/
            }
        });
        imgType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setType("eat");
                imgType3.setBackgroundResource(R.drawable.choose_image);
                imgType1.setBackgroundResource(R.drawable.not_choose_image);
                imgType2.setBackgroundResource(R.drawable.not_choose_image);
                imgType4.setBackgroundResource(R.drawable.not_choose_image);
               /* view.findViewById(R.id.imgType1Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType2Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType3Ac).setVisibility(View.VISIBLE);
                view.findViewById(R.id.imgType4Ac).setVisibility(View.INVISIBLE);*/
            }
        });
        imgType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setType("doctor");
                imgType4.setBackgroundResource(R.drawable.choose_image);
                imgType2.setBackgroundResource(R.drawable.not_choose_image);
                imgType3.setBackgroundResource(R.drawable.not_choose_image);
                imgType1.setBackgroundResource(R.drawable.not_choose_image);
                /*view.findViewById(R.id.imgType1Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType2Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType3Ac).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.imgType4Ac).setVisibility(View.VISIBLE);*/
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
        return  view;
    }




    @Override
    public void setMenuVisibility(boolean menuVisible) {
        // super.setMenuVisibility(menuVisible);
        if (!menuVisible) {
           // Log.e("not see","no no");
            if (isCheck) {

                isCheck =false;
                ((CreateNoteActivity)getActivity()).setNote(note);
            }

        }
        if(menuVisible) {
          //  Log.e("Visible", "no no");
          /*  if(getActivity() == null)
                return;
            note = ((CreateNoteActivity)getActivity()).getNote();
            if (note == null)
                note = new Note();*/
            isCheck = true;

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TYPE,note.getType());
    }
}
