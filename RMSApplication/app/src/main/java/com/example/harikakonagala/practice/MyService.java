package com.example.harikakonagala.practice;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service implements SensorEventListener{
    MyBinder myBinder_ = new MyBinder();
    Sensor accelerometer;
    SensorManager sensorManager;
    SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "MyPrefsFile";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return myBinder_;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
         sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Float rms = new Float(Math.sqrt(Math.pow(event.values[0],2)+Math.pow(event.values[1],2)+Math.pow(event.values[2],2)));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("rms", rms);

        // Commit the edits!
        editor.commit();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class MyBinder extends Binder {
        public MyService getService(){
            return MyService.this;
        }
    }

    public Float getRms(){
        return sharedPreferences.getFloat("rms", 0);
    }
}
