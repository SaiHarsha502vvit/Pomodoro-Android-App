package com.zenflow.app.timer;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PomodoroEngine {

    private int focusDurationMinutes;
    private int shortBreakDurationMinutes;
    private int longBreakDurationMinutes;
    private int cyclesBeforeLongBreak;

    private TimerState currentState;
    private MutableLiveData<TimerState> timerStateLiveData;
    private CountDownTimer countDownTimer;

    private int currentCycle;
    private long sessionStartTime;

    private TimerCallback callback;

    public PomodoroEngine(int focusMin, int shortBreakMin, int longBreakMin, int cyclesBeforeLong) {
        this.focusDurationMinutes = focusMin;
        this.shortBreakDurationMinutes = shortBreakMin;
        this.longBreakDurationMinutes = longBreakMin;
        this.cyclesBeforeLongBreak = cyclesBeforeLong;

        this.currentState = new TimerState();
        this.timerStateLiveData = new MutableLiveData<>(currentState);
        this.currentCycle = 0;
    }

    public LiveData<TimerState> getTimerState() {
        return timerStateLiveData;
    }

    public void setCallback(TimerCallback callback) {
        this.callback = callback;
    }

    public void setDurations(int focusMin, int shortBreakMin, int longBreakMin, int cyclesBeforeLong) {
        this.focusDurationMinutes = focusMin;
        this.shortBreakDurationMinutes = shortBreakMin;
        this.longBreakDurationMinutes = longBreakMin;
        this.cyclesBeforeLongBreak = cyclesBeforeLong;
    }

    public void startFocus() {
        currentCycle++;
        sessionStartTime = System.currentTimeMillis();
        startTimer(TimerState.Phase.FOCUS, focusDurationMinutes * 60);
    }

    public void startShortBreak() {
        startTimer(TimerState.Phase.SHORT_BREAK, shortBreakDurationMinutes * 60);
    }

    public void startLongBreak() {
        startTimer(TimerState.Phase.LONG_BREAK, longBreakDurationMinutes * 60);
    }

    public void pauseResume() {
        if (currentState.running) {
            if (currentState.paused) {
                resume();
            } else {
                pause();
            }
        }
    }

    public void pause() {
        if (currentState.running && !currentState.paused) {
            countDownTimer.cancel();
            currentState.paused = true;
            timerStateLiveData.postValue(new TimerState(
                currentState.phase,
                currentState.remainingSeconds,
                currentState.cycleNumber,
                true,
                true
            ));
        }
    }

    public void resume() {
        if (currentState.running && currentState.paused) {
            currentState.paused = false;
            startTimer(currentState.phase, currentState.remainingSeconds);
        }
    }

    public void stop() {
        stopTimer();
        currentCycle = 0;
        currentState = new TimerState();
        timerStateLiveData.postValue(currentState);
    }

    public void reset() {
        stopTimer();
        currentCycle = 0;
        currentState = new TimerState();
        timerStateLiveData.postValue(currentState);
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        currentState.running = false;
        currentState.paused = false;
    }

    private void startTimer(TimerState.Phase phase, int totalSeconds) {
        stopTimer();

        currentState.phase = phase;
        currentState.remainingSeconds = totalSeconds;
        currentState.cycleNumber = currentCycle;
        currentState.running = true;
        currentState.paused = false;

        countDownTimer = new CountDownTimer(totalSeconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentState.remainingSeconds = (int) (millisUntilFinished / 1000);
                timerStateLiveData.postValue(new TimerState(
                    currentState.phase,
                    currentState.remainingSeconds,
                    currentState.cycleNumber,
                    currentState.running,
                    currentState.paused
                ));
            }

            @Override
            public void onFinish() {
                currentState.remainingSeconds = 0;
                currentState.running = false;
                timerStateLiveData.postValue(new TimerState(
                    currentState.phase,
                    0,
                    currentState.cycleNumber,
                    false,
                    false
                ));

                if (callback != null) {
                    callback.onTimerComplete(currentState.phase);
                }

                handleTimerCompletion();
            }
        };

        countDownTimer.start();
        timerStateLiveData.postValue(new TimerState(
            currentState.phase,
            currentState.remainingSeconds,
            currentState.cycleNumber,
            currentState.running,
            currentState.paused
        ));
    }

    private void handleTimerCompletion() {
        TimerState.Phase completedPhase = currentState.phase;

        if (completedPhase == TimerState.Phase.FOCUS) {
            // Focus session completed, suggest break
            if (callback != null) {
                if (currentCycle % cyclesBeforeLongBreak == 0) {
                    callback.onSuggestLongBreak();
                } else {
                    callback.onSuggestShortBreak();
                }
            }
        } else {
            // Break completed, suggest focus
            if (callback != null) {
                callback.onSuggestFocus();
            }
        }
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public long getSessionStartTime() {
        return sessionStartTime;
    }

    public boolean isRunning() {
        return currentState.running;
    }

    public boolean isPaused() {
        return currentState.paused;
    }

    public TimerState.Phase getCurrentPhase() {
        return currentState.phase;
    }

    // Callback interface
    public interface TimerCallback {
        void onTimerComplete(TimerState.Phase phase);
        void onSuggestShortBreak();
        void onSuggestLongBreak();
        void onSuggestFocus();
    }
}
