package com.github.frozentear7.pulsealarm.manager;

import com.github.frozentear7.pulsealarm.listener.HeartRateChangeListener;

import java.util.LinkedList;
import java.util.List;

public abstract class HeartRateChangeListenerManager {
    List<HeartRateChangeListener> listeners = new LinkedList<>();

    public void registerListener(HeartRateChangeListener listener) {
        listeners.add(listener);
    }
}
