package com.example.nilan.myalarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final int BROADCASTREQUEST = 100;
    EditText myEditText;
    Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myEditText = (EditText) findViewById(R.id.editText);
        myButton = (Button) findViewById(R.id.button);
        myButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button:
                int interval = new Integer(myEditText.getText().toString()).intValue();
                Intent myintent = new Intent(this, MyBroadcastReceiver.class);
                PendingIntent mypendingintent = PendingIntent.getBroadcast(getApplicationContext()
                , BROADCASTREQUEST, myintent, 0);
                AlarmManager myalarmmanager = (AlarmManager) getSystemService(ALARM_SERVICE);
                myalarmmanager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+interval*1000,
                        mypendingintent);
                Toast.makeText(getApplicationContext(), "Alarm set to "+interval+"seconds", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
