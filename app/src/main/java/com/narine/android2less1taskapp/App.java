package com.narine.android2less1taskapp;

import android.app.Application;

import androidx.room.Room;

import com.narine.android2less1taskapp.room.AppDatabase;

public class App extends Application {

    private static AppDatabase appDatabase;
    private static App instance;

    public void onCreate() {
        super.onCreate();
        instance = this;
        appDatabase = Room
                .databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public static AppDatabase getAppDatabase(){
        return appDatabase;
    }

    public static App getInstance(){
        return instance;
    }
}
