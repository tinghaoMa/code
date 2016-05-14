package mth.messenger_handler_demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import mth.bean.Person;
import mth.constants.Constants;

public class MainActivity extends Activity {

    private static final String TAG = "MTH";
    private boolean mBindSucess = false;
    private Person person;
    private Messenger mClientMessenger;
    private Messenger mServiceMessenger;
    private Handler mClientHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_CLIENT: //接受到服务端消息
                    String string = msg.getData().getString(Constants.MSG_TO_CLIENT);
                    Log.e(TAG, string);
                    break;
                case Constants.MSG_DEFAULT:
                    Log.e(TAG, "msg-default-client");
                    break;
            }
        }
    };
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBindSucess = true;
            mServiceMessenger = new Messenger(service);
            //向客户端发送消息
            Message msg = Message.obtain();
            msg.what = Constants.SERVICE_CONNECT_SUCESS;
            Bundle data = new Bundle();
            data.putString(Constants.MSG_FROM_CLIENT, "hello server this is cilent");
            msg.setData(data);
            msg.replyTo = mClientMessenger;
            try {
                mServiceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        findViewById(R.id.id_bt_con_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent();
                serverIntent.setComponent(new ComponentName("mth.server", "mth.service.ServerService"));
                bindService(serverIntent, mConn, BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.id_bt_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServiceMessenger!=null){
                    Message msg = Message.obtain();
                    msg.what = Constants.MSG_DEFAULT;
                    msg.replyTo = mClientMessenger;
                    try {
                        mServiceMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initData() {
        mClientMessenger = new Messenger(mClientHandler);
        person = new Person();
        person.name = "person hello service";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBindSucess) {
            unbindService(mConn);
        }
    }
}
