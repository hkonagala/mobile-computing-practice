package com.example.nilan.broadcastreceiverexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private final String TAG = "MyBroadcastReceiver";
    public MyReceiver() {
    }


    @Override
    public void onReceive(final Context context, final Intent intent) {


        final PendingResult result = goAsync();

        AsyncTask<String, Integer, String> myAsyncTask = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                String action = intent.getAction();
                Log.d(TAG, action);
                Toast.makeText(context, "Action: "+action, Toast.LENGTH_LONG).show();

                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //processing with the returned value
                result.finish();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        };
        myAsyncTask.execute();



    }
}
