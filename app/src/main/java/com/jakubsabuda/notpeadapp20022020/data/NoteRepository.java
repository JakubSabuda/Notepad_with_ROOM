package com.jakubsabuda.notpeadapp20022020.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.jakubsabuda.notpeadapp20022020.async.DeleteAsyncTask;
import com.jakubsabuda.notpeadapp20022020.async.InsertAsyncTask;
import com.jakubsabuda.notpeadapp20022020.async.UpdateAsyncTask;
import com.jakubsabuda.notpeadapp20022020.model.Note;

import java.util.List;

public class NoteRepository {
    private NoteDatabase mNoteDatabase;

    //Creating Constructor with context
    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNote(Note note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void updateNote(Note note){
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);

    }

    public LiveData<List<Note>> retriveNote(){

        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote (Note note){
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);

    }
}
