package com.zenflow.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zenflow.app.data.local.entities.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long insert(TaskEntity task);

    @Update
    void update(TaskEntity task);

    @Delete
    void delete(TaskEntity task);

    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    LiveData<List<TaskEntity>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE completed = 0 ORDER BY priority DESC")
    LiveData<List<TaskEntity>> getActiveTasks();

    @Query("SELECT * FROM tasks WHERE completed = 1 ORDER BY completedAt DESC")
    LiveData<List<TaskEntity>> getCompletedTasks();

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    LiveData<TaskEntity> getTaskById(int id);

    @Query("SELECT * FROM tasks WHERE category = :category ORDER BY createdAt DESC")
    LiveData<List<TaskEntity>> getTasksByCategory(String category);

    @Query("DELETE FROM tasks WHERE completed = 1")
    void deleteAllCompletedTasks();

    @Query("SELECT COUNT(*) FROM tasks WHERE completed = 1")
    LiveData<Integer> getCompletedTaskCount();
}
