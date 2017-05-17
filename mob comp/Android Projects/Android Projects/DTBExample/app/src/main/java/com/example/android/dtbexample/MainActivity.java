package com.example.android.dtbexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etFirstName, etLastName, etPhoneNumber, etEmail, etPassword;
    Button btSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etFirstName = (EditText) findViewById(R.id.et_firstName);
        etLastName = (EditText) findViewById(R.id.et_lastName);
        etPhoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btSignUp = (Button) findViewById(R.id.bt_signup);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_signup:
                String message = "Welcome " + etFirstName.getText().toString() + " " + etLastName.getText().toString();
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.putExtra("welcomeMessage", message);
                startActivity(intent);
                break;
        }
    }
}
