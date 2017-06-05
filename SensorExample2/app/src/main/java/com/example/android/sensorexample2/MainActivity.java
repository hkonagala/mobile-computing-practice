package com.example.android.sensorexample2;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    EditText etAccx, etAccy, etAccz;
    Button btgetVal;
    Sensor accelerometer;
    SensorManager sensorManager;
    private Handler myHandler = new Handler();

    public class AccelerometerTask implements Runnable{
        private SensorEvent sensorEvent;

        public AccelerometerTask(SensorEvent sensorEvent){
            this.sensorEvent = sensorEvent;
        }
        @Override
        public void run() {
            etAccx.setText(String.valueOf(sensorEvent.values[0]));
            etAccy.setText(String.valueOf(sensorEvent.values[1]));
            etAccz.setText(String.valueOf(sensorEvent.values[2]));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etAccx=(EditText) findViewById(R.id.et_accx);
        etAccy=(EditText) findViewById(R.id.et_accy);
        etAccz=(EditText) findViewById(R.id.et_accz);
        btgetVal=(Button) findViewById(R.id.bt_getvalues);
        btgetVal.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onClick(View view) {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        AccelerometerTask task = new AccelerometerTask(sensorEvent);
        myHandler.post(task);
        //runOnUiThread(task);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
