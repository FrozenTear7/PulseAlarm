package com.github.frozentear7.pulsealarm;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.ExecutionException;

public class MainActivity extends WearableActivity implements SensorEventListener {
    private static final String TAG = "MainActivity";

    private TextView heartRateTextView;
    SensorManager mSensorManager;
    Sensor mHeartRateSensor;
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heartRateTextView = findViewById(R.id.heartRateTextView);
        heartRateTextView.setText("Test");

        Thread thread = new Thread(new SendHeartRateRunnable(getApplicationContext()));
        thread.start();

        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        assert mSensorManager != null;
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "LISTENER REGISTERED.");

        mSensorManager.registerListener(sensorEventListener, mHeartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void onResume() {
        super.onResume();
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            String msg = "" + (int) event.values[0];
            heartRateTextView.setText(msg);
            Log.d(TAG, msg);
            System.out.println(msg);
        } else {
            Log.d(TAG, "Unknown sensor type");
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }
}
