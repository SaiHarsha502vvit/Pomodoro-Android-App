package com.zenflow.app.ui.mood;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zenflow.app.data.local.entities.MoodLogEntity;
import com.zenflow.app.repository.ZenFlowRepository;

import java.util.List;

public class MoodViewModel extends ViewModel {

    private final ZenFlowRepository repository;
    private final LiveData<List<MoodLogEntity>> allMoodLogs;

    public MoodViewModel(ZenFlowRepository repository) {
        this.repository = repository;
        this.allMoodLogs = repository.getAllMoodLogs();
    }

    public LiveData<List<MoodLogEntity>> getAllMoodLogs() {
        return allMoodLogs;
    }

    public void logMood(int sessionId, String moodBefore, String moodAfter, String note) {
        MoodLogEntity moodLog = new MoodLogEntity();
        moodLog.sessionId = sessionId;
        moodLog.moodBefore = moodBefore;
        moodLog.moodAfter = moodAfter;
        moodLog.note = note;

        repository.insertMoodLog(moodLog);
    }
}

