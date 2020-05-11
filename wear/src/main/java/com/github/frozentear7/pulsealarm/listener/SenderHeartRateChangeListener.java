package com.github.frozentear7.pulsealarm.listener;

import android.support.wearable.activity.WearableActivity;

import com.github.frozentear7.pulsealarm.HeartRateChangeEvent;
import com.github.frozentear7.pulsealarm.SendHeartRateRunnable;

public class SenderHeartRateChangeListener implements HeartRateChangeListener {
    private final WearableActivity wearableActivity;

    public SenderHeartRateChangeListener(WearableActivity wearableActivity) {
        this.wearableActivity = wearableActivity;
    }

    @Override
    public void onHeartRateChanged(HeartRateChangeEvent heartRateChangeEvent) {
        new Thread(new SendHeartRateRunnable(wearableActivity.getApplicationContext(),
                heartRateChangeEvent.getHeartRate())).start();
    }
}
