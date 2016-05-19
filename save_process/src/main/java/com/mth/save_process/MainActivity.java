package com.mth.save_process;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mth.service.CancelService;
import com.mth.service.ProcessService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mServiceIntent = new Intent();
                mServiceIntent.setClass(MainActivity.this, ProcessService.class);
                startService(mServiceIntent);
            }
        });
    }
}
