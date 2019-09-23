package com.example.networkconnections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
//import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected())
                {
                    Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();
                    RequestParams reqparams = new RequestParams();
                    reqparams.addParameeters("name", "Pallav")
                            .addParameeters("age", "25")
                            .addParameeters("email","abcd@gmail.com");
                    new GetAsyncGetParams(reqparams).execute("http://api.theappsdr.com/simple.php");
                }
                else{
                    Toast.makeText(MainActivity.this, "is not connected", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo ==null || !networkInfo.isConnected()||(networkInfo.getType()!=ConnectivityManager.TYPE_WIFI &&
                networkInfo.getType()!=ConnectivityManager.TYPE_MOBILE))
        {
            return false;
        }
        return true;
    }

    private class GetAsyncData extends AsyncTask<String, Void, String> {


        @Override
        protected void onPostExecute(String s) {
            if(s!=null)
            {
                Log.d("demo",s);
            }else{
                Log.d("demo","null");
            }

        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sbuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader breader = null;
            String result = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                breader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line="";
                while((line = breader.readLine())!=null)
                {
                    sbuilder.append(line);
                }

                result=sbuilder.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null)
                {
                    connection.disconnect();
                }
                if(breader!=null){
                    try {
                        breader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
    }

    private class GetAsyncGetParams extends AsyncTask<String, Void, String>{

        RequestParams mParams;

        public GetAsyncGetParams(RequestParams params){
            mParams=params;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null)
            {
                Log.d("demo",s);
            }else{
                Log.d("demo","null");
            }

        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sbuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader breader = null;
            String result = null;
            try {
                URL url = new URL(mParams.getEncodedUrl(params[0]));
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    breader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line="";
                    while((line = breader.readLine())!=null)
                    {
                        sbuilder.append(line);
                    }

                    result=sbuilder.toString();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null)
                {
                    connection.disconnect();
                }
                if(breader!=null){
                    try {
                        breader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
    }

    private class GetAsyncPostParams extends AsyncTask<String, Void, String>{

        RequestParams mParams;

        public GetAsyncPostParams(RequestParams params){
            mParams=params;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result!=null)
            {
                Log.d("demo",result);
            }else{
                Log.d("demo","null");
            }

        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sbuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader breader = null;
            String result = null;
            try {
                URL url = new URL(mParams.getEncodedUrl(params[0]));
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                mParams.encodedPostParams(connection);
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    breader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line="";
                    while((line = breader.readLine())!=null)
                    {
                        sbuilder.append(line);
                    }

                    result=sbuilder.toString();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null)
                {
                    connection.disconnect();
                }
                if(breader!=null){
                    try {
                        breader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
    }


}
