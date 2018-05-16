package com.mth.aidl;

import java.util.List;

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
import android.widget.TextView;

import com.aidl.Book;
import com.aidl.IBookManager;
import com.aidl.IOnNewBookListener;
import com.mth.contanst.Contanst;
import com.mth.service.BookManagerService;

public class MainActivity extends Activity implements OnClickListener {
    private Button mBind;
    private Button mAddOne;
    private Button mGetAllBook;
    private TextView mBookTv;

    private boolean isConn;
    private ServiceConnection conn;
    private IBookManager mManager;
    private IBookManager mRemoteBookManager;
    /**
     * 断开连接 通知
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mRemoteBookManager == null)
                return;
            mRemoteBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mRemoteBookManager = null;
            // TODO:这里重新绑定远程Service
        }
    };



    private IOnNewBookListener listener = new IOnNewBookListener.Stub() {

        @Override
        public void OnNewBookArrviedListener(final Book book) throws RemoteException {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mBookTv.setText(book.toString());
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        setUpViews();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        if (mManager != null) {
            try {
                mManager.unRegisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void initData() {
        conn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConn = false;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                isConn = true;
                mManager = IBookManager.Stub.asInterface(service);
                mRemoteBookManager = mManager;
                try {
                    mRemoteBookManager.asBinder().linkToDeath(mDeathRecipient, 0);
                    mManager.registerListener(listener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void initEvent() {
        mBind.setOnClickListener(this);
        mAddOne.setOnClickListener(this);
        mGetAllBook.setOnClickListener(this);
    }

    private void setUpViews() {
        mBind = (Button) findViewById(R.id.id_bind_bt);
        mAddOne = (Button) findViewById(R.id.id_addbook_bt);
        mGetAllBook = (Button) findViewById(R.id.id_getallbook_bt);
        mBookTv = (TextView) findViewById(R.id.id_show_tv);
    }

    @Override
    public void onClick(View v) {
        Intent mService = new Intent(MainActivity.this, BookManagerService.class);
        switch (v.getId()) {
            case R.id.id_bind_bt:
                bindService(mService, conn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.id_addbook_bt:
                if (isConn) {
                    try {
                        mManager.addBook(new Book("客户端新添加的书籍", 33));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.id_getallbook_bt:
                if (isConn) {
                    try {
                        List<Book> mList = mManager.getBookList();
                        for (Book book : mList) {
                            Log.e(Contanst.TAG, book.toString());
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
