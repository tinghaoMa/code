package com.mth.aidl;

import android.os.RemoteException;

public class IComputerImpl extends ICompute.Stub {

    @Override
    public int computeAdd(int a, int b) throws RemoteException {
        return a + b;
    }

}
