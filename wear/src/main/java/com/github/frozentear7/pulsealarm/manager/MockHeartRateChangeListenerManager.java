package com.github.frozentear7.pulsealarm.manager;

import com.github.frozentear7.pulsealarm.HeartRateChangeEvent;
import com.github.frozentear7.pulsealarm.listener.HeartRateChangeListener;

import java.util.Random;

public class MockHeartRateChangeListenerManager extends HeartRateChangeListenerManager {
    public MockHeartRateChangeListenerManager() {
        Thread updaterThread = new MockHeartRateChangeUpdater();
        updaterThread.start();
    }

    private class MockHeartRateChangeUpdater extends Thread {
        private static final int DELAY = 1000;

        @Override
        public void run() {
            while (true) {
                HeartRateChangeEvent heartRateChangeEvent = new HeartRateChangeEvent(MockHeartRateChangeGenerator.next());
                for (HeartRateChangeListener listener : listeners) {
                    listener.onHeartRateChanged(heartRateChangeEvent);
                }
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class MockHeartRateChangeGenerator {
        private static final int MIN_HEART_RATE = 60;
        private static final int MAX_HEART_RATE = 200;
        private static final int MAX_HEART_RATE_DELTA = 10;
        private static Random random = new Random();
        private static int currentHeartRate = 80;

        public static int next() {
            currentHeartRate = currentHeartRate + getChangeMultiplier() * getChange();
            if (currentHeartRate > MAX_HEART_RATE) {
                currentHeartRate = MAX_HEART_RATE;
            }
            if (currentHeartRate < MIN_HEART_RATE) {
                currentHeartRate = MIN_HEART_RATE;
            }
            return currentHeartRate;
        }

        private static int getChange() {
            return random.nextInt(MAX_HEART_RATE_DELTA);
        }

        private static int getChangeMultiplier() {
            return random.nextInt(3) - 1; // could be -1, 0 or 1
        }
    }
}
