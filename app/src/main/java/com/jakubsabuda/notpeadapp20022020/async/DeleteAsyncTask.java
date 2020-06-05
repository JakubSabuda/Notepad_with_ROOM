package com.jakubsabuda.notpeadapp20022020.async;

import android.os.AsyncTask;

import com.jakubsabuda.notpeadapp20022020.data.NoteDao;
import com.jakubsabuda.notpeadapp20022020.model.Note;

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao mNoteDao;

    public DeleteAsyncTask(NoteDao dao){
        mNoteDao = dao;
    }



    @Override
    protected Void doInBackground(Note... notes) {
            mNoteDao.delete(notes);
        return null;
    }
}
