package com.app.mukesh.androidtrials;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

/**
 * Created by sahana on 4/7/16.
 */
public class PowerChangeReceiver extends BroadcastReceiver {
    private static final String TAG = PowerChangeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive(): " + intent.getAction());
        IntentFilter batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = context.registerReceiver(null, batteryFilter);
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean charging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        Log.d(TAG, "Charging: " + charging);
    }
}
