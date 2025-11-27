package com.zenflow.app.repository;

import androidx.lifecycle.LiveData;

import com.zenflow.app.data.local.ZenFlowDatabase;
import com.zenflow.app.data.local.dao.AchievementDao;
import com.zenflow.app.data.local.dao.MoodLogDao;
import com.zenflow.app.data.local.dao.SessionDao;
import com.zenflow.app.data.local.dao.TaskDao;
import com.zenflow.app.data.local.entities.AchievementEntity;
import com.zenflow.app.data.local.entities.MoodLogEntity;
import com.zenflow.app.data.local.entities.SessionEntity;
import com.zenflow.app.data.local.entities.TaskEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ZenFlowRepository {

    private final TaskDao taskDao;
    private final SessionDao sessionDao;
    private final MoodLogDao moodLogDao;
    private final AchievementDao achievementDao;
    private final Executor executor;

    public ZenFlowRepository(ZenFlowDatabase db) {
        this.taskDao = db.taskDao();
        this.sessionDao = db.sessionDao();
        this.moodLogDao = db.moodLogDao();
        this.achievementDao = db.achievementDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    // ============ Task Operations ============

    public LiveData<List<TaskEntity>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public LiveData<List<TaskEntity>> getActiveTasks() {
        return taskDao.getActiveTasks();
    }

    public LiveData<List<TaskEntity>> getCompletedTasks() {
        return taskDao.getCompletedTasks();
    }

    public LiveData<TaskEntity> getTaskById(int id) {
        return taskDao.getTaskById(id);
    }

    public LiveData<List<TaskEntity>> getTasksByCategory(String category) {
        return taskDao.getTasksByCategory(category);
    }

    public LiveData<Integer> getCompletedTaskCount() {
        return taskDao.getCompletedTaskCount();
    }

    public void insertTask(TaskEntity task) {
        executor.execute(() -> taskDao.insert(task));
    }

    public void updateTask(TaskEntity task) {
        executor.execute(() -> taskDao.update(task));
    }

    public void deleteTask(TaskEntity task) {
        executor.execute(() -> taskDao.delete(task));
    }

    public void deleteAllCompletedTasks() {
        executor.execute(() -> taskDao.deleteAllCompletedTasks());
    }

    // ============ Session Operations ============

    public LiveData<List<SessionEntity>> getAllSessions() {
        return sessionDao.getAllSessions();
    }

    public LiveData<List<SessionEntity>> getSessionsForTask(int taskId) {
        return sessionDao.getSessionsForTask(taskId);
    }

    public LiveData<List<SessionEntity>> getRecentCompletedSessions(int limit) {
        return sessionDao.getRecentCompletedSessions(limit);
    }

    public LiveData<Integer> getTotalFocusSessions() {
        return sessionDao.getTotalFocusSessions();
    }

    public LiveData<Integer> getTotalFocusMinutes() {
        return sessionDao.getTotalFocusMinutes();
    }

    public LiveData<List<SessionEntity>> getSessionsForDay(long startOfDay, long endOfDay) {
        return sessionDao.getSessionsForDay(startOfDay, endOfDay);
    }

    public LiveData<List<SessionEntity>> getFocusSessionsSince(long startTime) {
        return sessionDao.getFocusSessionsSince(startTime);
    }

    public void insertSession(SessionEntity session, OnSessionInsertedListener listener) {
        executor.execute(() -> {
            long id = sessionDao.insert(session);
            if (listener != null) {
                listener.onSessionInserted((int) id);
            }
        });
    }

    public void updateSession(SessionEntity session) {
        executor.execute(() -> sessionDao.update(session));
    }

    public void deleteSession(SessionEntity session) {
        executor.execute(() -> sessionDao.delete(session));
    }

    public interface OnSessionInsertedListener {
        void onSessionInserted(int sessionId);
    }

    // ============ MoodLog Operations ============

    public LiveData<List<MoodLogEntity>> getAllMoodLogs() {
        return moodLogDao.getAllMoodLogs();
    }

    public LiveData<MoodLogEntity> getMoodLogForSession(int sessionId) {
        return moodLogDao.getMoodLogForSession(sessionId);
    }

    public LiveData<List<MoodLogEntity>> getRecentMoodLogs(int limit) {
        return moodLogDao.getRecentMoodLogs(limit);
    }

    public LiveData<Integer> getMoodLogCount() {
        return moodLogDao.getMoodLogCount();
    }

    public LiveData<List<MoodLogEntity>> getMoodLogsSince(long startTime) {
        return moodLogDao.getMoodLogsSince(startTime);
    }

    public void insertMoodLog(MoodLogEntity moodLog) {
        executor.execute(() -> moodLogDao.insert(moodLog));
    }

    public void updateMoodLog(MoodLogEntity moodLog) {
        executor.execute(() -> moodLogDao.update(moodLog));
    }

    public void deleteMoodLog(MoodLogEntity moodLog) {
        executor.execute(() -> moodLogDao.delete(moodLog));
    }

    // ============ Achievement Operations ============

    public LiveData<List<AchievementEntity>> getAllAchievements() {
        return achievementDao.getAllAchievements();
    }

    public LiveData<List<AchievementEntity>> getUnlockedAchievements() {
        return achievementDao.getUnlockedAchievements();
    }

    public LiveData<List<AchievementEntity>> getLockedAchievements() {
        return achievementDao.getLockedAchievements();
    }

    public LiveData<Integer> getUnlockedAchievementCount() {
        return achievementDao.getUnlockedAchievementCount();
    }

    public void insertAchievement(AchievementEntity achievement) {
        executor.execute(() -> achievementDao.insert(achievement));
    }

    public void updateAchievement(AchievementEntity achievement) {
        executor.execute(() -> achievementDao.update(achievement));
    }

    public void unlockAchievement(String key) {
        executor.execute(() -> {
            AchievementEntity achievement = achievementDao.getAchievementByKey(key);
            if (achievement != null && !achievement.unlocked) {
                achievement.unlocked = true;
                achievement.unlockedAt = System.currentTimeMillis();
                achievementDao.update(achievement);
            }
        });
    }

    public void initializeAchievements() {
        executor.execute(() -> {
            // Check if achievements already exist
            if (achievementDao.getAchievementByKey("FIRST_FOCUS") == null) {
                // Initialize default achievements
                insertDefaultAchievements();
            }
        });
    }

    private void insertDefaultAchievements() {
        String[][] achievements = {
            {"FIRST_FOCUS", "First Focus", "Complete your first focus session"},
            {"FOCUS_10", "Getting Started", "Complete 10 focus sessions"},
            {"FOCUS_50", "Focused Mind", "Complete 50 focus sessions"},
            {"FOCUS_100", "Master of Focus", "Complete 100 focus sessions"},
            {"STREAK_3", "3-Day Streak", "Focus for 3 days in a row"},
            {"STREAK_7", "Week Warrior", "Focus for 7 days in a row"},
            {"TASK_10", "Task Crusher", "Complete 10 tasks"},
            {"TASK_50", "Productivity Pro", "Complete 50 tasks"},
            {"MOOD_10", "Self-Aware", "Log your mood 10 times"},
            {"EARLY_BIRD", "Early Bird", "Start a session before 8 AM"}
        };

        for (String[] achievement : achievements) {
            AchievementEntity entity = new AchievementEntity();
            entity.key = achievement[0];
            entity.title = achievement[1];
            entity.description = achievement[2];
            entity.unlocked = false;
            entity.unlockedAt = 0;
            achievementDao.insert(entity);
        }
    }
}
