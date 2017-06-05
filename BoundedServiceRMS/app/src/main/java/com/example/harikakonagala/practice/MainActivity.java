package com.example.harikakonagala.practice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DisplayContext;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editText;
    Button button;
    MyService.MyBinder myBinder;
    MyService myService;
    boolean bound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText =(EditText) findViewById(R.id.editText);
        button =(Button) findViewById(R.id.button);
        button.setOnClickListener(this);//act on click
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent myIntent =new Intent(this, MyService.class);
        bindService(myIntent, myServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(myServiceConnection);
    }

    private ServiceConnection myServiceConnection =new ServiceConnection() {
        public Object iBinder;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) iBinder;
            myService = myBinder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };


    @Override
    public void onClick(View v) {
        if(bound){
            String rmsRes= myService.getRmsData();
            editText.setText(rmsRes);
        }
    }
}
