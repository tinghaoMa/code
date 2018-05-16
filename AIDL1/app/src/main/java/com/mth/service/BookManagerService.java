package com.mth.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.aidl.Book;
import com.aidl.IBookManager;
import com.aidl.IOnNewBookListener;
import com.mth.contanst.Contanst;

public class BookManagerService extends Service {
    private AtomicBoolean flag = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookListener> mListenerList = new RemoteCallbackList<IOnNewBookListener>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book("服务端初始化书籍", 11));
        mBookList.add(new Book("服务端初始化书籍", 22));
        Log.e(Contanst.TAG, "onCreate");
        new Thread(new WorkRunnable()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(Contanst.TAG, "onBind");
        // 验证权限
        int check = checkCallingOrSelfPermission(Contanst.PERMISSION);
        Log.e(Contanst.TAG, "check=" + check);
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(Contanst.TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(Contanst.TAG, "onDestroy");
        flag.set(true);
        super.onDestroy();
    }

    private IBookManager.Stub mBinder = new IBookManager.Stub() {

        @Override
        public void unRegisterListener(IOnNewBookListener listener) throws RemoteException {
            mListenerList.unregister(listener);
        }

        @Override
        public void registerListener(IOnNewBookListener listener) throws RemoteException {
            mListenerList.register(listener);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        // 验证权限
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int check = checkCallingOrSelfPermission(Contanst.PERMISSION);
            Log.e(Contanst.TAG, "check=" + check);
            if (check == PackageManager.PERMISSION_DENIED) {
                return false;
            }

            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.e(Contanst.TAG, "onTransact: " + packageName);
            if (!packageName.startsWith(Contanst.PACKAGENAME)) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    private class WorkRunnable implements Runnable {

        @Override
        public void run() {
            int price = 1;
            while (!flag.get()) {
                try {
                    Log.e(Contanst.TAG, price + "");
                    Thread.sleep(3000);
                    Book book = new Book("后台自动添加书籍-------", price);
                    price++;
                    onNewBookArrvied(book);
                } catch (Exception e) {
                }
            }
        }

    }

    public void onNewBookArrvied(Book book) {
        mBookList.add(book);
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookListener l = mListenerList.getBroadcastItem(i);
            if (l != null) {
                try {
                    l.OnNewBookArrviedListener(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }
}
