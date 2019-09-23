package com.example.networkconnections;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class RequestParams {
    private HashMap<String,String> hashMap;
    StringBuilder stringBuilder = new StringBuilder();
    public RequestParams(){
        hashMap = new HashMap<>();
    }

    public RequestParams addParameeters(String key, String value)
    {
        try {
            hashMap.put(key, URLEncoder.encode(value,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getEncodedParams(){
        for(String keys: hashMap.keySet()){

            if(stringBuilder.length()>0){
                stringBuilder.append("&");
            }
            stringBuilder.append(keys + "=" + hashMap.get(keys));
        }

        return stringBuilder.toString();
    }

    public String getEncodedUrl(String url){
        return url + "?" + getEncodedParams();
    }

    public void encodedPostParams(HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        OutputStreamWriter oswriter = new OutputStreamWriter(connection.getOutputStream());
        oswriter.write(getEncodedParams());
        oswriter.flush();
    }
}
