package com.app.sqlitedatabaseexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.sqlitedatabaseexample.R;
import com.app.sqlitedatabaseexample.activity.DetailsActivity;
import com.app.sqlitedatabaseexample.activity.MainActivity;
import com.app.sqlitedatabaseexample.database.DatabaseHelper;
import com.app.sqlitedatabaseexample.listener.DeleteListener;
import com.app.sqlitedatabaseexample.listener.ItemClickListener;
import com.app.sqlitedatabaseexample.listener.UpdateListener;
import com.app.sqlitedatabaseexample.model.NoteModel;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private ArrayList<NoteModel> noteList;
    private LayoutInflater mInflater;
    private DeleteListener deleteListener;
    private UpdateListener updateListener;
    private ItemClickListener itemClickListener;
    String id;

    public NoteAdapter(Context context, ArrayList<NoteModel> noteList, UpdateListener updateListener, DeleteListener deleteListener, ItemClickListener itemClickListener) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.noteList = noteList;
        mDatabaseHelper = DatabaseHelper.newInstance(mContext);
        this.updateListener = updateListener;
        this.deleteListener = deleteListener;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.note_list_row, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, final int position) {
        final NoteModel noteModel = noteList.get(position);
        holder.tvNoteTitle.setText(noteModel.getTitle());
        holder.tvNoteDesc.setText(noteModel.getDescription());
        holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = String.valueOf(noteModel.getId());
                updateListener.onUpdate(String.valueOf(position), id);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = String.valueOf(noteModel.getId());
                deleteListener.onDelete(String.valueOf(position), id);
            }
        });
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = String.valueOf(noteModel.getId());
                itemClickListener.onItemClick(String.valueOf(position), id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
