package com.zenflow.app.ui.tasks;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zenflow.app.repository.ZenFlowRepository;

public class TaskListViewModelFactory implements ViewModelProvider.Factory {

    private final ZenFlowRepository repository;

    public TaskListViewModelFactory(ZenFlowRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskListViewModel.class)) {
            return (T) new TaskListViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
