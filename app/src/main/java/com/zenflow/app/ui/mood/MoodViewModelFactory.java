package com.zenflow.app.ui.mood;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zenflow.app.repository.ZenFlowRepository;

public class MoodViewModelFactory implements ViewModelProvider.Factory {

    private final ZenFlowRepository repository;

    public MoodViewModelFactory(ZenFlowRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MoodViewModel.class)) {
            return (T) new MoodViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
