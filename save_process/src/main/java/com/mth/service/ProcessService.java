package com.mth.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.mth.save_process.R;

public class ProcessService extends Service {
    private static final String TAG = "MTH";

    public ProcessService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
        Notification mNotification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.e(TAG, "大于18");
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            mNotification = builder.build();
            startForeground(250, mNotification);
            startService(new Intent(this, CancelService.class));
        } else {
            Log.e(TAG, "小于18");
            mNotification = new Notification();
            startForeground(250, mNotification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "sleep  ------");
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTrimMemory(int level) {
        Log.e(TAG, "onTrimMemory");
    }
}
