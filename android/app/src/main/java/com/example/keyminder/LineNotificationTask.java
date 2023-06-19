package com.example.keyminder;

import android.net.Uri;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

public class LineNotificationTask extends AsyncTask<String, Void, String> {

    private HttpPostTask postTask = new HttpPostTask();

    @Override
    protected String doInBackground(String... strings) {
        try {
            return sendNotification(strings[0], strings[1]);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    String sendNotification(String userId, String messages) throws ExecutionException, InterruptedException {
        Uri uri = Uri.parse(Configuration.HOST_URL + "/sendMessage").buildUpon()
                .appendQueryParameter("userId", userId)
                .appendQueryParameter("messages", messages)
                .build();
        return postTask.execute(uri.toString(), "application/x-www-form-urlencoded; charset=UTF-8");
    }
}
