package com.example.harikakonagala.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mauth;
    EditText email, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        register = (Button) findViewById(R.id.button);

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button;
                mauth.createUserWithEmailandPassword(email.toString(),password.toString()),
                addOnComp
        }
    }
}
