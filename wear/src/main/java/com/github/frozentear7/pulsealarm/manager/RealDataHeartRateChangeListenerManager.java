package com.github.frozentear7.pulsealarm.manager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import com.github.frozentear7.pulsealarm.HeartRateChangeEvent;
import com.github.frozentear7.pulsealarm.listener.HeartRateChangeListener;

public class RealDataHeartRateChangeListenerManager extends HeartRateChangeListenerManager implements SensorEventListener {
    private static final String TAG = "RealData";
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private SensorEventListener sensorEventListener;

    public RealDataHeartRateChangeListenerManager(WearableActivity wearableActivity) {
        mSensorManager = ((SensorManager) wearableActivity.getSystemService(Context.SENSOR_SERVICE));
        assert mSensorManager != null;
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "LISTENER REGISTERED.");

        mSensorManager.registerListener(sensorEventListener, mHeartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            int heartRate = (int) event.values[0];
            HeartRateChangeEvent heartRateChangeEvent = new HeartRateChangeEvent(heartRate);
            for (HeartRateChangeListener listener : listeners) {
                listener.onHeartRateChanged(heartRateChangeEvent);
            }
        } else {
            Log.d(TAG, "Unknown sensor type");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }
}
