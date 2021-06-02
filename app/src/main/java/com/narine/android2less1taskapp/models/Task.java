package com.narine.android2less1taskapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity

public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;

    public Task(
            String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
