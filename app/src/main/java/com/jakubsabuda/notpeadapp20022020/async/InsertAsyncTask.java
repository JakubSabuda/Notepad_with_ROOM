package com.jakubsabuda.notpeadapp20022020.async;

import android.os.AsyncTask;

import com.jakubsabuda.notpeadapp20022020.data.NoteDao;
import com.jakubsabuda.notpeadapp20022020.model.Note;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao mNoteDao;

    public InsertAsyncTask(NoteDao dao){
        mNoteDao = dao;
    }



    @Override
    protected Void doInBackground(Note... notes) {
            mNoteDao.insertNotes(notes);
        return null;
    }
}
