package com.zenflow.app.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zenflow.app.data.local.entities.TaskEntity;
import com.zenflow.app.data.local.entities.SessionEntity;
import com.zenflow.app.repository.ZenFlowRepository;

import java.util.Calendar;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final ZenFlowRepository repository;
    private final LiveData<List<TaskEntity>> activeTasks;
    private final LiveData<Integer> totalFocusSessions;
    private final LiveData<Integer> unlockedAchievementCount;

    public MainViewModel(ZenFlowRepository repository) {
        this.repository = repository;
        this.activeTasks = repository.getActiveTasks();
        this.totalFocusSessions = repository.getTotalFocusSessions();
        this.unlockedAchievementCount = repository.getUnlockedAchievementCount();

        // Initialize achievements on first launch
        repository.initializeAchievements();
    }

    public LiveData<List<TaskEntity>> getActiveTasks() {
        return activeTasks;
    }

    public LiveData<Integer> getTotalFocusSessions() {
        return totalFocusSessions;
    }

    public LiveData<Integer> getUnlockedAchievementCount() {
        return unlockedAchievementCount;
    }

    public LiveData<List<SessionEntity>> getTodaysSessions() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startOfDay = calendar.getTimeInMillis();

        return repository.getFocusSessionsSince(startOfDay);
    }
}
