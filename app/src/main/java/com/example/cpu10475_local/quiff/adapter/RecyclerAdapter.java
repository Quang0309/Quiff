package com.example.cpu10475_local.quiff.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cpu10475_local.quiff.DetailActivity;
import com.example.cpu10475_local.quiff.R;
import com.example.cpu10475_local.quiff.model.DataManager;
import com.example.cpu10475_local.quiff.model.Note;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    ArrayList<Note> data = DataManager.getInstance().notes;
    String dateTime[];
    public int index = -1;
    Activity activity;

    public RecyclerAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(DataManager.getInstance().context.getApplicationContext());
        View view = inflater.inflate(R.layout.note_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Note temp = data.get(position);

        if(temp.getType().equals("eat"))
            holder.imgType.setImageBitmap(DataManager.getInstance().bitmaps[2]);
        else if (temp.getType().equals("study"))
            holder.imgType.setImageBitmap(DataManager.getInstance().bitmaps[1]);
        else if(temp.getType().equals("business"))
            holder.imgType.setImageBitmap(DataManager.getInstance().bitmaps[0]);
        else
            holder.imgType.setImageBitmap(DataManager.getInstance().bitmaps[3]);
        holder.txtTitle.setText(temp.getTitles());
        dateTime = temp.getDate().split(" ");
        holder.txtTime.setText(dateTime[0]);
       // Toast.makeText(DataManager.getInstance().context.getApplicationContext(),temp.getDate().substring(0,5),Toast.LENGTH_SHORT).show();
        holder.txtDate.setText(dateTime[1]);
        if(temp.getLevel()==3) {
            holder.imgLevel.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.txtDate.setTextColor(Color.parseColor("#FF0000"));
            holder.txtTitle.setTextColor(Color.parseColor("#FF0000"));
            holder.txtTime.setTextColor(Color.parseColor("#FF0000"));
        }
        else if (temp.getLevel()==2) {
            holder.imgLevel.setBackgroundColor(Color.parseColor("#FFA500"));
            holder.txtDate.setTextColor(Color.parseColor("#FFA500"));
            holder.txtTitle.setTextColor(Color.parseColor("#FFA500"));
            holder.txtTime.setTextColor(Color.parseColor("#FFA500"));
        }
        else if (temp.getLevel()==1) {
            holder.imgLevel.setBackgroundColor(Color.parseColor("#CCCC00"));
            holder.txtDate.setTextColor(Color.parseColor("#CCCC00"));
            holder.txtTitle.setTextColor(Color.parseColor("#CCCC00"));
            holder.txtTime.setTextColor(Color.parseColor("#CCCC00"));
        }
        else {
            holder.imgLevel.setBackgroundColor(Color.parseColor("#32CD32"));
            holder.txtDate.setTextColor(Color.parseColor("#32CD32"));
            holder.txtTitle.setTextColor(Color.parseColor("#32CD32"));
            holder.txtTime.setTextColor(Color.parseColor("#32CD32"));
        }
        final View shareView = holder.constraint;
        shareView.setTransitionName(holder.txtTitle.getText().toString());
           // Toast.makeText(DataManager.getInstance().context,holder.txtTitle.getText().toString(),Toast.LENGTH_SHORT).show();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                Intent intent = new Intent(DataManager.getInstance().context, DetailActivity.class);
                intent.putExtra("hashcode",temp.hashCode());
                intent.putExtra("transition",ViewCompat.getTransitionName(shareView));
                intent.putExtra("title",temp.getTitles());
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,shareView,shareView.getTransitionName());
                try {
                    DataManager.getInstance().context.startActivity(intent, activityOptionsCompat.toBundle());
                }catch (IllegalArgumentException exception)
                {
                    DataManager.getInstance().context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
