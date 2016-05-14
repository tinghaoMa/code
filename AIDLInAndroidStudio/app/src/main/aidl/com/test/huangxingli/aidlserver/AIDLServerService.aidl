package com.test.huangxingli.aidlserver;
import com.test.huangxingli.aidlserver.Girl;
import com.test.huangxingli.aidlserver.IOnListener;

interface AIDLServerService {
       String sayHello();
       Girl getGirl();
       void resgistListener(IOnListener listener);
}
