package com.github.frozentear7.pulsealarm.manager;

public class HeartRateChangeListenerManagerProvider {

    public static HeartRateChangeListenerManager getHeartRateChangeListenerManager() {
        return new MockHeartRateChangeListenerManager();
    }
}
