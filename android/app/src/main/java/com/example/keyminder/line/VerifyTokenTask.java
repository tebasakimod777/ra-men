package com.example.keyminder.line;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.keyminder.Configuration;
import com.example.keyminder.network.HttpGetTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class VerifyTokenTask extends AsyncTask<String, Void, String> {

    private HttpGetTask getTask = new HttpGetTask();

    @Override
    protected String doInBackground(String... strings) {
        Integer status_code = null;
        String response = verifyToken(strings[0]);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> json;
        try {
            json = objectMapper.readValue(response, new TypeReference<Map<String,Object>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        status_code = (Integer) json.get("status_code");
        Log.d("verify status_code", String.valueOf(status_code));
        if (status_code == 200) {
            return "valid";
        } else {
            return  "invalid";
        }
    }

    protected String verifyToken(String access_token) {
        Uri uri = Uri.parse(Configuration.HOST_URL + "/verify").buildUpon()
                .appendQueryParameter("access_token", access_token)
                .build();
        return getTask.execute(uri.toString(), "application/x-www-form-urlencoded; charset=UTF-8");
    }
}

