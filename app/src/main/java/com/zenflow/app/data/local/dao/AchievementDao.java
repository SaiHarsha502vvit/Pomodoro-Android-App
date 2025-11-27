package com.zenflow.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zenflow.app.data.local.entities.AchievementEntity;

import java.util.List;

@Dao
public interface AchievementDao {

    @Insert
    long insert(AchievementEntity achievement);

    @Update
    void update(AchievementEntity achievement);

    @Delete
    void delete(AchievementEntity achievement);

    @Query("SELECT * FROM achievements ORDER BY unlockedAt DESC")
    LiveData<List<AchievementEntity>> getAllAchievements();

    @Query("SELECT * FROM achievements WHERE unlocked = 1 ORDER BY unlockedAt DESC")
    LiveData<List<AchievementEntity>> getUnlockedAchievements();

    @Query("SELECT * FROM achievements WHERE unlocked = 0")
    LiveData<List<AchievementEntity>> getLockedAchievements();

    @Query("SELECT * FROM achievements WHERE key = :key LIMIT 1")
    AchievementEntity getAchievementByKey(String key);

    @Query("SELECT COUNT(*) FROM achievements WHERE unlocked = 1")
    LiveData<Integer> getUnlockedAchievementCount();
}
