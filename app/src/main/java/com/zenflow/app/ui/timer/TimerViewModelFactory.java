package com.zenflow.app.ui.timer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zenflow.app.repository.ZenFlowRepository;

public class TimerViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final ZenFlowRepository repository;

    public TimerViewModelFactory(Application application, ZenFlowRepository repository) {
        this.application = application;
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TimerViewModel.class)) {
            return (T) new TimerViewModel(application, repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
