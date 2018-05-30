package com.example.cpu10475_local.quiff;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpu10475_local.quiff.model.DataManager;
import com.example.cpu10475_local.quiff.model.Note;

public class DetailActivity extends AppCompatActivity {
    Note note;
    TextView txtTitle,txtDate,txtLevel,txtNote,txtBrand;
    ConstraintLayout constraintLayout;
    ImageButton btnEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        loadNote();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,CreateNoteActivity.class);
                intent.putExtra("note",note);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        if(DataManager.getInstance().notes==null)
            DataManager.getInstance().init(this);
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
        btnEdit = findViewById(R.id.imgbtnEdit);

    }

    private void loadNote() {
        if (DataManager.getInstance().notes.size()==0)
        {
            Toast.makeText(this,"The note was deleted.",Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(DetailActivity.this,MainActivity.class);
            startActivity(intent);
            return;
        }
        note = DataManager.getInstance().notes.get(getIntent().getIntExtra("position",0));
        if(!note.getTitles().equals(getIntent().getStringExtra("title")))
        {
            Toast.makeText(this,"The note was deleted.",Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(DetailActivity.this,MainActivity.class);
            startActivity(intent);
            return;
        }
        //Toast.makeText(this,Integer.toString(DataManager.getInstance().notes.indexOf(note)),Toast.LENGTH_SHORT).show();
        constraintLayout.setTransitionName(getIntent().getStringExtra("transition"));
        if(getIntent().getStringExtra("transition")==null)
            Log.e("transition name","null");
        else
            Log.e("transition name",constraintLayout.getTransitionName());
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

    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}
