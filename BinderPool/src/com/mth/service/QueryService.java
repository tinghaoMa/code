package com.mth.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mth.aidl.IQueryBinderImpl;

public class QueryService extends Service {

    private IBinder mQueryBinder = new IQueryBinderImpl();

    @Override
    public IBinder onBind(Intent intent) {
        return mQueryBinder;
    }
}
