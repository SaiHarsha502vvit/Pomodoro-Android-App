package com.zenflow.app.ui.stats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zenflow.app.data.local.entities.SessionEntity;
import com.zenflow.app.repository.ZenFlowRepository;

import java.util.List;

public class StatsViewModel extends ViewModel {

    private final ZenFlowRepository repository;
    private final LiveData<Integer> totalFocusSessions;
    private final LiveData<Integer> totalFocusMinutes;
    private final LiveData<Integer> completedTaskCount;

    public StatsViewModel(ZenFlowRepository repository) {
        this.repository = repository;
        this.totalFocusSessions = repository.getTotalFocusSessions();
        this.totalFocusMinutes = repository.getTotalFocusMinutes();
        this.completedTaskCount = repository.getCompletedTaskCount();
    }

    public LiveData<Integer> getTotalFocusSessions() {
        return totalFocusSessions;
    }

    public LiveData<Integer> getTotalFocusMinutes() {
        return totalFocusMinutes;
    }

    public LiveData<Integer> getCompletedTaskCount() {
        return completedTaskCount;
    }

    public LiveData<List<SessionEntity>> getRecentSessions(int limit) {
        return repository.getRecentCompletedSessions(limit);
    }

    public LiveData<List<SessionEntity>> getSessionsForDay(long startOfDay, long endOfDay) {
        return repository.getSessionsForDay(startOfDay, endOfDay);
    }

    public LiveData<List<SessionEntity>> getFocusSessionsSince(long startTime) {
        return repository.getFocusSessionsSince(startTime);
    }
}
