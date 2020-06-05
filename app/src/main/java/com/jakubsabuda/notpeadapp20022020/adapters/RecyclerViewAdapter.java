package com.jakubsabuda.notpeadapp20022020.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jakubsabuda.notpeadapp20022020.R;
import com.jakubsabuda.notpeadapp20022020.model.Note;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Note> mNotes = new ArrayList<>();
    private OnNoteListener mOnNoteListener;
    public RecyclerViewAdapter(ArrayList<Note> notes, OnNoteListener onNoteListener) {
        this.mNotes = notes;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.timestamp.setText(mNotes.get(position).getTimestamp());
        holder.title.setText(mNotes.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView timestamp;
        OnNoteListener mOnNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title = itemView.findViewById(R.id.title_textView);
            timestamp = itemView.findViewById(R.id.date_textView);
            mOnNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    //Creating interface to navigate to another activity
    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
