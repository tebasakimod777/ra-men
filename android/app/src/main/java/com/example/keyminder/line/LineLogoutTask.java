package com.example.keyminder.line;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.keyminder.Configuration;
import com.example.keyminder.network.HttpPostTask;

import java.util.concurrent.ExecutionException;

public class LineLogoutTask extends AsyncTask<String, Void, String> {
    private HttpPostTask postTask = new HttpPostTask();

    @Override
    protected String doInBackground(String... strings) {
        try {
            return logout(strings[0]);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    String logout(String access_token) throws ExecutionException, InterruptedException {
        Uri uri = Uri.parse(Configuration.HOST_URL + "/logout").buildUpon()
                .appendQueryParameter("access_token", access_token)
                .build();
        return postTask.execute(uri.toString(), "application/x-www-form-urlencoded; charset=UTF-8");
    }
}
