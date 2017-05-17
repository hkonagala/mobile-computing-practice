package com.example.harikakonagala.serviceapplication;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_rms;
    Button bt_rms;
    boolean flag = false;
    MyService myService;
    Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_rms= (EditText) findViewById(R.id.editText);
        bt_rms = (Button) findViewById(R.id.button);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bt_rms.setOnClickListener(this);
        Intent myIntent = new Intent(this, MyService.class);
        bindService(myIntent, myServiceConnection, Service.BIND_AUTO_CREATE);
    }
    ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder myBinder = (MyService.MyBinder) service;
            myService = myBinder.getService();
            flag = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            flag=false;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        bt_rms.setOnClickListener(null);
        unbindService(myServiceConnection);
    }

    @Override
    public void onClick(View v) {
        AccelTask aTask = new AccelTask();
        myHandler.post(aTask);
    }

    private class AccelTask implements Runnable{

        @Override
        public void run() {
            if (flag){
                float rms = myService.getRms();
                et_rms.setText(String.valueOf(rms));
            }
        }
    }
}
