package com.app.sqlitedatabaseexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.app.sqlitedatabaseexample.R;
import com.app.sqlitedatabaseexample.database.DatabaseHelper;
import com.app.sqlitedatabaseexample.model.NoteModel;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private TextView mTvNoteId;
    private TextView mTvNoteTitle;
    private TextView mTvNoteDesc;
    private String strId = "", strPosition = "";
    private DatabaseHelper mDatabaseHelper;
    private ArrayList<NoteModel> noteModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDatabaseHelper = DatabaseHelper.newInstance(DetailsActivity.this);
        mTvNoteId = findViewById(R.id.tvNoteId);
        mTvNoteTitle = findViewById(R.id.tvNoteTitle);
        mTvNoteDesc = findViewById(R.id.tvNoteDesc);

        Intent intent = getIntent();
        if (intent != null) {
            strId = intent.getStringExtra("noteId");
        }
        noteModelArrayList = mDatabaseHelper.getNoteById(strId);
        Log.i("SIZE", "++" + noteModelArrayList.size());
        if (noteModelArrayList != null && noteModelArrayList.size() > 0) {
            mTvNoteId.setText("Id :" + noteModelArrayList.get(0).getId());
            mTvNoteTitle.setText("Title :" + noteModelArrayList.get(0).getTitle());
            mTvNoteDesc.setText("Desc :" + noteModelArrayList.get(0).getDescription());
        }
    }
}
