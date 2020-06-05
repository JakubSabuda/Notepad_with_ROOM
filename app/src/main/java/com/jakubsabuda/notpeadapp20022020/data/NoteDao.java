package com.jakubsabuda.notpeadapp20022020.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jakubsabuda.notpeadapp20022020.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNotes(Note...notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Delete
    void delete(Note... notes);

    @Update
    void update(Note... notes);
}
