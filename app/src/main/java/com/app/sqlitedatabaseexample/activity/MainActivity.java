package com.app.sqlitedatabaseexample.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sqlitedatabaseexample.R;
import com.app.sqlitedatabaseexample.adapter.NoteAdapter;
import com.app.sqlitedatabaseexample.database.DatabaseHelper;
import com.app.sqlitedatabaseexample.listener.DeleteListener;
import com.app.sqlitedatabaseexample.listener.ItemClickListener;
import com.app.sqlitedatabaseexample.listener.UpdateListener;
import com.app.sqlitedatabaseexample.model.NoteModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, UpdateListener, DeleteListener, ItemClickListener {

    private FloatingActionButton mFab;
    private DatabaseHelper mDatabaseHelper;
    private RecyclerView mRecyclerList;
    private ArrayList<NoteModel> noteModelArrayList = new ArrayList<>();
    ;
    private NoteAdapter mAdapter;
    EditText mEdtTitle, mEdtDescription;
    NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }

    private void findViews() {
        mDatabaseHelper = DatabaseHelper.newInstance(MainActivity.this);
        noteModel = new NoteModel();
        mFab = findViewById(R.id.fab);
        mRecyclerList = findViewById(R.id.recycler_view);

        noteModelArrayList.addAll(mDatabaseHelper.getAllNotes());
        setAdapter();
        setOnClickListener();
    }

    private void setAdapter() {
        noteModelArrayList = mDatabaseHelper.getAllNotes();
        mAdapter = new NoteAdapter(this, noteModelArrayList, this, this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerList.setLayoutManager(mLayoutManager);
        mRecyclerList.setItemAnimator(new DefaultItemAnimator());
        mRecyclerList.setAdapter(mAdapter);
    }

    private void setOnClickListener() {
        mFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                showNoteDialog(false, null, -1);
                break;
        }
    }

    private void showNoteDialog(final boolean shouldUpdate, final NoteModel noteModel, final int position) {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.note_dialog);


        mEdtTitle = dialog.findViewById(R.id.edtTitle);
        mEdtDescription = dialog.findViewById(R.id.edtDescription);
        Button btnOK = dialog.findViewById(R.id.btnOK);
        Button btnCancel = dialog.findViewById(R.id.btnCancle);

        if (shouldUpdate && noteModel != null) {
            dialog.setTitle("Update Note");
            mEdtTitle.setText(noteModel.getTitle());
            mEdtDescription.setText(noteModel.getDescription());
        } else {
            dialog.setTitle("Add Note");
            mEdtTitle.setText("");
            mEdtDescription.setText("");
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!mEdtTitle.getText().toString().equals("")) && (!mEdtDescription.getText().toString().equals(""))) {
                    if (shouldUpdate && noteModel != null) {
                        mDatabaseHelper.updateNote(String.valueOf(position), mEdtTitle.getText().toString(), mEdtDescription.getText().toString());
                    } else {
                        mDatabaseHelper.insertNote(mEdtTitle.getText().toString(), mEdtDescription.getText().toString());
                    }
                    setAdapter();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Please Enter Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onUpdate(String s, String position) {
        showNoteDialog(true, noteModelArrayList.get(Integer.parseInt(s)), Integer.parseInt(position));

    }

    @Override
    public void onDelete(String position, String id) {
        mDatabaseHelper.deleteNote(Integer.parseInt(id));
        noteModelArrayList.remove(position);
        setAdapter();
    }

    @Override
    public void onItemClick(String position, String id) {

        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("noteId", id);
        startActivity(intent);
    }
}
