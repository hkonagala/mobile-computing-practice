package com.example.harikakonagala.boundedservicepractice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textView, tv;
    //EditText editText;
    Button button;
    MyService myService;
    MyService.MyBinder myBinder;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView_1);
        tv = (TextView) findViewById(R.id.textView2);
        button = (Button) findViewById(R.id.button_click);
        button.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(myServiceConnection);
    }

    private ServiceConnection myServiceConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           // Object iBinder = null;
            myBinder = (MyService.MyBinder) service;
            myService = myBinder.getService();
            flag=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            flag=false;
        }
    };

    @Override
    public void onClick(View v) {
        if(flag){
            String rmsres=myService.rms();
            tv.setText(rmsres);
        }
    }
}
