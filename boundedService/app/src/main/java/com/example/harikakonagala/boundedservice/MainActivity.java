package com.example.harikakonagala.boundedservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private MessengerService myservice_;
    public final String key1="username";
    private boolean bound;
    Messenger mymessenger;
    private Button mybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bound=false;

        SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key1,"harika");
        editor.commit();
        mybutton=(Button) findViewById(R.id.button);
        mybutton.setOnClickListener(this);

        // Intent intent=new Intent(this, MessengerService.class);
        //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mymessenger = new Messenger(service);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound=false;
        }
    };
        public void onClick(View v){
            switch(v.getId()){
                case R.id.button:
                    if(bound){
                        Message msg= Message.obtain(null, MessengerService.MSG_ACTIVITY,0,0);
                        try{
                            mymessenger.send(msg);}
                        catch (Exception e){
                            e.printStackTrace();
                        }
                }break;
                case R.id.button2:
                    SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);
            }
        }

}
