package com.zenflow.app.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskEntity {
    
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    @NonNull
    public String title;
    
    public String description;
    
    public int priority; // 1=Low, 2=Medium, 3=High
    
    public String category; // "Study", "Work", etc.
    
    public int estimatedPomodoros;
    
    public long createdAt;
    
    public boolean completed;
    
    public Long completedAt; // nullable
    
    public TaskEntity() {
        this.title = "";
    }
}

