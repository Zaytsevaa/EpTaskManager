package com.zaytsevaa.eptaskmanager.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<TaskEntity>> mTasks;

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    public AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mTasks = getAllTasks();
    }

    private LiveData<List<TaskEntity>> getAllTasks() {
        return mDb.taskDao().getAllTasks();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<TaskEntity> tasks = new ArrayList<>();
                tasks.add(new TaskEntity(1,
                        "64-16",
                        getDate(0),
                        getDate(1),
                        getDate(2),
                        null,
                        null,
                        null,
                        "Comment1",
                        TaskEntity.TaskStatus.ARRIVED));
                tasks.add(new TaskEntity(2,
                        "64-17",
                        getDate(6),
                        getDate(7),
                        getDate(8),
                        null,
                        null,
                        getDate(11),
                        "Comment2",
                        TaskEntity.TaskStatus.ENDED));
                mDb.taskDao().insertAllTasks(tasks);
            }
        });
    }

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public LiveData<TaskEntity> getTaskById(final int taskId) {
        return mDb.taskDao().getTaskById(taskId);
    }

    public void insert(final TaskEntity task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.taskDao().insertTask(task);
            }
        });
    }

    public void deleteTaskById(final int taskId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.taskDao().deleteTaskById(taskId);
            }
        });
    }
}
