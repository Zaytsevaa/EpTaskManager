package com.zaytsevaa.eptaskmanager.db;

import androidx.room.TypeConverter;

import static com.zaytsevaa.eptaskmanager.db.TaskEntity.TaskStatus.ARRIVED;
import static com.zaytsevaa.eptaskmanager.db.TaskEntity.TaskStatus.CLOSED;
import static com.zaytsevaa.eptaskmanager.db.TaskEntity.TaskStatus.ENDED;
import static com.zaytsevaa.eptaskmanager.db.TaskEntity.TaskStatus.GET;
import static com.zaytsevaa.eptaskmanager.db.TaskEntity.TaskStatus.OPENED;
import static com.zaytsevaa.eptaskmanager.db.TaskEntity.TaskStatus.STARTED;

public class StatusConverter {
    @TypeConverter
    public static TaskEntity.TaskStatus toStatus(int status) {
        if (status == GET.getCode()) {
            return GET;
        } else if (status == STARTED.getCode()) {
            return STARTED;
        } else if (status == ARRIVED.getCode()) {
            return ARRIVED;
        } else if (status == CLOSED.getCode()) {
            return CLOSED;
        } else if (status == OPENED.getCode()) {
            return OPENED;
        } else if (status == ENDED.getCode()) {
            return ENDED;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    public static Integer toInteger(TaskEntity.TaskStatus status) {
        return status.getCode();
    }
}
