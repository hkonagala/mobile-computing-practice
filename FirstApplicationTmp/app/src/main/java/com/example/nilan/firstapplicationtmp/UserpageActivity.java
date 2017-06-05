package com.example.nilan.firstapplicationtmp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UserpageActivity extends AppCompatActivity {

    private TextView myTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);
        myTextView = (TextView)findViewById(R.id.textView5);
        Intent myIntent = getIntent();
        String uname = myIntent.getStringExtra("Username");
        myTextView.setText(uname);
    }
}
