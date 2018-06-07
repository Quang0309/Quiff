package com.example.cpu10475_local.quiff.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.WindowManager;

import com.example.cpu10475_local.quiff.R;
import com.example.cpu10475_local.quiff.model.DataManager;
import com.example.cpu10475_local.quiff.model.Note;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {


    RecyclerView recyclerView;
    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerView recyclerView) {
        super(dragDirs, swipeDirs);
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        final Dialog dialog = new Dialog(DataManager.getInstance().context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.confirmed_dialog);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.2f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
        dialog.getWindow().setAttributes(lp);

        dialog.show();
        final int index = viewHolder.getAdapterPosition();

        final Note temp = DataManager.getInstance().notes.get(index);

        DataManager.getInstance().notes.remove(index);

        recyclerView.getAdapter().notifyItemRemoved(index);
        dialog.findViewById(R.id.txtDelete).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                DataManager.getInstance().deleteNotification(temp);
                DataManager.getInstance().deleteNote(temp);
                DataManager.getInstance().numItem--;
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.txtCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().notes.add(index,temp);
                recyclerView.getAdapter().notifyItemInserted(index);
                dialog.dismiss();

            }
        });

    }


}
