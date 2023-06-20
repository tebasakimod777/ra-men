package com.example.keyminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.keyminder.line.LineLogoutTask;
import com.example.keyminder.line.VerifyTokenTask;

import java.util.concurrent.ExecutionException;

public class LoginSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_setting);

        SharedPreferences pref = getSharedPreferences("prefs", MODE_PRIVATE);
        String access_token = pref.getString("access_token", "");


        Button finishLoginSettingButton = findViewById(R.id.finish_login_setting_button);
        finishLoginSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button loginoutButton = findViewById(R.id.loinout_button);

        VerifyTokenTask verifyTokenTask = new VerifyTokenTask();
        String token_state;
        try {
            token_state = verifyTokenTask.execute(access_token).get();
            if (token_state == "invalid") {
                loginoutButton.setText("ログイン");
                loginoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                loginoutButton.setText("ログアウト");
                loginoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LineLogoutTask logoutTask = new LineLogoutTask(LoginSettingActivity.this);
                        logoutTask.execute(access_token);
                        Intent intent = new Intent(getApplication(), LoginSettingActivity.class);
                        startActivity(intent);
                    }
                });
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}