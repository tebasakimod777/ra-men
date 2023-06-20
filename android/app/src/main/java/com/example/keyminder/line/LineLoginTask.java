package com.example.keyminder.line;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.keyminder.Configuration;

import java.util.PrimitiveIterator;
import java.util.UUID;

public class LineLoginTask extends AsyncTask<String, Void, Void> {
    private Activity parentActivity;

    private ProgressDialog dialog;

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

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(parentActivity);
        dialog.setMessage("Loading");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void voids) {
        dialog.dismiss();
    }

}
