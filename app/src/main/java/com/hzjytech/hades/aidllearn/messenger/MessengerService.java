package com.hzjytech.hades.aidllearn.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.hzjytech.hades.aidllearn.constrants.MyConstant;

public class MessengerService extends Service {

    private static final String TAG = MessengerService.class.getSimpleName();

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstant.MSG_FROM_CLIENT:
                    Log.d("TAG","receive msg from Client:"+msg.getData().getString("msg"));
                    Messenger client=msg.replyTo;
                    Message replyMessage=Message.obtain(null,MyConstant.MSG_FROM_SERVICE);
                    Bundle bundle=new Bundle();
                    bundle.putString("reply","Hi Client,I get your message ");
                    replyMessage.setData(bundle);

                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }

    private final Messenger mMessenger=new Messenger(new MessengerHandler());

    public MessengerService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG","oncreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mMessenger.getBinder();
    }
}
