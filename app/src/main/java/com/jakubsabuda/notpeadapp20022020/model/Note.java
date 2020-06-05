package com.jakubsabuda.notpeadapp20022020.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//Entity = Table
@Entity(tableName = "notes")

public class Note implements Parcelable {

    //Variables
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "timestamp")
    private String timestamp;

    @ColumnInfo(name = "content")
    private String content;

    @Ignore
    //Empty Constructor(Ignoring it)
    public Note() {
    }


    //Constructors
    public Note(String title, String timestamp, String content) {
        this.title = title;
        this.timestamp = timestamp;
        this.content = content;
    }



    //GETTERS AND SETTERS

    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        timestamp = in.readString();
        content = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    //Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(timestamp);
        dest.writeString(content);
    }


    //TO string

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
