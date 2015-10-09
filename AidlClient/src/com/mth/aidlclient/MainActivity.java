package com.mth.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mth.aidl.Book;
import com.mth.aidl.IBookManager;

public class MainActivity extends Activity implements OnClickListener {
    private Button mBindService;
    private Button mStopService;
    private Button mShowAllBook;
    private Button mAddOneBook;

    private ServiceConnection conn;
    private IBookManager manager;

    private int i = 1;

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
                manager = IBookManager.Stub.asInterface(service);
                if (manager != null) {
                    try {
                        manager.getAllBook();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    private void initEvent() {
        mBindService.setOnClickListener(this);
        mStopService.setOnClickListener(this);
        mShowAllBook.setOnClickListener(this);
        mAddOneBook.setOnClickListener(this);
    }

    private void setUpViews() {
        mBindService = (Button) findViewById(R.id.id_bindservice_bt);
        mStopService = (Button) findViewById(R.id.id_stopservice_bt);
        mShowAllBook = (Button) findViewById(R.id.id_showall_bt);
        mAddOneBook = (Button) findViewById(R.id.id_addone_bt);
    }

    @Override
    public void onClick(View v) {
        Intent mServiceIntent = new Intent("com.mth.service");
        mServiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (v.getId()) {
            case R.id.id_bindservice_bt:
                bindService(mServiceIntent, conn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.id_stopservice_bt:
                unbindService(conn);
                break;
            case R.id.id_showall_bt:
                if (manager != null)
                    try {
                        manager.getAllBook();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                break;
            case R.id.id_addone_bt:
                if (manager != null) {
                    try {
                        manager.addBook(new Book("客户端添加的书籍" + i));
                        i++;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
