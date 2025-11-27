package com.zenflow.app.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zenflow.app.data.local.dao.AchievementDao;
import com.zenflow.app.data.local.dao.MoodLogDao;
import com.zenflow.app.data.local.dao.SessionDao;
import com.zenflow.app.data.local.dao.TaskDao;
import com.zenflow.app.data.local.entities.AchievementEntity;
import com.zenflow.app.data.local.entities.MoodLogEntity;
import com.zenflow.app.data.local.entities.SessionEntity;
import com.zenflow.app.data.local.entities.TaskEntity;

@Database(
        entities = {
                TaskEntity.class,
                SessionEntity.class,
                MoodLogEntity.class,
                AchievementEntity.class
        },
        version = 2,
        exportSchema = false
)
public abstract class ZenFlowDatabase extends RoomDatabase {

    private static volatile ZenFlowDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract SessionDao sessionDao();
    public abstract MoodLogDao moodLogDao();
    public abstract AchievementDao achievementDao();

    public static ZenFlowDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ZenFlowDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ZenFlowDatabase.class,
                                    "zenflow_db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

