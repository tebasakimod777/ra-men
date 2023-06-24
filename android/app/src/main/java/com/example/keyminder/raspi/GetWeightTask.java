package com.example.keyminder.raspi;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.keyminder.Configuration;
import com.example.keyminder.network.HttpGetTask;

import java.util.concurrent.ExecutionException;

public class GetWeightTask extends AsyncTask<String, Void, String> {


    HttpGetTask getTask = new HttpGetTask();

    @Override
    protected String doInBackground(String... strings) {
        Uri uri = Uri.parse(Configuration.HOST_URL + "/raspi").buildUpon()
                .build();
        return getTask.execute(uri.toString(), "application/x-www-form-urlencoded; charset=UTF-8");
    }


}
