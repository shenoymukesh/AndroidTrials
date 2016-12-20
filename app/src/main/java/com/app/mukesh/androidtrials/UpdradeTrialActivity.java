package com.app.mukesh.androidtrials;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
/**
 * Created by sahana on 4/26/16.
 */
public class UpdradeTrialActivity extends Activity implements View.OnClickListener {

    private static final String TAG = UpdradeTrialActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.upgrade_trial_activity);
        findViewById(R.id.start_serv_btn).setOnClickListener(this);
        findViewById(R.id.start_activity_btn).setOnClickListener(this);
        findViewById(R.id.battery_dialog_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_serv_btn:
                startService(new Intent(UpdradeTrialActivity.this, LongRunningService.class));
                break;

            case R.id.start_activity_btn:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(UpdradeTrialActivity.this, DummyActivity.class));
                    }
                }, 20 * 1000);
                break;

            case R.id.battery_dialog_btn:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    String pkg=getPackageName();
                    PowerManager pm=getSystemService(PowerManager.class);

                    if (!pm.isIgnoringBatteryOptimizations(pkg)) {
                        Intent i=
                                new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                                        .setData(Uri.parse("package:"+pkg));

                        startActivity(i);
                    }
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}
