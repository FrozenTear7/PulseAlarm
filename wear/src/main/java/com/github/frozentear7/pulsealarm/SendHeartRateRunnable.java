package com.github.frozentear7.pulsealarm;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.ExecutionException;

public class SendHeartRateRunnable implements Runnable {
    private Context applicationContext;
    private int heartRate;

    private static final String TAG = "SendHeartRateRunnable";

    private static final String HEARTRATE_PATH = "/heartrate";
    private static final String HEARTRATE_KEY = "heartrate";

    public SendHeartRateRunnable(Context applicationContext, int heartRate) {
        this.applicationContext = applicationContext;
        this.heartRate = heartRate;
    }

    @Override
    public void run() {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(HEARTRATE_PATH);
        putDataMapRequest.getDataMap().putInt(HEARTRATE_KEY, heartRate);

        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        request.setUrgent();

        Task<DataItem> dataItemTask = Wearable.getDataClient(applicationContext).putDataItem(request);

        Log.i(TAG, "Created task for: " + heartRate);

        try {
            // Block on a task and get the result synchronously (because this is on a background
            // thread).
            DataItem dataItem = Tasks.await(dataItemTask);

            Log.i(TAG, "DataItem saved: " + dataItem);
        } catch (ExecutionException exception) {
            Log.i(TAG, "Task failed: " + exception);

        } catch (InterruptedException exception) {
            Log.i(TAG, "Interrupt occurred: " + exception);
        }
    }
}
