package com.example.android.messengerexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MessengerService myService_;
    private boolean bound;
    Messenger myMessenger;
    Button btClickMe, btShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bound = false;

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", "pkanna1");
        editor.commit();

        Intent myIntent = new Intent(this, MessengerService.class);
        bindService(myIntent, mConnection, Context.BIND_AUTO_CREATE);
        btClickMe = (Button) findViewById(R.id.bt_click_me);
        btShared = (Button) findViewById(R.id.bt_shared);
        btClickMe.setOnClickListener(this);
        btShared.setOnClickListener(this);

        //Intent myIntent = new Intent(this, MyIntentService.class);
        //startService(myIntent);
    }
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myMessenger = new Messenger(iBinder);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    };

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.bt_click_me:
                if (bound) {
                    Message myMessage = Message.obtain(null, MessengerService.MSG_ACTIVITY, 0, 0);
                    try {
                        myMessenger.send(myMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.bt_shared:
                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "Doesnotexist");
                Toast.makeText(getApplicationContext(), "Username is: " + username, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
