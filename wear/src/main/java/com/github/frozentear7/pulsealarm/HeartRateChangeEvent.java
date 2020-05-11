package com.github.frozentear7.pulsealarm;

public class HeartRateChangeEvent {
    private final int heartRate;

    public HeartRateChangeEvent(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getHeartRate() {
        return heartRate;
    }
}
