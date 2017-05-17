package com.example.harikakonagala.notificationdemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity implements View.OnClickListener {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;
    private int notification_id = 100;
    private String NOTIFICATION_ID = "NotificationID";
    private NotificationCompat.Builder notification_builder;
    private NotificationManagerCompat notification_manager;
    Button notificationbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);

        notificationbutton = (Button) findViewById(R.id.notificationbutton);
        notificationbutton.setOnClickListener(this);
        generateNotification();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
        }
    }

    private void generateNotification(){
        Intent myIntent = new Intent(this, NewActivity.class);
        myIntent.putExtra(NOTIFICATION_ID, notification_id);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,myIntent,0);

        Intent newIntent = new Intent(Intent.ACTION_VIEW);
        Uri myUri = new Uri.parse("http://www.umbc.edu");
        newIntent.setData(myUri);
        PendingIntent pin = new PendingIntent.getActivity(this, 0, newIntent, 0);

        Intent wearbale = new Intent(this, myWearableActivity.class);
        PendingIntent penint = PendingIntent.getActivity(this, 0, wearbale, 0);
        NotificationCompat.Action wearbale = new NotificationCompat.Action.Builder(android.R.drawable.ic_dialog_info,
                "only for wearbles", penint).build();


        notification_builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                .setContentTitle("this is a simple notification")
                .setContentText("hey!!")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.ic_dialog_info, "umbc webpage",pin)
        .extend(new NotificationCompat.WearableExtender().addAction(wearbale));

        notification_manager = NotificationManagerCompat.from(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.notificationbutton:
                notification_manager.notify(notification_id , notification_builder.build());
                break;
        }
    }
}
