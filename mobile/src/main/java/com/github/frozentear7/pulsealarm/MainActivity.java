package com.github.frozentear7.pulsealarm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
    private int heartRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Hello BRUH", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {

                DataItem item = event.getDataItem();
                if (Objects.requireNonNull(item.getUri().getPath()).compareTo(HEARTRATE_PATH) == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    Log.i(TAG, "Current heart rate: " + dataMap.getInt(HEARTRATE_KEY));
                }
            } else {
                event.getType();// DataItem deleted
            }
        }
    }
}

