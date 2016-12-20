package com.app.mukesh.androidtrials;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by sahana on 4/26/16.
 */
public class LongRunningService extends Service {


    private static final String TAG = LongRunningService.class.getSimpleName();

    private int mCounter = 0;
    private PowerManager.WakeLock mCpuWakeLock;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mCpuWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        mCpuWakeLock.acquire();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand(): " + startId);
        startForeground(1, new Notification.Builder(this).setContentTitle("I am Hi Service")
                .setSmallIcon(R.mipmap.ic_launcher).setContentText("Hi").build());
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Hi " + (++ mCounter));
                handler.postDelayed(this, 1000);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        mCpuWakeLock.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
