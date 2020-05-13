package com.github.frozentear7.pulsealarm;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import com.github.frozentear7.pulsealarm.listener.LoggerHeartRateChangeListener;
import com.github.frozentear7.pulsealarm.listener.SenderHeartRateChangeListener;
import com.github.frozentear7.pulsealarm.listener.ViewerHeartRateChangeListener;
import com.github.frozentear7.pulsealarm.manager.HeartRateChangeListenerManager;
import com.github.frozentear7.pulsealarm.manager.HeartRateChangeListenerManagerProvider;

public class MainActivity extends WearableActivity {
    private static final String TAG = "MainActivity";

    private TextView heartRateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heartRateTextView = findViewById(R.id.heartRateTextView);
        heartRateTextView.setText("Test");

        HeartRateChangeListenerManager heartRateChangeListenerManager =
                HeartRateChangeListenerManagerProvider.getHeartRateChangeListenerManager();
        heartRateChangeListenerManager.registerListener(new ViewerHeartRateChangeListener(this, heartRateTextView));
        heartRateChangeListenerManager.registerListener(new LoggerHeartRateChangeListener(TAG));
        heartRateChangeListenerManager.registerListener(new SenderHeartRateChangeListener(this));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
