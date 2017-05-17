package com.example.android.sensorexample;

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

public class GyroActivity extends AppCompatActivity implements SensorEventListener {

    EditText gyrox, gyroy, gyroz;
    Button btGotoAccl;
    Sensor gyrometer;
    private Handler myHandler = new Handler();

    private class GyroscopeTask implements Runnable{
        private SensorEvent sensorEvent;
        public GyroscopeTask(SensorEvent event){sensorEvent = event; }

        @Override
        public void run() {
            gyrox.setText(String.valueOf(sensorEvent.values[0]));
            gyroy.setText(String.valueOf(sensorEvent.values[1]));
            gyroz.setText(String.valueOf(sensorEvent.values[2]));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);

        gyrox = (EditText) findViewById(R.id.et_gyro_x);
        gyroy = (EditText) findViewById(R.id.et_gyro_y);
        gyroz = (EditText) findViewById(R.id.et_gyro_z);
        btGotoAccl = (Button) findViewById(R.id.bt_goto_accl);

        btGotoAccl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GyroActivity.this.finish();
            }
        });

        SensorManager sensorManager_ = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyrometer = sensorManager_.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager_.registerListener(this, gyrometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            GyroscopeTask gyroscopeTask = new GyroscopeTask(sensorEvent);
            myHandler.post(gyroscopeTask);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
