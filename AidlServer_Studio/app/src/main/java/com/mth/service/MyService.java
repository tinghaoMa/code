package com.mth.service;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.mth.aidl.Book;
import com.mth.aidl.IBookManager;
import com.mth.aidl.IBookManager.Stub;

public class MyService extends Service {

    private static final String TAG = "MyService";
    private List<Book> mList = new ArrayList<Book>();

    @Override
    public void onCreate() {
        for (int i = 0; i < 5; i++) {
            mList.add(new Book("服务端书籍--"+i));
        }
        Log.e(TAG, "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
    }

    private IBookManager.Stub mBinder = new Stub() {

        @Override
        public List<Book> getAllBook() throws RemoteException {
            for (Book book : mList) {
                Log.e(TAG, book.toString());
            }
            return mList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mList.add(book);
        }

        // 在这里可以做权限认证，return
        // false意味着客户端的调用就会失败，比如下面，只允许包名为com.example.test的客户端通过，
        // 其他apk将无法完成调用过程
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            String[] packages = MyService.this.getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.d(TAG, "onTransact: " + packageName);
            if (!"com.mth.aidlclient".equals(packageName)) {
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }
    };
}
