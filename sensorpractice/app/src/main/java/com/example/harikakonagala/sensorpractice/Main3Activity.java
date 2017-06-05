package com.example.harikakonagala.sensorpractice;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity implements LocationListener{
    TextView textView;
    EditText editText;
    Button button;
    LocationManager locationManager;
    Location gpslocation, networklocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textView = (TextView) findViewById(R.id.textView_1);
        editText = (EditText) findViewById(R.id.editText_1);
        button = (Button) findViewById(R.id.button_1);
        button.setOnClickListener((View.OnClickListener) this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,gpslocation,null);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,networklocation,null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(gpslocation);
        locationManager.removeUpdates(networklocation);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
