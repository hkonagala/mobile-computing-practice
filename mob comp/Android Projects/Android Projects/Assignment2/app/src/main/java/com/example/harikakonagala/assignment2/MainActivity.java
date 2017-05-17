package com.example.harikakonagala.assignment2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editEmail, editPassword, editName;
    Button btnSignIn, btnRegister;

    String URL= "http://localhost:81/friendfinder/index.php";
    JSONParser jsonParser=new JSONParser();

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptLogin attemptLogin = new AttemptLogin();
                attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), "");
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    i = 1;
                    editEmail.setVisibility(View.VISIBLE);
                    btnSignIn.setVisibility(View.GONE);
                    btnRegister.setText("CREATE ACCOUNT");
                } else {

                    btnRegister.setText("REGISTER");
                    editEmail.setVisibility(View.GONE);
                    btnSignIn.setVisibility(View.VISIBLE);
                    i = 0;

                    AttemptLogin attemptLogin = new AttemptLogin() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            return null;
                        }
                    };
                    attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString());

                }
            }
        });
    }


    private abstract class AttemptLogin extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject o) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (o != null) {
                    Toast.makeText(getApplicationContext(),o.getString("message"),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String email = args[2];
            String password = args[1];
            String name= args[0];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));
            if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;
        }
    }
}

