package com.example.harikakonagala.practice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    MyService myService;
    boolean flag;
    TextView tv;
    Button bt;

    Handler myHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView_1);
        bt = (Button) findViewById(R.id.button_1);


    }

    @Override
    protected void onResume() {
        super.onResume();
        bt.setOnClickListener(this);

        Intent intent = new Intent(this, MyService.class);

        bindService(intent, myServiceConnection, Service.BIND_AUTO_CREATE);
    }
    private ServiceConnection myServiceConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder myBinder = (MyService.MyBinder) service;
            myService = myBinder.getService();
            flag = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            flag = false;
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        unbindService(myServiceConnection);
        bt.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        if(flag){
           Accel accel=new Accel();
            myHandler.post(accel);
        }
    }

    public class Accel implements Runnable{

        Float rms;
        @Override
        public void run() {
            rms=myService.getRms();
            tv.setText(String.valueOf(rms));
        }
    }
}
