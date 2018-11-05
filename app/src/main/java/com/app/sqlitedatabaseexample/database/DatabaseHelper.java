package com.app.sqlitedatabaseexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.sqlitedatabaseexample.model.NoteModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;

    private static final String DATABASE_NAME = "NoteDB";

    private static final String TABLE_NOTE = "Note";
    public static final String TBL_NOTE_KEY_ID = "id";
    private static final String TBL_NOTE_KEY_TITLE = "title";
    private static final String TBL_NOTE_KEY_DESC = "Description";

    private static final String TABLE_CREATE_NOTE =
            "create table Note(id integer primary key autoincrement, "
                    + "title text, Description text);";


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static synchronized DatabaseHelper newInstance(Context context) {

        if (sDatabaseHelper == null)
            sDatabaseHelper = new DatabaseHelper(context);

        return sDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertNote(String strTitle, String strDescription) {
        mSqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TBL_NOTE_KEY_TITLE, strTitle);
        contentValues.put(TBL_NOTE_KEY_DESC, strDescription);
        mSqLiteDatabase.insertWithOnConflict(TABLE_NOTE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

        mSqLiteDatabase.close();
    }
    
       /*
     * Using Model Class
     * */
    public void insertNote(NoteModel noteModel) {
        mSqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_NOTE_KEY_TITLE, noteModel.getTitle());
        values.put(TBL_NOTE_KEY_DESC, noteModel.getDescription());
        mSqLiteDatabase.insertWithOnConflict(TABLE_NOTE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        mSqLiteDatabase.close();
        SQLiteDatabase.releaseMemory();
    }

    public ArrayList<NoteModel> getAllNotes() {
        final Cursor cur;
        mSqLiteDatabase = getReadableDatabase();
        ArrayList<NoteModel> noteData = new ArrayList<NoteModel>();

        NoteModel noteModel;
        cur = mSqLiteDatabase.rawQuery("select * from  Note ", null);

        if (cur.getCount() > 0) {
            if (cur != null) {
                if (cur.moveToFirst()) {

                    for (int i = 0; i < cur.getCount(); i++) {
                        noteModel = new NoteModel();

                        noteModel.setId(cur.getInt(cur.getColumnIndex("id")));
                        noteModel.setTitle(cur.getString(cur.getColumnIndex("title")));
                        noteModel.setDescription(cur.getString(cur.getColumnIndex("Description")));
                        noteData.add(noteModel);
                        cur.moveToNext();
                    }
                }
            }
        }
        cur.close();
        mSqLiteDatabase.close();
        SQLiteDatabase.releaseMemory();
        return noteData;
    }

    public boolean updateNote(String strNoteId, String strNoteTitle, String strNoteDesc) {
        mSqLiteDatabase = getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(TBL_NOTE_KEY_TITLE, strNoteTitle);
        args.put(TBL_NOTE_KEY_DESC, strNoteDesc);
        return mSqLiteDatabase.update(TABLE_NOTE, args, TBL_NOTE_KEY_ID + "=" + strNoteId, null) > 0;
    }

    public void deleteNote(int id) {
        mSqLiteDatabase = getWritableDatabase();
        mSqLiteDatabase.delete(TABLE_NOTE, TBL_NOTE_KEY_ID + " = ?"
                , new String[]{"" + id});
        mSqLiteDatabase.close();
    }

    public boolean checkIsNoteIdExist(String fieldValue) {
        mSqLiteDatabase = getWritableDatabase();
        String Query = "Select * from " + TABLE_NOTE + " where " + TBL_NOTE_KEY_ID + " = " + fieldValue;
        Cursor cursor = mSqLiteDatabase.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    // Get Notes by Id returning Arraylist
    public ArrayList<NoteModel> getNoteById(String id) {
        final Cursor cur;
        mSqLiteDatabase = getReadableDatabase();
        ArrayList<NoteModel> noteData = new ArrayList<NoteModel>();

        NoteModel noteModel;
        cur = mSqLiteDatabase.rawQuery("select * from  Note where id=" + id, null);

        if (cur.getCount() > 0) {
            if (cur != null) {
                if (cur.moveToFirst()) {

                    for (int i = 0; i < cur.getCount(); i++) {
                        noteModel = new NoteModel();

                        noteModel.setId(cur.getInt(cur.getColumnIndex("id")));
                        noteModel.setTitle(cur.getString(cur.getColumnIndex("title")));
                        noteModel.setDescription(cur.getString(cur.getColumnIndex("Description")));
                        noteData.add(noteModel);
                        cur.moveToNext();
                    }
                }
            }
        }
        cur.close();
        mSqLiteDatabase.close();
        SQLiteDatabase.releaseMemory();
        return noteData;
    }


}
