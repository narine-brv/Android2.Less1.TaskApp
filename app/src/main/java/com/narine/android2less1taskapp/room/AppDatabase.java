package com.narine.android2less1taskapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.narine.android2less1taskapp.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
