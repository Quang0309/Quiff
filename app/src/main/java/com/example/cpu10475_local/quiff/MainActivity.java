package com.example.cpu10475_local.quiff;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.cpu10475_local.quiff.adapter.RecyclerAdapter;
import com.example.cpu10475_local.quiff.model.DataManager;

public class MainActivity extends AppCompatActivity {
    DataManager manager;
    RecyclerView recyclerView;
    FloatingActionButton btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager.getInstance().init(this);
        init();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateNoteActivity.class);
                startActivity(intent);
            }
        });
     // manager.getInstance().addNote(new Note("Dinner with lover","28-06-2018","eat","remember to buy flowers",4));
     // manager.getInstance().addNote(new Note("Study with friend","29-06-2018","study","remember to do the homeworks",3));

    }

    private void init() {
        recyclerView =findViewById(R.id.recyclerMainAc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter());
        btnAdd = findViewById(R.id.floatingActionButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
