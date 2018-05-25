package com.example.cpu10475_local.quiff;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cpu10475_local.quiff.model.DataManager;
import com.example.cpu10475_local.quiff.model.Note;

public class DetailActivity extends AppCompatActivity {
    Note note;
    TextView txtTitle,txtDate,txtLevel,txtNote,txtBrand;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        supportPostponeEnterTransition();
        init();
        loadNote();
    }

    private void init() {
        constraintLayout = findViewById(R.id.constraintLayoutDetail);
        txtTitle = findViewById(R.id.txtTitleLayoutDetail);
        txtDate = findViewById(R.id.txtDateLayoutDetail);
        txtLevel = findViewById(R.id.txtLevelLayoutDetail);
        txtNote = findViewById(R.id.txtNoteLayoutDetail);
        txtBrand = findViewById(R.id.txtBrand);
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "quicksandregular.ttf");
        txtTitle.setTypeface(typeface);
        txtDate.setTypeface(typeface);
        txtLevel.setTypeface(typeface);
        txtNote.setTypeface(typeface);
    }

    private void loadNote() {
        note = DataManager.getInstance().notes.get(getIntent().getIntExtra("position",0));
        constraintLayout.setTransitionName(getIntent().getStringExtra("transition"));
        txtTitle.setText(note.getTitles());
        txtDate.setText(note.getDate());
        if(note.getLevel()==0) {
            txtLevel.setText("Level: take it easy");

            txtBrand.setBackground(getDrawable(R.drawable.brand0));
        }
        else if(note.getLevel()==1) {
            txtLevel.setText("Level: normal");
            txtBrand.setBackground(getDrawable(R.drawable.brand1));
        }
        else if(note.getLevel()==2) {
            txtLevel.setText("Level: important");
            txtBrand.setBackground(getDrawable(R.drawable.brand2));
        }
        else if(note.getLevel()==3) {
            txtLevel.setText("Level: very important");
            txtBrand.setBackground(getDrawable(R.drawable.brand3));
        }
        txtNote.setText(note.getOptionalInfo());
        supportStartPostponedEnterTransition();
    }
}
