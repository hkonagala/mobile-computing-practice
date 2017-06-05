package com.example.android.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class userHomeActivity extends AppCompatActivity {


    TextView tvDisplayUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();
        String userName = myIntent.getStringExtra("username");
        setContentView(R.layout.activity_user_home);
        tvDisplayUserName = (TextView) findViewById(R.id.tv_display_username);
        tvDisplayUserName.setText(userName);
    }
}
