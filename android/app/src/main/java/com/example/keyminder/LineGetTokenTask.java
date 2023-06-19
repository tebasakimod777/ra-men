package com.example.keyminder;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LineGetTokenTask extends AsyncTask<String, Void, String> {

    private HttpGetTask getTask = new HttpGetTask();

    @Override
    protected String doInBackground(String... strings) {

        Log.d("LineGetTokenTask", "LineGetTokenTask Called");

        try {
            return getToken(strings[0]);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getToken(String code) throws ExecutionException, InterruptedException {
        Uri uri = Uri.parse(Configuration.HOST_URL + "/token").buildUpon()
                .appendQueryParameter("code", code)
                .build();
        Log.d("Uri", uri.toString());

        return  getTask.execute(uri.toString(), "application/x-www-form-urlencoded; charset=UTF-8");
    }
}
