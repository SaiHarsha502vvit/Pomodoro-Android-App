package com.zenflow.app.ui.stats;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zenflow.app.repository.ZenFlowRepository;

public class StatsViewModelFactory implements ViewModelProvider.Factory {

    private final ZenFlowRepository repository;

    public StatsViewModelFactory(ZenFlowRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StatsViewModel.class)) {
            return (T) new StatsViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
