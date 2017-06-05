package com.example.nilan.myalarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public MyBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Setting up the vibrator", Toast.LENGTH_LONG).show();
        Vibrator myvibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        myvibrator.vibrate(2000);
    }
}
