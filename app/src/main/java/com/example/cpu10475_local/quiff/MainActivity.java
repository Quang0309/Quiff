package com.example.cpu10475_local.quiff;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.cpu10475_local.quiff.adapter.RecyclerAdapter;
import com.example.cpu10475_local.quiff.adapter.ViewHolder;
import com.example.cpu10475_local.quiff.helper.RecyclerItemTouchHelper;
import com.example.cpu10475_local.quiff.model.DataManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DataManager manager;
    RecyclerView recyclerView;
    FloatingActionButton btnAdd;
    SwipeRefreshLayout refreshLayout;
    RecyclerItemTouchHelper recyclerItemTouchHelper;
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
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        Date date = new Date();
        String temp = dateFormat.format(date);
        Toast.makeText(this,temp,Toast.LENGTH_SHORT).show();*/
     // manager.getInstance().addNote(new Note("Dinner with lover","28-06-2018","eat","remember to buy flowers",4));
     // manager.getInstance().addNote(new Note("Study with friend","29-06-2018","study","remember to do the homeworks",3));

    }

    private void init() {
        recyclerView =findViewById(R.id.recyclerMainAc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new RecyclerAdapter());
        btnAdd = findViewById(R.id.floatingActionButton);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
            }
        });
        recyclerItemTouchHelper = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT,recyclerView);
        new ItemTouchHelper(recyclerItemTouchHelper).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this,Integer.toString(recyclerView.getAdapter().getItemCount()),Toast.LENGTH_SHORT).show();
        if(DataManager.getInstance().numItem!=DataManager.getInstance().notes.size()) {
            recyclerView.getAdapter().notifyDataSetChanged();
            DataManager.getInstance().numItem = DataManager.getInstance().notes.size();
        }
    }


}
