package com.example.networkconnections;

import android.os.AsyncTask;

import java.util.LinkedList;

public class GetTweetsAsyncData extends AsyncTask<String, Void, LinkedList<String>> {

    IDATA idata;

    public GetTweetsAsyncData(IDATA idata) {
        this.idata = idata;
    }

    @Override
    protected LinkedList<String> doInBackground(String... strings) {
        LinkedList<String> tweets = new LinkedList<>();
        for(int i=0;i<10;i++)
        {
            tweets.add("Tweet" + i);
        }
        return tweets;
    }

    @Override
    protected void onPostExecute(LinkedList<String> strings) {
        idata.handleListData(strings);
    }

    public static interface IDATA{
        public void handleListData(LinkedList<String> data);
    }
}
