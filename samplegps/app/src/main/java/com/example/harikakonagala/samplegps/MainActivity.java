package com.example.harikakonagala.samplegps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    protected LocationManager lm;
    TextView lat1, lon1, tvProvider;
    Handler myHandler = new Handler();
    Timer timer = new Timer();
    Location gpsLocation, networkLocation, bestLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lat1 = (TextView) findViewById(R.id.et_lat1);
        lon1 = (TextView) findViewById(R.id.et_lon1);
        tvProvider = (TextView) findViewById(R.id.tv_provider);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    public class MyTask extends TimerTask {

        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
    }

    final Handler h = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return false;
            }
            bestLocation = gpsLocation;
            if (gpsLocation != null){
                if (networkLocation != null){
                    if (gpsLocation.getTime() > networkLocation.getTime()){
                        bestLocation = gpsLocation;
                    } else{
                        bestLocation = networkLocation;
                    }
                }
            } else{
                bestLocation = networkLocation;
            }
            if (bestLocation != null) {
                LocationTask myTask = new LocationTask(bestLocation);
                myHandler.post(myTask);
                Log.d("MAINACTIVITY _ LOCATION", bestLocation.getProvider());
                tvProvider.setText(bestLocation.getProvider());
            }
            return false;
        }
    });

    LocationListener locationListenerGps = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if ((ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                return;
            }
            Log.d("G", String.valueOf(location.getTime()));
            Log.d("G", String.valueOf(location.getLatitude()));
            Log.d("G", String.valueOf(location.getLongitude()));
            gpsLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if ((ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                return;
            }
            Log.d("N", String.valueOf(location.getTime()));
            Log.d("N", String.valueOf(location.getLatitude()));
            Log.d("N", String.valueOf(location.getLongitude()));
            networkLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
    };

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }
        bestLocation = gpsLocation;
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps, null);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork, null);
        if (gpsLocation != null){
            if (networkLocation != null){
                if (gpsLocation.getTime() > networkLocation.getTime()){
                    bestLocation = gpsLocation;
                } else{
                    bestLocation = networkLocation;
                }
            }
        } else{
            bestLocation = networkLocation;
        }
        if (bestLocation != null) {
            LocationTask myTask = new LocationTask(bestLocation);
            myHandler.post(myTask);
            Log.d("MAINACTIVITY _ LOCATION", bestLocation.getProvider());
            tvProvider.setText(bestLocation.getProvider());
        }
        timer = new Timer();
        timer.schedule(new MyTask(), 2000, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }
        lm.removeUpdates(locationListenerGps);
        lm.removeUpdates(locationListenerNetwork);
        timer.cancel();
        timer.purge();
    }

    public class LocationTask implements Runnable{
        Location location;

        public LocationTask(Location location) {
            this.location = location;
        }

        @Override
        public void run() {
            lat1.setText(String.valueOf(location.getLatitude()));
            lon1.setText(String.valueOf(location.getLongitude()));
        }
    }
}
