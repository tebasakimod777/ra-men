package com.example.keyminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LineLoginTask loginTask = new LineLoginTask(this);
        LineGetTokenTask getTokenTask = new LineGetTokenTask();
        LineGetUserProfileTask getUserProfileTask = new LineGetUserProfileTask();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> json;
        SharedPreferences pref = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String code = parseIntent(getIntent());
        if (code == null) {
            loginTask.execute();
        } else {
            try {
                String response = getTokenTask.execute(code).get();
                json = objectMapper.readValue(response, new TypeReference<Map<String,Object>>(){});
                String access_token = (String) json.get("access_token");

                response = getUserProfileTask.execute(access_token).get();
                json = objectMapper.readValue(response, new TypeReference<Map<String,Object>>(){});
                String userId = (String) json.get("userId");

                editor.putString("access_token", access_token);
                editor.putString("userId", userId);
                editor.apply();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        Button finishButton = findViewById(R.id.finish_user_setting_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private String parseIntent(Intent intent) {
        // Intent の action を確認
        String action = intent.getAction();
        if (action == null || !action.equals(Intent.ACTION_VIEW)){
            return null;
        }
        // Uri を取り出す
        Uri uri = intent.getData();
        if (uri == null) {
            return null;
        }
        // code パラメータから Authorization Code を取り出す
        String code = uri.getQueryParameter("code");
        return code;
    }

}