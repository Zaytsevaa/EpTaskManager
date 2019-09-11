package com.zaytsevaa.eptaskmanager.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String aptName;
    private Date taskGetTime;
    private Date taskStartTime;
    private Date taskArrivalTime;
    private Date taskAptCloseTime;
    private Date taskAptOpenTime;
    private Date taskEndTime;
    private String taskComment;
    private TaskStatus taskStatus;

    @Ignore
    public TaskEntity() {

    }

    public enum TaskStatus {
        GET(0),
        STARTED(1),
        ARRIVED(2),
        CLOSED(3),
        OPENED(4),
        ENDED(5);

        private int code;

        TaskStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }
    public TaskEntity(int id, String aptName, Date taskGetTime, Date taskStartTime, Date taskArrivalTime, Date taskAptCloseTime, Date taskAptOpenTime, Date taskEndTime, String taskComment, TaskStatus taskStatus) {
        this.id = id;
        this.aptName = aptName;
        this.taskGetTime = taskGetTime;
        this.taskStartTime = taskStartTime;
        this.taskArrivalTime = taskArrivalTime;
        this.taskAptCloseTime = taskAptCloseTime;
        this.taskAptOpenTime = taskAptOpenTime;
        this.taskEndTime = taskEndTime;
        this.taskComment = taskComment;
        this.taskStatus = taskStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public Date getTaskGetTime() {
        return taskGetTime;
    }

    public void setTaskGetTime(Date taskGetTime) {
        this.taskGetTime = taskGetTime;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskArrivalTime() {
        return taskArrivalTime;
    }

    public void setTaskArrivalTime(Date taskArrivalTime) {
        this.taskArrivalTime = taskArrivalTime;
    }

    public Date getTaskAptCloseTime() {
        return taskAptCloseTime;
    }

    public void setTaskAptCloseTime(Date taskAptCloseTime) {
        this.taskAptCloseTime = taskAptCloseTime;
    }

    public Date getTaskAptOpenTime() {
        return taskAptOpenTime;
    }

    public void setTaskAptOpenTime(Date taskAptOpenTime) {
        this.taskAptOpenTime = taskAptOpenTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(String taskComment) {
        this.taskComment = taskComment;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
