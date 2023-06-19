package com.example.keyminder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.util.UUID;

public class LineLoginTask extends AsyncTask<String, Void, Void> {
    private Activity parentActivity;
    public LineLoginTask(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    protected Void doInBackground(String... strings) {

        Uri uri = Uri.parse(Configuration.HOST_URL + "/login").buildUpon().build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        parentActivity.startActivity(intent);

        return null;
    }

}
