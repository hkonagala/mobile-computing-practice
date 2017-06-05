package com.example.harikakonagala.friendfinderfirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private String email, userName;
    private LatLng currentLocation;
    private Handler mHandler;
    private boolean isFocused = false;
    private LocationManager locationManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mydb;
    private DatabaseReference mydbchildusers;
    private FirebaseUser user;
    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent myIntent = getIntent();
        email = myIntent.getStringExtra("email");
        userName = myIntent.getStringExtra("username");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        SharedPreferences userDetails = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (user != null) {
            userInformation = new UserInformation(user.getUid(),
                    userDetails.getString("user.email", ""),
                    userDetails.getString("user.username", ""),
                    userDetails.getLong("user.timestamp", 0),
                    userDetails.getFloat("user.latitude", 0),
                    userDetails.getFloat("user.longitude", 0)
            );
        }else {
            startActivity(new Intent(this, MainActivity.class));
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        mHandler = new Handler();
        startRepeatingTask();
    }



    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
        stopRepeatingTask();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (currentLocation != null) {
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
        email = "";
        userName = "";
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                if (currentLocation != null) {
                    String[] input = new String[2];
                    input[0] = String.valueOf(currentLocation.latitude);
                    input[1] = String.valueOf(currentLocation.longitude);
                    GetFriendsTask getFriendsTask = new GetFriendsTask();
                    getFriendsTask.execute(input);
                }
            } finally {
                int interval = 5000;
                mHandler.postDelayed(mStatusChecker, interval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    @Override
    public void onLocationChanged(Location location) {
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

        String[] input = new String[2];
        input[1] = latitude;
        input[2] = longitude;
        UpdateLocationTask updateLocationTask = new UpdateLocationTask();
        updateLocationTask.execute(input);

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

    class UpdateLocationTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            URL url;
            String response = "";
            //String domain = getString(R.string.domain);
            //String requestUrl = domain + getString(R.string.updateUserDetailsPage);
            try{
                //url = new URL(requestUrl);
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                myConnection.setReadTimeout(15000);
                myConnection.setConnectTimeout(15000);
                myConnection.setRequestMethod("POST");
                myConnection.setDoInput(true);
                myConnection.setDoOutput(true);

                OutputStream os = myConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

                String requestJsonString = new JSONObject()
                        .put("latitude", params[0])
                        .put("longitude", params[1])
                        .toString();

                Log.d("REQUEST BODY : ", requestJsonString);
                bw.write(requestJsonString);
                bw.flush();
                bw.close();

                int responseCode = myConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                    line = br.readLine();
                    while(line != null){
                        response += line;
                        line = br.readLine();
                    }
                    br.close();
                }
                myConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            Log.d("RESPONSE BODY:", response);
            return response;

        }
    }

    private class GetFriendsTask extends AsyncTask<String, Integer, List<UserInformation>>{
        @Override
        protected List<UserInformation> doInBackground(String... params) {
            URL url;
            String response = "";
            //String domain = getString(R.string.domain);
            //String requestUrl = domain + getString(R.string.getFriendsPage);
            List<UserInformation> friends = new ArrayList<>();
            try{
                //url = new URL(requestUrl);
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                myConnection.setReadTimeout(15000);
                myConnection.setConnectTimeout(15000);
                myConnection.setRequestMethod("POST");
                myConnection.setDoInput(true);
                myConnection.setDoOutput(true);

                OutputStream os = myConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

                String requestJsonString = new JSONObject()
                        .put("latitude", params[0])
                        .put("longitude", params[1])
                        .toString();

                Log.d("REQUEST BODY : ", requestJsonString);
                bw.write(requestJsonString);
                bw.flush();
                bw.close();

                int responseCode = myConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                    line = br.readLine();
                    while(line != null){
                        response += line;
                        line = br.readLine();
                    }
                    br.close();
                }
                Log.d("RESPONSE BODY: ", response);
                JSONArray jsonArray = new JSONArray(response);
                if(jsonArray.length() > 0){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject childJsonObj = jsonArray.getJSONObject(i);
                        UserInformation friend = new UserInformation(childJsonObj.getString("userId"),
                                childJsonObj.getString("email"),
                                childJsonObj.getString("username"),
                                childJsonObj.getLong("timestamp"),
                                childJsonObj.getDouble("latitude"),
                                childJsonObj.getDouble("longitude"));
                        friends.add(friend);
                    }
                }
                myConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return friends;
        }


    }
}
