package com.example.android.inclassassignment3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity: ";
    EditText etEmail, etFullName, etPassword;
    Button btRegister, btGotoLogin;
    private DatabaseReference mydb;
    private DatabaseReference mydbchildusers;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.et_email);
        etFullName = (EditText) findViewById(R.id.et_fullName);
        etPassword = (EditText) findViewById(R.id.et_password);
        btRegister = (Button) findViewById(R.id.bt_register);
        btGotoLogin = (Button) findViewById(R.id.bt_goto_login);

        mydb = FirebaseDatabase.getInstance().getReference();
        mydbchildusers = mydb.child("users");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent myIntent = new Intent(MainActivity.this, HomeActivity.class);
                    myIntent.putExtra("userFullName", etFullName.getText().toString());
                    startActivity(myIntent);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        btRegister.setOnClickListener(this);
        btGotoLogin.setOnClickListener(this);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User tmp = dataSnapshot.getValue(User.class);
                if (tmp != null) {
                    Toast.makeText(getApplicationContext(), tmp.fullName, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mydbchildusers.child("pkanna1@umbc\uff0eedu").addValueEventListener(eventListener);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_goto_login:
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(myIntent);
                break;
            case R.id.bt_register:
                User myUser = new User(etEmail.getText().toString(), etFullName.getText().toString(), etPassword.getText().toString());
                mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, R.string.auth_failed,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                mydbchildusers.child(JSONObject.quote(etEmail.getText().toString().replace(".", "\uff0e"))).setValue(myUser);
                break;
        }
    }
}
