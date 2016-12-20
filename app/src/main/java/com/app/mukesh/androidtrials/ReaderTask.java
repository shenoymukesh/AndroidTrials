package com.app.mukesh.androidtrials;

import android.util.Log;

/**
 * Created by sahana on 4/3/16.
 */
public class ReaderTask implements Runnable {


    private static final String TAG = ReaderTask.class.getSimpleName();

    @Override
    public void run() {
        Log.d(TAG, "Run() started: ");
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "Run() " + e.toString());
        }
        Log.d(TAG, "Run() ended: ");
    }
}
