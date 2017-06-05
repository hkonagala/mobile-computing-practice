package com.example.harikakonagala.friendfinderfirebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    EditText email, password;
    Button login, register;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mydb;
    private DatabaseReference mydbchildusers;
    private FirebaseUser user;
    private String emailText;
    private String passwordText;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mydb = FirebaseDatabase.getInstance().getReference();
        mydbchildusers = mydb.child("users");

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();
                //check if user already logged in and if so bypass and move to main menu
                if (user != null) {
                    String userId = user.getUid();
                    // Get details from database
                    mydbchildusers.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserInformation dbUser = dataSnapshot.getValue(UserInformation.class);
                            // add items to shared preferences
                            if (dbUser != null) {
                                SharedPreferences userDetails = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = userDetails.edit();
                                editor.putString("user.email", dbUser.email);
                                editor.putString("user.username", dbUser.username);
                                editor.putLong("user.timestamp", dbUser.timestamp);
                                editor.putFloat("user.latitude", (float) dbUser.latitude);
                                editor.putFloat("user.longitude", (float) dbUser.longitude);
                                editor.apply();
                                finish();
                                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                intent.putExtra("email", String.valueOf(dbUser.email));
                                intent.putExtra("username", String.valueOf(dbUser.username));
                                startActivity(intent);
                            } else{
                                handler.post(new RunOnMainUI("Cannot retreive user details!"));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mAuth.getCurrentUser().getUid());
                }
                else
                {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        email = (EditText) findViewById(R.id.et_loginemail);
        password = (EditText) findViewById(R.id.et_loginpwd);
        login = (Button) findViewById(R.id.bt_login);
        register = (Button) findViewById(R.id.bt_register);



    }

    @Override
    protected void onResume() {
        super.onResume();
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        login.setOnClickListener(null);
        register.setOnClickListener(null);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                //startActivity(new Intent(this, MapsActivity.class));
                userLogin();
                break;
            case R.id.bt_register:
                startActivity(new Intent(this, signupActivity.class));
                break;
        }

    }

    private void userLogin() {
        emailText = email.getText().toString().trim();
        passwordText = password.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(emailText)) {
            handler.post(new RunOnMainUI("Please enter email"));
            return;
        }

        if (TextUtils.isEmpty(passwordText)) {
            handler.post(new RunOnMainUI("Please enter password"));
            return;
        }

        //logging in the user
        mAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        //if the task is successful
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            handler.post(new RunOnMainUI("User not registered"));
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
