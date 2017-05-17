package com.example.harikakonagala.serviceapplication;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service implements SensorEventListener {
    MyBinder myBinder_ = new MyBinder();
    SensorManager sensorManager;
    Sensor accelerometer;
    double x = Float.valueOf(0);
    double y = Float.valueOf(0);
    double z = Float.valueOf(0);


    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this, accelerometer);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myBinder_;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this){
            x = 0.9*x + (1 - 0.9)*event.values[0];
            y = 0.9*y + (1 - 0.9)*event.values[1];
            z = 0.9*z + (1 - 0.9)*event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }
    public float getRms(){
        synchronized (this) {
            return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        }
    }
}
