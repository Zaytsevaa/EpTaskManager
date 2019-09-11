package com.zaytsevaa.eptaskmanager.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskEntity taskEntity);

    @Update
    void updateTask(TaskEntity taskEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTasks(List<TaskEntity> tasks);

    @Delete
    void deleteTask(TaskEntity taskEntity);

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TaskEntity> getTaskById(int id);

    @Query("SELECT * FROM tasks ORDER BY taskGetTime DESC")
    LiveData<List<TaskEntity>> getAllTasks();

    @Query("DELETE FROM tasks")
    int deleteAllTasks();

    @Query("SELECT COUNT(*) FROM tasks")
    int getCountTasks();

    @Query("DELETE FROM tasks WHERE id = :taskId")
    void deleteTaskById(int taskId);
}
