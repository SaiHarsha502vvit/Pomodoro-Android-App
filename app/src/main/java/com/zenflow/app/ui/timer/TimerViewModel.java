package com.zenflow.app.ui.timer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zenflow.app.data.local.entities.SessionEntity;
import com.zenflow.app.data.local.entities.TaskEntity;
import com.zenflow.app.data.prefs.SettingsPreferences;
import com.zenflow.app.repository.ZenFlowRepository;
import com.zenflow.app.timer.PomodoroEngine;
import com.zenflow.app.timer.TimerState;

public class TimerViewModel extends AndroidViewModel implements PomodoroEngine.TimerCallback {

    private final ZenFlowRepository repository;
    private final SettingsPreferences settingsPreferences;
    private final PomodoroEngine pomodoroEngine;

    private MutableLiveData<TaskEntity> selectedTask = new MutableLiveData<>();
    private MutableLiveData<String> timerEvent = new MutableLiveData<>();
    private int currentSessionId = -1;

    public TimerViewModel(@NonNull Application application, ZenFlowRepository repository) {
        super(application);
        this.repository = repository;
        this.settingsPreferences = new SettingsPreferences(application);

        // Initialize pomodoro engine with saved settings
        this.pomodoroEngine = new PomodoroEngine(
            settingsPreferences.getFocusMinutes(),
            settingsPreferences.getShortBreakMinutes(),
            settingsPreferences.getLongBreakMinutes(),
            settingsPreferences.getCyclesBeforeLongBreak()
        );

        pomodoroEngine.setCallback(this);
    }

    public LiveData<TimerState> getTimerState() {
        return pomodoroEngine.getTimerState();
    }

    public LiveData<TaskEntity> getSelectedTask() {
        return selectedTask;
    }

    public LiveData<String> getTimerEvent() {
        return timerEvent;
    }

    public void setSelectedTask(TaskEntity task) {
        selectedTask.setValue(task);
    }

    public void startFocus() {
        pomodoroEngine.startFocus();

        // Create session record
        SessionEntity session = new SessionEntity();
        if (selectedTask.getValue() != null) {
            session.taskId = selectedTask.getValue().id;
        } else {
            session.taskId = null; // No specific task
        }
        session.startTime = System.currentTimeMillis();
        session.isFocusSession = true;
        session.isCompleted = false;
        session.durationMinutes = settingsPreferences.getFocusMinutes();

        repository.insertSession(session, sessionId -> {
            currentSessionId = sessionId;
        });
    }

    public void startShortBreak() {
        pomodoroEngine.startShortBreak();
        createBreakSession(settingsPreferences.getShortBreakMinutes());
    }

    public void startLongBreak() {
        pomodoroEngine.startLongBreak();
        createBreakSession(settingsPreferences.getLongBreakMinutes());
    }

    private void createBreakSession(int duration) {
        SessionEntity session = new SessionEntity();
        session.taskId = null; // Breaks are not associated with tasks
        session.startTime = System.currentTimeMillis();
        session.isFocusSession = false;
        session.isCompleted = false;
        session.durationMinutes = duration;

        repository.insertSession(session, sessionId -> {
            currentSessionId = sessionId;
        });
    }

    public void pauseTimer() {
        pomodoroEngine.pause();
    }

    public void resumeTimer() {
        pomodoroEngine.resume();
    }

    public void stopTimer() {
        pomodoroEngine.stop();
        currentSessionId = -1;
    }

    public void resetTimer() {
        pomodoroEngine.reset();
        currentSessionId = -1;
    }

    public void updateSettings() {
        pomodoroEngine.setDurations(
            settingsPreferences.getFocusMinutes(),
            settingsPreferences.getShortBreakMinutes(),
            settingsPreferences.getLongBreakMinutes(),
            settingsPreferences.getCyclesBeforeLongBreak()
        );
    }

    // PomodoroEngine.TimerCallback implementation

    @Override
    public void onTimerComplete(TimerState.Phase phase) {
        // Mark session as completed
        if (currentSessionId != -1) {
            // We would need to fetch and update the session
            // For simplicity, we'll emit an event
        }

        if (phase == TimerState.Phase.FOCUS) {
            timerEvent.postValue("FOCUS_COMPLETE");
            // Check for achievements
            repository.unlockAchievement("FIRST_FOCUS");
        } else {
            timerEvent.postValue("BREAK_COMPLETE");
        }
    }

    @Override
    public void onSuggestShortBreak() {
        timerEvent.postValue("SUGGEST_SHORT_BREAK");
    }

    @Override
    public void onSuggestLongBreak() {
        timerEvent.postValue("SUGGEST_LONG_BREAK");
    }

    @Override
    public void onSuggestFocus() {
        timerEvent.postValue("SUGGEST_FOCUS");
    }

    public int getCurrentCycle() {
        return pomodoroEngine.getCurrentCycle();
    }

    public void pauseResume() {
        pomodoroEngine.pauseResume();
    }

    public void stop() {
        pomodoroEngine.stop();
        if (currentSessionId != -1) {
            SessionEntity sessionToDelete = new SessionEntity();
            sessionToDelete.id = currentSessionId;
            repository.deleteSession(sessionToDelete);
            currentSessionId = -1;
        }
    }
}
