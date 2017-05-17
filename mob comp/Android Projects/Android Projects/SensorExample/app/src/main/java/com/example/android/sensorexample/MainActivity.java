package com.example.android.sensorexample;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    EditText acclx, accly, acclz;
    Button btGotoGyro, btGotoLoc;
    Sensor accelerometer;
    private Handler myHandler = new Handler();

    private class AccelerometerTask implements Runnable{
        private SensorEvent sensorEvent;
        public AccelerometerTask(SensorEvent event){
            sensorEvent = event;
        }

        @Override
        public void run() {
            acclx.setText(String.valueOf(sensorEvent.values[0]));
            accly.setText(String.valueOf(sensorEvent.values[1]));
            acclz.setText(String.valueOf(sensorEvent.values[2]));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acclx = (EditText) findViewById(R.id.et_accl_x);
        accly = (EditText) findViewById(R.id.et_accl_y);
        acclz = (EditText) findViewById(R.id.et_accl_z);
        btGotoGyro = (Button) findViewById(R.id.bt_goto_gyro);

        btGotoGyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, GyroActivity.class);
                startActivity(myIntent);
            }
        });

        btGotoLoc = (Button) findViewById(R.id.bt_goto_location);

        btGotoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(myIntent);
            }
        });

        SensorManager sensorManager_ = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager_.registerListener( this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            AccelerometerTask accelerometerTask = new AccelerometerTask(sensorEvent);
            myHandler.post(accelerometerTask);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
