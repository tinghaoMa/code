package com.mth.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

public class MessengerService extends Service {
    private Messenger mMessenger;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    System.out.println("接收到客户端的信息---" + msg.getData().getString("msg"));
                    Message message = new Message();
                    message.what = 2;
                    Bundle bundle = new Bundle();
                    bundle.putString("mth", "发送给客户的信息----hello client!!!!!");
                    message.setData(bundle);
                    try {
                        Thread.sleep(3000);
                        msg.replyTo.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        System.out.println("service oncreate");
        mMessenger = new Messenger(new MessengerHandler());
    }

}
