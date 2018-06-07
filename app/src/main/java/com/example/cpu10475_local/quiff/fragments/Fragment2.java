package com.example.cpu10475_local.quiff.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpu10475_local.quiff.CreateNoteActivity;
import com.example.cpu10475_local.quiff.R;
import com.example.cpu10475_local.quiff.model.Note;

public class Fragment2 extends Fragment {
    boolean isCheck =false;
    TextView txtNote;
    Note note;
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
        View view = inflater.inflate(R.layout.layout_add2,container,false);
        txtNote = view.findViewById(R.id.txtNote);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "quicksandregular.ttf");
        txtNote.setTypeface(typeface);
        TextView txtNoteHeader = view.findViewById(R.id.txtNoteHeader);
        txtNoteHeader.setTypeface(typeface);
        txtNoteHeader.setText("NOTES");
        if(note.getOptionalInfo()!=null)
            txtNote.setText(note.getOptionalInfo());
        return  view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        // super.setMenuVisibility(menuVisible);
        if (!menuVisible) {
            // Log.e("not see","no no");
            if (isCheck) {

                isCheck =false;
                note.setOptionalInfo(txtNote.getText().toString());


                ((CreateNoteActivity)getActivity()).setNote(note);
            }

        }
        if(menuVisible) {
         //   Log.e("Visible", "no no");
            isCheck = true;
           note = ((CreateNoteActivity)getActivity()).getNote();
//            Log.e("Title", note.getTitles());
            /*if (note == null)
                note = new Note();*/
        }
    }
}
