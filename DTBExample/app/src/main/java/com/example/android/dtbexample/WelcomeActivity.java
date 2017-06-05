package com.example.android.dtbexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView tvWelcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tvWelcomeMessage = (TextView) findViewById(R.id.tv_welcome_message);
        Intent intent = getIntent();
        String welcomeMessage = intent.getStringExtra("welcomeMessage");
        tvWelcomeMessage.setText(welcomeMessage);
    }


}
