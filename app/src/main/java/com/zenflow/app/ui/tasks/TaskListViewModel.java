package com.zenflow.app.ui.tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zenflow.app.data.local.entities.TaskEntity;
import com.zenflow.app.repository.ZenFlowRepository;

import java.util.List;

public class TaskListViewModel extends ViewModel {

    private final ZenFlowRepository repository;
    private final LiveData<List<TaskEntity>> tasks;
    private final LiveData<List<TaskEntity>> activeTasks;

    public TaskListViewModel(ZenFlowRepository repository) {
        this.repository = repository;
        this.tasks = repository.getAllTasks();
        this.activeTasks = repository.getActiveTasks();
    }

    public LiveData<List<TaskEntity>> getTasks() {
        return tasks;
    }

    public LiveData<List<TaskEntity>> getActiveTasks() {
        return activeTasks;
    }

    public void addTask(String title, String description, String category, int priority, int estimatedPomodoros) {
        TaskEntity task = new TaskEntity();
        task.title = title;
        task.description = description;
        task.category = category;
        task.priority = priority;
        task.estimatedPomodoros = estimatedPomodoros;
        task.createdAt = System.currentTimeMillis();
        task.completed = false;

        repository.insertTask(task);
    }

    public void updateTask(TaskEntity task) {
        repository.updateTask(task);
    }

    public void deleteTask(TaskEntity task) {
        repository.deleteTask(task);
    }

    public void toggleTaskComplete(TaskEntity task) {
        task.completed = !task.completed;
        if (task.completed) {
            task.completedAt = System.currentTimeMillis();
        } else {
            task.completedAt = null;
        }
        repository.updateTask(task);
    }
}

