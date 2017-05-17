package com.example.android.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 5000;
    EditText etUserName, etPassword;
    Button loginButton;
    TextView tvDisplayUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        etUserName = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        loginButton = (Button) findViewById(R.id.bt_login);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_login:
                Intent myIntent = new Intent(MainActivity.this, userHomeActivity.class);
                myIntent.putExtra("username", String.valueOf(etUserName.getText()));
                startActivityForResult(myIntent, REQUEST_CODE);
                break;
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE:

                break;
        }
    }
}
