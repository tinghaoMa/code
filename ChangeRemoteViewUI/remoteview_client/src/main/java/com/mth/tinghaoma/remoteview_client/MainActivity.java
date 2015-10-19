package com.mth.tinghaoma.remoteview_client;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

/**
 * 改变远程应用UI
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private static final String ACTION = "com.mth.changeUi";
    private static final String TAG = "UI";

    private Button mChangeBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        initEvent();
    }

    private void initEvent() {
        mChangeBt.setOnClickListener(this);
    }

    private void setUpViews() {
        mChangeBt = (Button) findViewById(R.id.id_change_bt);
    }

    @Override
    public void onClick(View v) {
        if(v==mChangeBt) {
            RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.remote_layout );
            Intent mIntent = new Intent(ACTION);
            mIntent.putExtra(TAG, remoteViews);
            sendBroadcast(mIntent);
        }
    }
}
