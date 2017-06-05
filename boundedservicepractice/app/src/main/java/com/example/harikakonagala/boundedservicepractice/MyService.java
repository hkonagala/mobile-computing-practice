package com.example.harikakonagala.boundedservicepractice;

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

public class MyService extends Service implements SensorEventListener{
    MyBinder myBinder_ = new MyBinder();
    List<MyAccelerometerData> list= new ArrayList<>();
    int cur=0;
    Sensor accelerometer;

    @Override
    public void onCreate() {
        super.onCreate();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myBinder_;
    }

    class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            storeData(event.values[0], event.values[1], event.values[2]);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void storeData(float accx, float accy, float accz) {
        synchronized (this){
            MyAccelerometerData data = new MyAccelerometerData(new Float(accx), new Float(accy),new Float(accz));
                if(cur<99){
                    list.add(data);
                    cur++;
                }else{
                    if(cur == 99){
                        list.add(data);
                        cur = 0;
                    }
                }
            }
        }
    
    public String rms(){
        String res="";
        synchronized (this){
            double rmsx = new Float(0);
            for(MyAccelerometerData data: list){
                rmsx+= data.getRms();
                
            }
            int count = list.size();
            rmsx = Math.sqrt(rmsx/count);
            res= new Double(rmsx).toString();
            
        }
        return res;
    }
    }



