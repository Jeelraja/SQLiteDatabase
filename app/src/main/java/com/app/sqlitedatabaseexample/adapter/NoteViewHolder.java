package com.app.sqlitedatabaseexample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sqlitedatabaseexample.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    public TextView tvNoteTitle;
    public TextView tvNoteDesc;
    public ImageView ivUpdate;
    public ImageView ivDelete;
    public RelativeLayout rlRoot;

    public NoteViewHolder(View itemView) {
        super(itemView);
        tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
        tvNoteDesc = itemView.findViewById(R.id.tvNoteDesc);
        ivUpdate = itemView.findViewById(R.id.ivUpdate);
        ivDelete = itemView.findViewById(R.id.ivDelete);
        rlRoot = itemView.findViewById(R.id.rlroot);
    }
}
