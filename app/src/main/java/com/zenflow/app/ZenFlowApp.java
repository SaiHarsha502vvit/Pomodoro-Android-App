package com.zenflow.app;

import android.app.Application;

import com.zenflow.app.data.local.ZenFlowDatabase;
import com.zenflow.app.repository.ZenFlowRepository;

public class ZenFlowApp extends Application {

    private ZenFlowDatabase database;
    private ZenFlowRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize database
        database = ZenFlowDatabase.getInstance(this);

        // Initialize repository
        repository = new ZenFlowRepository(database);

        // Initialize achievements
        repository.initializeAchievements();
    }

    public ZenFlowRepository getRepository() {
        return repository;
    }

    public ZenFlowDatabase getDatabase() {
        return database;
    }
}
