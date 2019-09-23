package com.example.networkconnections;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements GetTweetsAsyncData.IDATA {
    TextView tv;

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
                    //new GetAsyncGetParams(reqparams).execute("http://api.theappsdr.com/params.php");
                    new GetTweetsAsyncData(MainActivity.this).execute("");
                    new GetImageAsyncData((ImageView)findViewById(R.id.imageView)).execute("https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Android_greenrobot-01_aRFK1TB.max-2800x2800.jpg");
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

    @Override
    public void handleListData(final LinkedList<String> data) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        tv= findViewById(R.id.textView);
        alertBuilder.setTitle("Tweets");
        alertBuilder.setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv.setText(data.get(which).toString());
            }
        });
        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private class GetImageAsyncData extends AsyncTask<String, Void, Void> {

        ImageView imageView;
        Bitmap bitmap=null;
        public GetImageAsyncData(ImageView iv) {
            imageView=iv;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(bitmap!=null && imageView!=null)
            {
                imageView.setImageBitmap(bitmap);
            }
        }


        @Override
        protected Void doInBackground(String... params) {
            StringBuilder sbuilder = new StringBuilder();
            HttpURLConnection connection = null;
            bitmap = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
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
            }
            return null;
        }
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
            String result = "";
            try {
                URL url = new URL(mParams.getEncodedUrl(params[0]));
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    result= IOUtils.toString(connection.getInputStream(), "UTF8");

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
