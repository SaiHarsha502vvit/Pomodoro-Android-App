package com.zenflow.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zenflow.app.data.local.entities.SessionEntity;

import java.util.List;

@Dao
public interface SessionDao {

    @Insert
    long insert(SessionEntity session);

    @Update
    void update(SessionEntity session);

    @Delete
    void delete(SessionEntity session);

    @Query("SELECT * FROM sessions ORDER BY startTime DESC")
    LiveData<List<SessionEntity>> getAllSessions();

    @Query("SELECT * FROM sessions WHERE taskId = :taskId ORDER BY startTime DESC")
    LiveData<List<SessionEntity>> getSessionsForTask(int taskId);

    @Query("SELECT * FROM sessions WHERE isCompleted = 1 ORDER BY startTime DESC LIMIT :limit")
    LiveData<List<SessionEntity>> getRecentCompletedSessions(int limit);

    @Query("SELECT COUNT(*) FROM sessions WHERE isFocusSession = 1 AND isCompleted = 1")
    LiveData<Integer> getTotalFocusSessions();

    @Query("SELECT SUM(durationMinutes) FROM sessions WHERE isFocusSession = 1 AND isCompleted = 1")
    LiveData<Integer> getTotalFocusMinutes();

    @Query("SELECT * FROM sessions WHERE startTime >= :startOfDay AND startTime < :endOfDay ORDER BY startTime DESC")
    LiveData<List<SessionEntity>> getSessionsForDay(long startOfDay, long endOfDay);

    @Query("SELECT * FROM sessions WHERE isFocusSession = 1 AND startTime >= :startTime")
    LiveData<List<SessionEntity>> getFocusSessionsSince(long startTime);
}
