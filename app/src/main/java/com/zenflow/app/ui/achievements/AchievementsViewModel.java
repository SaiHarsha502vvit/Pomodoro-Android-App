package com.zenflow.app.ui.achievements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zenflow.app.data.local.entities.AchievementEntity;
import com.zenflow.app.repository.ZenFlowRepository;

import java.util.List;

public class AchievementsViewModel extends ViewModel {

    private final ZenFlowRepository repository;
    private final LiveData<List<AchievementEntity>> allAchievements;
    private final LiveData<List<AchievementEntity>> unlockedAchievements;
    private final LiveData<List<AchievementEntity>> lockedAchievements;
    private final LiveData<Integer> unlockedCount;

    public AchievementsViewModel(ZenFlowRepository repository) {
        this.repository = repository;
        this.allAchievements = repository.getAllAchievements();
        this.unlockedAchievements = repository.getUnlockedAchievements();
        this.lockedAchievements = repository.getLockedAchievements();
        this.unlockedCount = repository.getUnlockedAchievementCount();
    }

    public LiveData<List<AchievementEntity>> getAllAchievements() {
        return allAchievements;
    }

    public LiveData<List<AchievementEntity>> getUnlockedAchievements() {
        return unlockedAchievements;
    }

    public LiveData<List<AchievementEntity>> getLockedAchievements() {
        return lockedAchievements;
    }

    public LiveData<Integer> getUnlockedCount() {
        return unlockedCount;
    }
}
