package com.github.frozentear7.pulsealarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.Objects;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends Activity implements DataClient.OnDataChangedListener {
    private static final String TAG = "MainActivity";

    private static final String HEARTRATE_PATH = "/heartrate";
    private static final String HEARTRATE_KEY = "heartrate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView heartRateValueTextView = findViewById(R.id.heartRateValue);
        heartRateValueTextView.setText(String.valueOf(123));
        Wearable.getDataClient(this).addListener(this);

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Wearable.getDataClient(this).addListener(this);
        Log.i(TAG, "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Wearable.getDataClient(this).removeListener(this);
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
                }
            } else {
                event.getType(); // DataItem deleted
            }
        }
    }
}

