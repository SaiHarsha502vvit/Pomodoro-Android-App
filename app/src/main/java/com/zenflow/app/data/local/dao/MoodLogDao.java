package com.zenflow.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zenflow.app.data.local.entities.MoodLogEntity;

import java.util.List;

@Dao
public interface MoodLogDao {

    @Insert
    long insert(MoodLogEntity moodLog);

    @Update
    void update(MoodLogEntity moodLog);

    @Delete
    void delete(MoodLogEntity moodLog);

    @Query("SELECT * FROM mood_logs ORDER BY timestamp DESC")
    LiveData<List<MoodLogEntity>> getAllMoodLogs();

    @Query("SELECT * FROM mood_logs WHERE sessionId = :sessionId LIMIT 1")
    LiveData<MoodLogEntity> getMoodLogForSession(int sessionId);

    @Query("SELECT * FROM mood_logs ORDER BY timestamp DESC LIMIT :limit")
    LiveData<List<MoodLogEntity>> getRecentMoodLogs(int limit);

    @Query("SELECT COUNT(*) FROM mood_logs")
    LiveData<Integer> getMoodLogCount();

    @Query("SELECT * FROM mood_logs WHERE timestamp >= :startTime ORDER BY timestamp DESC")
    LiveData<List<MoodLogEntity>> getMoodLogsSince(long startTime);
}
