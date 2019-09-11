package com.zaytsevaa.eptaskmanager.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.zaytsevaa.eptaskmanager.R;
import com.zaytsevaa.eptaskmanager.db.AppRepository;
import com.zaytsevaa.eptaskmanager.db.TaskEntity;

import java.util.Date;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private AppRepository mRepository;
    public LiveData<List<TaskEntity>> mLiveTasks;
    public MediatorLiveData<TaskEntity> mCurrentTask;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mLiveTasks = mRepository.mTasks;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void setCurrentTask(int taskId) {
        if (mCurrentTask == null) {
            mCurrentTask = new MediatorLiveData<>();
        }
        mCurrentTask.addSource(mRepository.getTaskById(taskId), new Observer<TaskEntity>() {
            @Override
            public void onChanged(TaskEntity taskEntity) {
                mCurrentTask.setValue(taskEntity);
            }
        });
    }

    public void setCurrentTask() {
        if (mCurrentTask == null) {
            mCurrentTask = new MediatorLiveData<>();
        }
        MutableLiveData<TaskEntity> newTask = new MutableLiveData<>();
        TaskEntity task = new TaskEntity();
        task.setTaskGetTime(new Date());
        task.setTaskStatus(TaskEntity.TaskStatus.GET);
        newTask.setValue(task);
        mCurrentTask.addSource(newTask, new Observer<TaskEntity>() {
            @Override
            public void onChanged(TaskEntity taskEntity) {
                mCurrentTask.setValue(taskEntity);
            }
        });

    }

    public void saveTask() {
        mRepository.insert(mCurrentTask.getValue());
    }

    public void taskTimerSpinner() {
        final TaskEntity task = mCurrentTask.getValue();
        if (task.getTaskStatus() == null) {
            task.setTaskGetTime(new Date());
            task.setTaskStatus(TaskEntity.TaskStatus.GET);
        } else {
            switch (task.getTaskStatus()) {
                case GET: {
                    task.setTaskStartTime(new Date());
                    task.setTaskStatus(TaskEntity.TaskStatus.STARTED);
                    break;
                }
                case STARTED: {
                    task.setTaskArrivalTime(new Date());
                    task.setTaskStatus(TaskEntity.TaskStatus.ARRIVED);
                    break;
                }
                case CLOSED: {
                    task.setTaskAptOpenTime(new Date());
                    task.setTaskStatus(TaskEntity.TaskStatus.OPENED);
                    break;
                }
                case OPENED: {
                    task.setTaskEndTime(new Date());
                    task.setTaskStatus(TaskEntity.TaskStatus.ENDED);
                    break;
                }
                default: {
                    Toast.makeText(getApplication().getApplicationContext(), getApplication().getResources().getString(R.string.task_complete), Toast.LENGTH_SHORT).show();
                }
            }
        }
        mCurrentTask.setValue(task);
    }

    public void taskTimerSpinner(boolean aptClosed) {
        final TaskEntity task = mCurrentTask.getValue();

        if (aptClosed) {
            task.setTaskAptCloseTime(new Date());
            task.setTaskStatus(TaskEntity.TaskStatus.CLOSED);
        } else {
            task.setTaskEndTime(new Date());
            task.setTaskStatus(TaskEntity.TaskStatus.ENDED);
        }
        mCurrentTask.setValue(task);
    }

    public void deleteUserById(int taskId) {
        mRepository.deleteTaskById(taskId);
    }

    public void changeTime(int viewId, long time) {
        final TaskEntity task = mCurrentTask.getValue();
        switch (viewId) {
            case R.id.task_get_time: {
                task.setTaskGetTime(new Date(time));
                break;
            }
            case R.id.start_time: {
                task.setTaskStartTime(new Date(time));
                break;
            }
            case R.id.arrival_time: {
                task.setTaskArrivalTime(new Date(time));
                break;
            }
            case R.id.apt_open_time: {
                task.setTaskAptOpenTime(new Date(time));
                break;
            }
            case R.id.apt_closed_time: {
                task.setTaskAptCloseTime(new Date(time));
                break;
            }
            case R.id.end_time: {
                task.setTaskEndTime(new Date(time));
                break;
            }
            default: {
                Toast.makeText(getApplication().getApplicationContext(), "Something goes wrong", Toast.LENGTH_SHORT).show();
            }
        }
        mCurrentTask.setValue(task);
    }
}
