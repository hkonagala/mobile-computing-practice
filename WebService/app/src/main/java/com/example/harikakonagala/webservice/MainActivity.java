package com.example.harikakonagala.webservice;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String latitude = "49.5";
        String longitude = "-72.5";
        String timestamp = "2017-04-13 13:29:00";
        String id = "harika";

        String[] input = new String[4];
        input[0] = latitude;
        input[1] = longitude;
        input[2] = timestamp;
        input[3] = id;

        InvokeWebService mywebservice = new InvokeWebService();
        mywebservice.execute(input);

    }

    private class InvokeWebService extends AsyncTask<String, Integer, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            String response = "";
            String requestURL = "find an ip address of webserver from the laptop and paste here"
            //similar to run method
            try
            {

                url = new URL(requestURL);
                HttpURLConnection myconnection = (HttpURLConnection) url.openConnection();
                myconnection.setConnectTimeout(15000);
                myconnection.setRequestMethod("POST");
                myconnection.setDoInput(true);
                myconnection.setDoOutput(true);

                OutputStream out = myconnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                StringBuilder str = new StringBuilder();

                str.append("latitude" +params[0]+ "&").append("longitude")+params[1]+"&");
                str.append("timestamp" +params[2]+ "&").append("id")+params[3]+"&");

                String urstr = str.toString();
                writer.write(urstr);
                writer.flush();
                writer.close();

                int respondeCode = myconnection.getResponseCode();
                if(respondeCode == HttpURLConnection.HTTP_OK){
                    BufferedReader br= new BufferedReader(new InputStreamReader(
                       myconnection.getInputStream()
                    ));
                    line = br.readLine();
                    while (line!= null){
                        response += line;
                        line =br.readLine();
                    }
                    br.close();
                }

                myconnection.disconnect();

               // writer.write();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
