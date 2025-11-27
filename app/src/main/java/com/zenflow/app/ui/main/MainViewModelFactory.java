package com.zenflow.app.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zenflow.app.repository.ZenFlowRepository;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final ZenFlowRepository repository;

    public MainViewModelFactory(ZenFlowRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
