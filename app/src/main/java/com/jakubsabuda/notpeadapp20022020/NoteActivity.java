package com.jakubsabuda.notpeadapp20022020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakubsabuda.notpeadapp20022020.data.NoteRepository;
import com.jakubsabuda.notpeadapp20022020.model.Note;
import com.jakubsabuda.notpeadapp20022020.util.Utilitiy;

public class NoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    //UI
    private EditingLines mEditingLines;
    private EditText mEditTitle;
    private TextView mViewTitle;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck, mBackArrow;


    //Variables
    private boolean mIsNewNote;
    private Note initialNote;
    private GestureDetector mGestureDetector;
    private int mMode;
    private NoteRepository mNoteRepository;
    private Note mFinalNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEditingLines = findViewById(R.id.edit_text_content);
        mEditTitle = findViewById(R.id.title_edit);
        mViewTitle = findViewById(R.id.title_of_note);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);

        mNoteRepository = new NoteRepository(this);

        setListeners();
        if (getIncomingIntent()) {
            //Creating new note EIDTITNG MODE
            setNewNoteProperties();
            enableEditMode();
        } else {
            //Viewing note VIEWING MODE
            setNoteProperties();
            disableContentInteraction();
        }



    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_note")) {
            initialNote = getIntent().getParcelableExtra("selected_note");

            mFinalNote = new Note();
            mFinalNote.setTitle(initialNote.getTitle());
            mFinalNote.setContent(initialNote.getContent());
            mFinalNote.setTimestamp(initialNote.getTimestamp());
            mFinalNote.setId(initialNote.getId());
           // mFinalNote = getIntent().getParcelableExtra("selected_note");

            ///IT WAS DISABLED BEFORE
            mMode = EDIT_MODE_ENABLED;
            mIsNewNote = false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;
    }

    private void setListeners() {
        mEditingLines.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);

    }

    private void enableEditMode() {
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mViewTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void disableEditMode() {
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();
        String temporary= mEditingLines.getText().toString();
        temporary = temporary.replace("\n", "" );
        temporary = temporary.replace(" ", "" );
        if(temporary.length() > 0){
            mFinalNote.setTitle(mEditTitle.getText().toString());
            mFinalNote.setContent(mEditingLines.getText().toString());
            String timestamp = Utilitiy.getCurrentTimestamp();
            mFinalNote.setTimestamp(timestamp);

            if(!mFinalNote.getContent().equals(initialNote.getContent()) ||
                    !mFinalNote.getTitle().equals(initialNote.getTitle())){
                saveChanges();
            }
        }

    }

    private void saveChanges(){
        if(mIsNewNote){
         saveNewNote();
        }else {
            updateNote();
        }
    }

    private void saveNewNote(){
        mNoteRepository.insertNote(mFinalNote);
    }

    private void setNoteProperties() {
        mViewTitle.setText(initialNote.getTitle());
        mEditTitle.setText(initialNote.getTitle());
        mEditingLines.setText(initialNote.getContent());
    }

    private void setNewNoteProperties() {
        mViewTitle.setText("Note title");
        mEditTitle.setText("Note title");

        initialNote = new Note();
        mFinalNote = new Note();
        initialNote.setTitle("Note title");
        mFinalNote.setTitle("Note title");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    // Double tap methods
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }


    //On click implemented
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_check:
                hideKeyboard();
                disableEditMode();
                break;

            case R.id.title_of_note:
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
                break;

            case R.id.toolbar_back_arrow:
                finish();
                break;

        }

    }


    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED) {
            onClick(mCheck);
        } else {
            super.onBackPressed();
        }

    }

    private void disableContentInteraction(){
        mEditingLines.setKeyListener(null);
        mEditingLines.setFocusable(false);
        mEditingLines.setFocusableInTouchMode(false);
        mEditingLines.setCursorVisible(false);
        mEditingLines.clearFocus();
    }

    private void enableContentInteraction(){
        mEditingLines.setKeyListener(new EditText(this).getKeyListener());
        mEditingLines.setFocusable(true);
        mEditingLines.setFocusableInTouchMode(true);
        mEditingLines.setCursorVisible(true);
        mEditingLines.requestFocus();
    }

    private void hideKeyboard(){
        InputMethodManager im = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view == null){
            view = new View(this);
        }
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("mode", String.valueOf(mMode));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mMode = savedInstanceState.getInt("mode");
        if(mMode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }

    //TEXT WATCHER
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void updateNote(){
        mNoteRepository.updateNote(mFinalNote);
    }
}

