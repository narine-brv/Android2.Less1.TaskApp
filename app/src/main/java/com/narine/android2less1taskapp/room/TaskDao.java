package com.narine.android2less1taskapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.narine.android2less1taskapp.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task ORDER BY title ASC")
    LiveData <List<Task>> sortByAsc();

    @Insert
    void insert (Task task);

    @Delete
    void remove (Task task);
}
