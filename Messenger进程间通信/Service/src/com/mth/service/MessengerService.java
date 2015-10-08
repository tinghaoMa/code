package com.mth.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    private static final int MSG_SUM = 0x110;

    private static final String TAG = "MTH";

    private Messenger mMessenger = new Messenger(new Handler() {

        @Override
        public void handleMessage(Message msgFromClient) {
            Message msgToClient = Message.obtain(msgFromClient);
            switch (msgFromClient.what) {
                case MSG_SUM:
                    msgToClient.what = MSG_SUM;
                    msgToClient.arg2 = msgFromClient.arg1 + msgFromClient.arg2;
                    try {
                        Thread.sleep(3000);
                        msgFromClient.replyTo.send(msgToClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        };
    });

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "开启服务!!!!!!!!");
    }

}
