package com.mth.aidl;

import android.os.IBinder;
import android.os.RemoteException;

public class IQueryBinderImpl extends IQueryBinder.Stub {

    @Override
    public IBinder queryBinder(int code) throws RemoteException {
        switch (code) {
            case 1:
                return new IComputerImpl();
            case 2:
                return new ISecurityCenterImpl();
        }
        return null;
    }

}
