package com.mth.tinghaoma.remoteviewdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
    private static final String ACTION = "com.mth.changeUi";
    private static final String TAG = "UI";


    private RelativeLayout rl_parent;
    private MyReceiver receiver = new MyReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            RemoteViews remoteViews = intent.getParcelableExtra(TAG);
            if (remoteViews != null) {
                updateView(remoteViews);
            }
        }


    };

    private void updateView(RemoteViews remoteViews) {
        View clientView = remoteViews.apply(this, rl_parent);
        rl_parent.addView(clientView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
    }

    private void setUpViews() {
        rl_parent = (RelativeLayout) findViewById(R.id.id_rl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null)
            unregisterReceiver(receiver);

    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
