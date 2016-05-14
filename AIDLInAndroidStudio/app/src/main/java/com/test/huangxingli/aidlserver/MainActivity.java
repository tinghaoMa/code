package com.test.huangxingli.aidlserver;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.logging.LogManager;


public class MainActivity extends Activity {

    TextView textView;
    Button button;
    AIDLServerService aidlServerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6.0
//                Intent intent = new Intent("com.test.huangxingli.aidlserver.MAIDLServerService");
//                intent.setPackage("com.test.huangxingli.aidlserver");
//                bindService(intent, connection, Service.BIND_AUTO_CREATE);
                //6.0
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.test.huangxingli.aidlserver", "com.test.huangxingli.aidlserver.MAIDLServerService"));
                boolean b = bindService(intent, connection, Context.BIND_AUTO_CREATE);
                Log.e("aidl", "b=" + b);

//                Intent intent = new Intent();
//                intent.setAction("com.test.huangxingli.aidlserver.MAIDLServerService");
//                bindService(intent, connection, Service.BIND_AUTO_CREATE);

//                Intent mActivity = new Intent();
//                mActivity.setComponent(new ComponentName("com.test.huangxingli.aidlserver", "com.test.huangxingli.aidlserver.MainActivity"));
//                startActivity(mActivity);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aidlServerService != null) {
            Log.e("aidl", "unbind");
            unbindService(connection);
        } else {
            Log.e("aidl", "aidlServerService null");
        }
    }

    ServiceConnection connection = new ServiceConnection() {

        String content;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlServerService = AIDLServerService.Stub.asInterface(service);
            try {
                content = aidlServerService.sayHello() + "\n";
                Girl girl = aidlServerService.getGirl();
                content += "my name is " + girl.getName();
                textView.setText(content);
                aidlServerService.resgistListener(new IOnListener.Stub() {
                    @Override
                    public void onNewBookArrived(final String tip) throws RemoteException {
                        Log.e("aidl", "tip" + tip);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("tip-----" + tip);
                            }
                        });
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            aidlServerService = null;
        }
    };

}
