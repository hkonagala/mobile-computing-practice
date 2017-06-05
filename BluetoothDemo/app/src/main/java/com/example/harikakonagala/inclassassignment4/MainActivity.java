package com.example.harikakonagala.inclassassignment4;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private BluetoothAdapter myBluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // take an instance of BluetoothAdapter - Bluetooth radio
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(myBluetoothAdapter == null){

            //TODO
        }else{
            if(!myBluetoothAdapter.isEnabled()){
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, REQUEST_CODE);

                Toast.makeText(getApplicationContext(),"Bluetooth turned on" , Toast.LENGTH_LONG).show();

            }

            Set<BluetoothDevice> paired = myBluetoothAdapter.getBondedDevices();
            if(paired.size()>0){
                for(BluetoothDevice device : paired){
                    Log.d("PAIRED", device.getAddress());
                    Log.d("PAIRED", device.getName());

                }
            }
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);
            if(!myBluetoothAdapter.isDiscovering()){


                Intent discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverable);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mReceiver);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                String name = device.getName();
                String address = device.getAddress();
                Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_LONG).show();

                Log.d("BTDEVICE", name);
                Log.d("BTDEVICE", address);
            }
        }
    };
}
