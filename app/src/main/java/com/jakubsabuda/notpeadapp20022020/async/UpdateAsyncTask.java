package com.jakubsabuda.notpeadapp20022020.async;

import android.os.AsyncTask;

import com.jakubsabuda.notpeadapp20022020.data.NoteDao;
import com.jakubsabuda.notpeadapp20022020.model.Note;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao mNoteDao;

    public UpdateAsyncTask(NoteDao dao){
        mNoteDao = dao;
    }



    @Override
    protected Void doInBackground(Note... notes) {
            mNoteDao.update(notes);
        return null;
    }
}
