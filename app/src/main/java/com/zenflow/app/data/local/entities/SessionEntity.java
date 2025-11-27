package com.zenflow.app.data.local.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "sessions",
    foreignKeys = @ForeignKey(
        entity = TaskEntity.class,
        parentColumns = "id",
        childColumns = "taskId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index(value = "taskId")}
)
public class SessionEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public Integer taskId; // nullable - can be null if no task is selected

    public long startTime;

    public Long endTime; // nullable

    public int durationMinutes;

    public boolean isCompleted;

    public boolean isFocusSession; // true for focus, false for break

    public SessionEntity() {
    }
}
