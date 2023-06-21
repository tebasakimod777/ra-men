package com.example.keyminder.raspi;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.keyminder.Configuration;
import com.example.keyminder.network.HttpGetTask;

public class GetWeightTask extends AsyncTask<String, Void, String> {


    HttpGetTask getTask = new HttpGetTask();

    @Override
    protected String doInBackground(String... strings) {
        Uri uri = Uri.parse(Configuration.HOST_URL + "/hogehoge").buildUpon()
                .build();
        return getTask.execute(uri.toString(), "application/x-www-form-urlencoded; charset=UTF-8");
    }


}
