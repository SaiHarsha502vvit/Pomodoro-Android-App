package com.zenflow.app.timer;

public class TimerState {

    public enum Phase {
        IDLE,
        FOCUS,
        SHORT_BREAK,
        LONG_BREAK
    }

    public Phase phase;
    public int remainingSeconds;
    public int cycleNumber;
    public boolean running;
    public boolean paused;

    public TimerState() {
        this.phase = Phase.IDLE;
        this.remainingSeconds = 0;
        this.cycleNumber = 0;
        this.running = false;
        this.paused = false;
    }

    public TimerState(Phase phase, int remainingSeconds, int cycleNumber, boolean running, boolean paused) {
        this.phase = phase;
        this.remainingSeconds = remainingSeconds;
        this.cycleNumber = cycleNumber;
        this.running = running;
        this.paused = paused;
    }

    public String getFormattedTime() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}

