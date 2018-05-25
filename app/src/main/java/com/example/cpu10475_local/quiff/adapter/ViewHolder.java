package com.example.cpu10475_local.quiff.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpu10475_local.quiff.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imgType;
    TextView txtTitle, txtDate, txtTime;
    ImageView imgLevel;
    ConstraintLayout constraint;
    public ViewHolder(View itemView) {
        super(itemView);
        imgType = itemView.findViewById(R.id.imgType);
        imgLevel = itemView.findViewById(R.id.imgLevel);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtDate = itemView.findViewById(R.id.txtDate);
        txtTime = itemView.findViewById(R.id.txtTime);
        constraint = itemView.findViewById(R.id.constraintLayout);
    }
}
