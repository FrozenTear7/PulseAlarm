package com.github.frozentear7.pulsealarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.Objects;

public class MainActivity extends Activity implements DataClient.OnDataChangedListener {
    private static final String TAG = "MainActivity";

    private static final String HEARTRATE_PATH = "/heartrate";
    private static final String HEARTRATE_KEY = "heartrate";

    int prefLowerHeartRate;
    int prefUpperHeartRate;
    SharedPreferences sharedPref;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Wearable.getDataClient(this).addListener(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        prefLowerHeartRate = sharedPref.getInt(getString(R.string.prefLowerHeartRate), 60);
        prefUpperHeartRate = sharedPref.getInt(getString(R.string.prefUpperHeartRate), 140);

        Log.i(TAG, "Lower heartRate: " + prefLowerHeartRate);
        Log.i(TAG, "Upper heartRate: " + prefUpperHeartRate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Resume");

        prefLowerHeartRate = sharedPref.getInt(getString(R.string.prefLowerHeartRate), 60);
        prefUpperHeartRate = sharedPref.getInt(getString(R.string.prefUpperHeartRate), 140);

        Log.i(TAG, "Lower heartRate: " + prefLowerHeartRate);
        Log.i(TAG, "Upper heartRate: " + prefUpperHeartRate);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Pause");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.i(TAG, "Data change event");
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataItem item = event.getDataItem();
                if (Objects.requireNonNull(item.getUri().getPath()).compareTo(HEARTRATE_PATH) == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    int heartRate = dataMap.getInt(HEARTRATE_KEY);
                    Log.i(TAG, "Current heart rate: " + heartRate);
                    TextView heartRateValueTextView = findViewById(R.id.heartRateValue);
                    heartRateValueTextView.setText(String.valueOf(heartRate));

                    if (heartRate >= prefUpperHeartRate || heartRate <= prefLowerHeartRate) {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        assert v != null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.EFFECT_HEAVY_CLICK));
                        } else {
                            v.vibrate(500);
                        }

                        Log.i(TAG, "HEART RATE CRITICAL");
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        mp = MediaPlayer.create(this, alarmSound);

                        // Conditions don't work
                        if (!mp.isPlaying() && !mp.isLooping()){
                            mp.start();
                        }
                    }
                }
            } else {
                event.getType(); // DataItem deleted
            }
        }
    }

    public void settingsOpen(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}

