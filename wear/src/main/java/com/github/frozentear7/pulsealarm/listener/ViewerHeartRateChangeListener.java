package com.github.frozentear7.pulsealarm.listener;

import android.annotation.SuppressLint;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import com.github.frozentear7.pulsealarm.HeartRateChangeEvent;

public class ViewerHeartRateChangeListener implements HeartRateChangeListener {
    private final WearableActivity wearableActivity;
    private final TextView textView;

    public ViewerHeartRateChangeListener(WearableActivity activity, TextView textView) {
        this.wearableActivity = activity;
        this.textView = textView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onHeartRateChanged(final HeartRateChangeEvent heartRateChangeEvent) {
        wearableActivity.runOnUiThread(() -> textView.setText(Integer.toString(heartRateChangeEvent.getHeartRate())));
    }
}
