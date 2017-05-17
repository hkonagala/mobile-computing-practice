package com.example.harikakonagala.boundedservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MessengerService extends Service {
public static final int MSG_ACTIVITY = 1;
    public MessengerService(){

}
    final Messenger mymessenger=new Messenger(new myHandler());
    class myHandler extends Handler{
        public void handleMessage(Message msg){
            switch(msg.what){
                case MSG_ACTIVITY;
                    Toast.makeText(getApplicationContext(), "hello from activity", Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mymessenger.getBinder();
    }
}
