package com.zenflow.app.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsPreferences {

    private static final String PREF_NAME = "zenflow_prefs";
    private static final String KEY_FOCUS_MIN = "focus_minutes";
    private static final String KEY_SHORT_BREAK_MIN = "short_break_minutes";
    private static final String KEY_LONG_BREAK_MIN = "long_break_minutes";
    private static final String KEY_CYCLES_BEFORE_LONG = "cycles_before_long_break";
    private static final String KEY_SOUND_ENABLED = "sound_enabled";
    private static final String KEY_VIBRATION_ENABLED = "vibration_enabled";
    private static final String KEY_THEME_MODE = "theme_mode";
    private static final String KEY_DAILY_GOAL = "daily_goal_sessions";

    private final SharedPreferences prefs;

    public SettingsPreferences(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Focus duration
    public int getFocusMinutes() {
        return prefs.getInt(KEY_FOCUS_MIN, 25);
    }

    public void setFocusMinutes(int minutes) {
        prefs.edit().putInt(KEY_FOCUS_MIN, minutes).apply();
    }

    // Short break duration
    public int getShortBreakMinutes() {
        return prefs.getInt(KEY_SHORT_BREAK_MIN, 5);
    }

    public void setShortBreakMinutes(int minutes) {
        prefs.edit().putInt(KEY_SHORT_BREAK_MIN, minutes).apply();
    }

    // Long break duration
    public int getLongBreakMinutes() {
        return prefs.getInt(KEY_LONG_BREAK_MIN, 15);
    }

    public void setLongBreakMinutes(int minutes) {
        prefs.edit().putInt(KEY_LONG_BREAK_MIN, minutes).apply();
    }

    // Cycles before long break
    public int getCyclesBeforeLongBreak() {
        return prefs.getInt(KEY_CYCLES_BEFORE_LONG, 4);
    }

    public void setCyclesBeforeLongBreak(int cycles) {
        prefs.edit().putInt(KEY_CYCLES_BEFORE_LONG, cycles).apply();
    }

    // Sound enabled
    public boolean isSoundEnabled() {
        return prefs.getBoolean(KEY_SOUND_ENABLED, true);
    }

    public void setSoundEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply();
    }

    // Vibration enabled
    public boolean isVibrationEnabled() {
        return prefs.getBoolean(KEY_VIBRATION_ENABLED, true);
    }

    public void setVibrationEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_VIBRATION_ENABLED, enabled).apply();
    }

    // Theme mode (0=System, 1=Light, 2=Dark)
    public int getThemeMode() {
        return prefs.getInt(KEY_THEME_MODE, 0);
    }

    public void setThemeMode(int mode) {
        prefs.edit().putInt(KEY_THEME_MODE, mode).apply();
    }

    // Daily goal
    public int getDailyGoal() {
        return prefs.getInt(KEY_DAILY_GOAL, 8);
    }

    public void setDailyGoal(int sessions) {
        prefs.edit().putInt(KEY_DAILY_GOAL, sessions).apply();
    }
}
