package com.test.huangxingli.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MAIDLServerService extends Service {



    public MAIDLServerService() {

    }

    AIDLServerService.Stub binder=new AIDLServerService.Stub() {


        @Override
        public String sayHello() throws RemoteException {
            return "hello, i am from AIDLServerService";
        }

        @Override
        public Girl getGirl() throws RemoteException {
            Girl girl=new Girl();
            girl.setAge(25);
            girl.setName("lily");
            return girl;
        }

        @Override
        public void resgistListener(IOnListener listener) throws RemoteException {
            start(listener);
        }
    };

    private void start(final IOnListener listener) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                int count = 0;
                while (true){
                    try {
                        Thread.sleep(3000);
                        count++;
                        if(listener!=null){
                            listener.onNewBookArrived("msg from remote service count ="+count);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }


    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("MTH----MAIDLServerService.onBind");
       return binder;
    }






}
