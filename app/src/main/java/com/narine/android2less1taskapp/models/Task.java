package com.narine.android2less1taskapp.models;

import java.io.Serializable;

public class Task implements Serializable {

    private String title;

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
