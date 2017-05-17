package com.example.android.inclassassignment;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyAccelerometerService extends Service implements SensorEventListener{

    MyBinder myBinder_ = new MyBinder();
    List<AccelerometerData> list = new ArrayList<AccelerometerData>();
    int current = 0;
    Sensor accelerometer;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            storeData(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class MyBinder extends Binder {

        public MyAccelerometerService getService(){
            return MyAccelerometerService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder_;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SensorManager sensorManager_ = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager_.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public String getRMS(){
        String result;
        synchronized (this) {
            double rmsX = new Float(0);
            for (AccelerometerData dataItem : list) {
                rmsX = rmsX + dataItem.getRMSItem();
            }

            int count = list.size();
            rmsX = Math.sqrt(rmsX / count);
            result = new Double(rmsX).toString();
        }
        return result;
    }

    public void storeData(float accx, float accy, float accz){
        synchronized (this) {
            AccelerometerData dataItem = new AccelerometerData(new Float(accx), new Float(accy), new Float(accz));
            if (current < 99) {
                list.add(dataItem);
                current++;
            } else if (current == 99) {
                list.add(dataItem);
                current = 0;
            }
        }
    }
}