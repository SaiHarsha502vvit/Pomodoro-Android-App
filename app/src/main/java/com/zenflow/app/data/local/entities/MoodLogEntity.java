package com.zenflow.app.data.local.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "mood_logs",
    foreignKeys = @ForeignKey(
        entity = SessionEntity.class,
        parentColumns = "id",
        childColumns = "sessionId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index(value = "sessionId")}
)
public class MoodLogEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int sessionId;

    public String moodBefore; // "Calm", "Anxious", "Energetic", "Tired", etc.

    public String moodAfter;

    public String note; // optional user note

    public long timestamp;

    public MoodLogEntity() {
    }
}
