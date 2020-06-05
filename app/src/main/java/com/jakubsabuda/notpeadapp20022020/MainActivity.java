
/*
 * Created by Jakub Sabuda
 * Copyright (c) 2020 .
 * Last modified 6/5/20 10:19 AM
 *
 */

package com.jakubsabuda.notpeadapp20022020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakubsabuda.notpeadapp20022020.adapters.RecyclerViewAdapter;
import com.jakubsabuda.notpeadapp20022020.data.NoteRepository;
import com.jakubsabuda.notpeadapp20022020.model.Note;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements
        RecyclerViewAdapter.OnNoteListener, View.OnClickListener {

    //UI
    private RecyclerView mRecyclerView;
    private FloatingActionButton floatingActionButton;

    //Variables
    private ArrayList<Note> mNotes = new ArrayList<>();
    private RecyclerViewAdapter mNoteRecyclerAdapter;
    private NoteRepository mNoteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.fab_button);
        floatingActionButton.setOnClickListener(this);

        mNoteRepository = new NoteRepository(this);

        initRecyclerView();
        retrieveNotes();

        //Toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main_activity));
        setTitle("Notepad");



    }


    //Setting up RecyclerView
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNoteRecyclerAdapter = new RecyclerViewAdapter(mNotes, this);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mRecyclerView);
    }

    //Implementation of clickListener created in RecyclerViewAdapter
    @Override
    public void onNoteClick(int position) {
        Log.d("onNote CLICKED: ", "Item clicled : " + String.valueOf(position));
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }



    private void deleteNote(Note note){
        mNotes.remove(note);
        mNoteRecyclerAdapter.notifyDataSetChanged();
        mNoteRepository.deleteNote(note);
    }





    private ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }





        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };




    private void retrieveNotes(){
        mNoteRepository.retriveNote().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if(mNotes.size()>0){
                    mNotes.clear();
                }
                if(notes != null){
                    mNotes.addAll(notes);
                }
                mNoteRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }




}
