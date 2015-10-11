package com.mth.binderpool;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mth.aidl.ICompute;
import com.mth.aidl.IComputerImpl;
import com.mth.aidl.IQueryBinder;
import com.mth.aidl.ISecurityCenter;
import com.mth.aidl.ISecurityCenterImpl;
import com.mth.pool.BinderPool;
import com.mth.pool.BinderPool.OnQueryBinderListener;
import com.mth.service.QueryService;

public class MainActivity extends Activity implements OnQueryBinderListener {

    private IQueryBinder queryBinder;
    private BinderPool pool = BinderPool.getInstance();
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            queryBinder = IQueryBinder.Stub.asInterface(service);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent service = new Intent(MainActivity.this, QueryService.class);
        bindService(service, conn, Context.BIND_AUTO_CREATE);
        pool.setmListener(this);
        Button mAddBt = (Button) findViewById(R.id.id_add_bt);
        Button id_safe_bt = (Button) findViewById(R.id.id_safe_bt);
        mAddBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    ICompute addBinder = IComputerImpl.asInterface(pool.queryBinder(1));
                    int result = addBinder.computeAdd(3, 4);
                    Log.e("MTH", result + "");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        id_safe_bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    ISecurityCenter securityCenter = ISecurityCenterImpl.asInterface(pool.queryBinder(2));
                    String resultEncrypt = securityCenter.encrypt("加密");
                    String resultDecrypt = securityCenter.decrypt("解密");
                    Log.e("MTH", resultEncrypt);
                    Log.e("MTH", resultDecrypt);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public IBinder query(int code) {
        try {
            return queryBinder.queryBinder(code);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
