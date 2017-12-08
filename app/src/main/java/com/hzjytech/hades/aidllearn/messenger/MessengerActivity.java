package com.hzjytech.hades.aidllearn.messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hzjytech.hades.aidllearn.R;
import com.hzjytech.hades.aidllearn.constrants.MyConstant;

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG=MessengerActivity.class.getSimpleName();


    private Messenger mService;
    private Messenger mGetReplyMessenger=new Messenger(new MessengerHandler());
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.d("TAG","onServiceConnected");
            mService=new Messenger(service);
            Message msg=Message.obtain(null, MyConstant.MSG_FROM_CLIENT);
            Bundle data=new Bundle();
            data.putString("msg","hello this is client");
            msg.setData(data);
            msg.replyTo=mGetReplyMessenger;

            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent=new Intent("com.hzjytech.hades.aidllearn.messenger.MessengerService.launch");
        intent.setPackage("com.hzjytech.hades.aidllearn");
        bindService(intent,mConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    private class MessengerHandler extends Handler  {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyConstant.MSG_FROM_SERVICE:
                    Log.d("TAG","receive msg from Service:"+msg.getData().getString("reply"));
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
