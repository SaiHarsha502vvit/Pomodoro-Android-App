package com.zenflow.app.ui.achievements;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zenflow.app.repository.ZenFlowRepository;

public class AchievementsViewModelFactory implements ViewModelProvider.Factory {

    private final ZenFlowRepository repository;

    public AchievementsViewModelFactory(ZenFlowRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AchievementsViewModel.class)) {
            return (T) new AchievementsViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
