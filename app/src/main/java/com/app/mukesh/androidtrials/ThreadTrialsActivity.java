package com.app.mukesh.androidtrials;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sahana on 4/3/16.
 */
public class ThreadTrialsActivity extends Activity implements View.OnClickListener {

    private static final String TAG = ThreadTrialsActivity.class.getSimpleName();
    private ScheduledExecutorService mScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_trials_activity);
        findViewById(R.id.start_btn).setOnClickListener(this);
        findViewById(R.id.wait_result_btn).setOnClickListener(this);
        mScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                mScheduler.scheduleAtFixedRate(new ReaderTask(), 0, 500, TimeUnit.MILLISECONDS);
                break;

            case R.id.wait_result_btn:
                ExecutorService threadPool = Executors.newSingleThreadExecutor();

                CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);

                for(int i = 0; i < 10; i++){
                    pool.submit(new StringTask(i + 1));
                }
                try {
                for(int i = 0; i < 10; i++){
                        String result = pool.take().get();

                    Log.d(TAG, result);
                }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                threadPool.shutdown();
                break;
        }
    }

    private final class StringTask implements Callable<String> {

        private final int mNumber;

        public StringTask(int number) {
            mNumber = number;
        }

        public String call(){
            Log.d(TAG, "Run() started: ");
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d(TAG, "Run() " + e.toString());
            }
            Log.d(TAG, "Run() ended: ");

            return "Result: " + mNumber;
        }
    }
}
