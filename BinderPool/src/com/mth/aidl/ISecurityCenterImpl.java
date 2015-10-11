package com.mth.aidl;

import android.os.RemoteException;

public class ISecurityCenterImpl extends ISecurityCenter.Stub {

    @Override
    public String encrypt(String content) throws RemoteException {
        return content + "---加密";
    }

    @Override
    public String decrypt(String content) throws RemoteException {
        return content + "---解密";
    }

}
