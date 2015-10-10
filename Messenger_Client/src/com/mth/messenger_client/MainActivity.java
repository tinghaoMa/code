package com.mth.messenger_client;

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

public class MainActivity extends Activity implements OnClickListener {

    private ServiceConnection conn;
    private Button mBind;
    private Button mSendMsg;
    private Messenger mMessenger;
    private Messenger mMsgMessenger;

    private class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    System.out.println(msg.getData().getString("mth"));
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        setUpViews();
        initEvent();
    }

    private void initData() {
        conn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMessenger = new Messenger(service);
            }
        };
    }

    private void initEvent() {
        mBind.setOnClickListener(this);
        mSendMsg.setOnClickListener(this);
    }

    private void setUpViews() {
        mBind = (Button) findViewById(R.id.id_bindservice_bt);
        mSendMsg = (Button) findViewById(R.id.id_sendmsg_bt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_bindservice_bt:
                Intent mServiceIntent = new Intent("com.mth.messengerservice");
                bindService(mServiceIntent, conn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.id_sendmsg_bt:
                if (mMessenger != null) {
                    try {
                        mMsgMessenger = new Messenger(new ClientHandler());
                        Message message = new Message();
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", "hello server");
                        message.replyTo = mMsgMessenger;
                        message.setData(bundle);
                        mMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

}
