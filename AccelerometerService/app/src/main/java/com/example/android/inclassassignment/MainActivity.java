package com.example.android.inclassassignment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    MyAccelerometerService.MyBinder myBinder;
    MyAccelerometerService myService;
    EditText accX;
    Button btPrint;
    boolean bound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accX = (EditText) findViewById(R.id.et_accl_x);
        btPrint = (Button) findViewById(R.id.bt_getRMS);
        btPrint.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent myIntent = new Intent(this, MyAccelerometerService.class);
        bindService(myIntent, myServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(myServiceConnection);
    }

    private ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBinder = (MyAccelerometerService.MyBinder) iBinder;
            myService = myBinder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    };

    @Override
    public void onClick(View view) {
        if (bound) {
            String rmsResult = myService.getRMS();
            accX.setText(rmsResult);
        }
    }
}
