package com.example.keyminder.line;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.keyminder.Configuration;
import com.example.keyminder.network.HttpGetTask;

import java.util.concurrent.ExecutionException;

public class LineGetUserProfileTask extends AsyncTask<String, Void, String> {

    private HttpGetTask getTask = new HttpGetTask();

    private Activity parentActivity;

    private ProgressDialog dialog;

    public LineGetUserProfileTask(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    protected String doInBackground(String... strings) {
        try {
            return getUserProfile(strings[0]);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String string) {
    }

    private String getUserProfile(String access_token) throws ExecutionException, InterruptedException {
        Uri uri = Uri.parse(Configuration.HOST_URL + "/userProfile").buildUpon()
                .appendQueryParameter("access_token", access_token)
                .build();
        return  getTask.execute(uri.toString(), "application/x-www-form-urlencoded; charset=UTF-8");
    }

}
