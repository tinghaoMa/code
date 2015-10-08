package com.mth.messengerclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int MSG_SUM = 0x110;
    private Button mBt;
    private Button mBtAdd;
    private TextView mTv;
    private Messenger mService;
    private boolean isConn;
    private Messenger mMessenger = new Messenger(new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUM:
                    mTv.setText("返回的结果是--->" + msg.arg2);
                    break;
            }
        };
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        mBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                bindService();
            }
        });
        mBtAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mTv.setText("计算中---------------!");
                int a = 100;
                int b = 234;
                Message msgToService = Message.obtain(null, MSG_SUM, a, b);
                msgToService.replyTo = mMessenger;
                try {
                    if (isConn) {
                        // 往服务端发送消息
                        mService.send(msgToService);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        mBt = (Button) findViewById(R.id.bt);
        mBtAdd = (Button) findViewById(R.id.addbt);
        mTv = (TextView) findViewById(R.id.tv);
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConn = false;
            mService = null;
            mTv.setText("断开连接!");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            isConn = true;
            mTv.setText("连接成功!");
        }
    };

    protected void bindService() {
        Intent mIntent = new Intent();
        mIntent.setAction("com.mth.messengerservice");
        bindService(mIntent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
