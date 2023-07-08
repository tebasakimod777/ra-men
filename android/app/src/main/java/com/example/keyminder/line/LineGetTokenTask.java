package com.example.keyminder.line;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.keyminder.Configuration;
import com.example.keyminder.network.HttpGetTask;

import java.util.concurrent.ExecutionException;

public class LineGetTokenTask extends AsyncTask<String, Void, String> {

    private HttpGetTask getTask = new HttpGetTask();

    private Activity parentActivity;

    private ProgressDialog dialog;

    public LineGetTokenTask(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

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

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String string) {
    }

    private String getToken(String code) throws ExecutionException, InterruptedException {
        Uri uri = Uri.parse(Configuration.HOST_URL + "/token").buildUpon()
                .appendQueryParameter("code", code)
                .build();
        Log.d("Uri", uri.toString());

        return  getTask.execute(uri.toString(), "application/x-www-form-urlencoded; charset=UTF-8");
    }
}
