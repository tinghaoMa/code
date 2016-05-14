package mth.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import mth.constants.Constants;

public class ServerService extends Service {

    private static final String TAG = "MTH";
    private Messenger mServiceMessenger;
    private Messenger mClientMessenger;

    private Handler mServiceHandler = new Handler() {
        Message mServiceMsg;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.SERVICE_CONNECT_SUCESS:
                    //接收到客户端消息
                    Bundle data = msg.getData();
                    String str = data.getString(Constants.MSG_FROM_CLIENT);
                    Log.e(TAG, "service Constants.SERVICE_CONNECT_SUCESS");
                    Log.e(TAG, str);
                    //向客户端发送消息
                    mClientMessenger = msg.replyTo;
                    mServiceMsg = Message.obtain();
                    mServiceMsg.what = Constants.MSG_CLIENT;
                    Bundle dataToClient = msg.getData();
                    data.putString(Constants.MSG_TO_CLIENT, "hello client this is server");
                    mServiceMsg.setData(dataToClient);
                    try {
                        mClientMessenger.send(mServiceMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.MSG_DEFAULT:
                    Log.e(TAG, "service-msg-default");
                    mServiceMsg = Message.obtain();
                    mServiceMsg.what = Constants.MSG_DEFAULT;
                    try {
                        mClientMessenger.send(mServiceMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public ServerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "service onBind");
        return mServiceMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "service onCreate");
        mServiceMessenger = new Messenger(mServiceHandler);
    }
}
