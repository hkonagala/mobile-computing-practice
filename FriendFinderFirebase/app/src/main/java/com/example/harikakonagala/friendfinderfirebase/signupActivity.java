package com.example.harikakonagala.friendfinderfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class signupActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "signupActivity";
    EditText username, email, pwd;
    Button signup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mydb;
    private DatabaseReference mydbchildusers;
    private FirebaseUser user;
    String emailText, passwordText;
    private String usernameText;
    ProgressDialog progressDialog;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        mydb = FirebaseDatabase.getInstance().getReference();
        mydbchildusers = mydb.child("users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();
                if(user != null){
                    long currentDate = Calendar.getInstance().getTimeInMillis();
                    UserInformation myUser = new UserInformation(mAuth.getCurrentUser().getUid(), emailText, usernameText, currentDate, 0, 0);
                    mydbchildusers.child(mAuth.getCurrentUser().getUid()).setValue(myUser);
                    handler.post(new RunOnMainUI("Registration Successful"));
                    // add items to shared preferences
                    SharedPreferences userDetails = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor =  userDetails.edit();
                    editor.putString("user.email", myUser.email);
                    editor.putString("user.username", myUser.username);
                    editor.putLong("user.timestamp", myUser.timestamp);
                    editor.putFloat("user.latitude", (float) myUser.latitude);
                    editor.putFloat("user.longitude", (float) myUser.longitude);
                    editor.apply();
                    finish();
                    Intent intent = new Intent(signupActivity.this, MapsActivity.class);
                    intent.putExtra("email", String.valueOf(myUser.email));
                    intent.putExtra("username", String.valueOf(myUser.username));
                    startActivity(intent);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
                else
                {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        username = (EditText) findViewById(R.id.et_username);
        email = (EditText) findViewById(R.id.et_signupemail);
        pwd = (EditText) findViewById(R.id.et_signuppwd);
        signup = (Button) findViewById(R.id.bt_signup);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        signup.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        signup.setOnClickListener(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    public void onClick(View v) { saveUserInfo();}

    private void saveUserInfo() {
        usernameText = username.getText().toString().trim();

        emailText = email.getText().toString().trim();
        passwordText = pwd.getText().toString().trim();
        //creating a new user
        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        //checking if success
                        if(task.isSuccessful() && mAuth.getCurrentUser() != null){
                            progressDialog.dismiss();
                        } else{
                            //display some message here
                            handler.post(new RunOnMainUI("Registration Error"));
                        }
                    }
                });
    }

    private class RunOnMainUI implements Runnable {
        String s;

        public RunOnMainUI(String s) {
            this.s = s;
        }

        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
}
