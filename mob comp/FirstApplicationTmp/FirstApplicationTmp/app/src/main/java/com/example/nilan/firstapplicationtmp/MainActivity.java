package com.example.nilan.firstapplicationtmp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    EditText login, password;
    public final static int REQUEST_CODE = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText)findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        loginButton = (Button)findViewById(R.id.buttonlogin);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.buttonlogin:
                //check to see if the password is correct
                String logintxt = login.getText().toString();
                Intent myIntent = new Intent(this,UserpageActivity.class);
                myIntent.putExtra("Username", logintxt);
                startActivityForResult(myIntent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE:
                break;
        }
    }
}
