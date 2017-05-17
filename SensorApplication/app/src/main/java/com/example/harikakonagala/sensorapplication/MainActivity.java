package com.example.harikakonagala.sensorapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    EditText accx,accy,accz;
    //Button bt;
    SensorManager sensorManager;
    Sensor accelerometer;
    Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accx=(EditText) findViewById(R.id.editText);
        accy=(EditText) findViewById(R.id.editText2);
        accz=(EditText) findViewById(R.id.editText3);
       //bt = (Button) findViewById(R.id.button);
    }

    @Override
    protected void onResume() {
        super.onResume();

       // bt.setOnClickListener((View.OnClickListener) this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
       // bt.setOnClickListener(null);
        sensorManager.unregisterListener(this, accelerometer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            AccelerometerTask accelerometerTask = new AccelerometerTask(event);
            myHandler.post(accelerometerTask);
       // }
    }
    public class AccelerometerTask implements Runnable{
        SensorEvent sensorEvent;
        public AccelerometerTask(SensorEvent event){
            sensorEvent = event;
        }

        @Override
        public void run() {
            accx.setText(String.valueOf(sensorEvent.values[0]));
            accy.setText(String.valueOf(sensorEvent.values[1]));
            accz.setText(String.valueOf(sensorEvent.values[2]));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
