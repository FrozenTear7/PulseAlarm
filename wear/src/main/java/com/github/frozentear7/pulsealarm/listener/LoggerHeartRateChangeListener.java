package com.github.frozentear7.pulsealarm.listener;

import android.util.Log;

import com.github.frozentear7.pulsealarm.HeartRateChangeEvent;

public class LoggerHeartRateChangeListener implements HeartRateChangeListener {
    private final String tag;

    public LoggerHeartRateChangeListener(String tag) {
        this.tag = tag;
    }

    @Override
    public void onHeartRateChanged(HeartRateChangeEvent heartRateChangeEvent) {
        Log.i(tag, String.valueOf(heartRateChangeEvent.getHeartRate()));
    }
}
