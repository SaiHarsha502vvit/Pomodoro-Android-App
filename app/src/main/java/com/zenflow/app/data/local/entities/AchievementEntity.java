package com.zenflow.app.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "achievements")
public class AchievementEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String key; // e.g., "FIRST_FOCUS", "WEEK_STREAK", "MASTER_100"

    public String title;

    public String description;

    public long unlockedAt;

    public boolean unlocked;

    public String iconName; // optional icon reference

    public AchievementEntity() {
        this.key = "";
    }
}
