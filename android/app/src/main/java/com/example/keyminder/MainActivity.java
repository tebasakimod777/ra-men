package com.example.keyminder;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // NotificationTask task = new NotificationTask(this);

        // LineNotificationTask notificationTask = new LineNotificationTask();

        NotificationManager notificationManager = new NotificationManager(this);

        Button notificationButton = findViewById(R.id.notification_button);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String channelId = "channel_id";
//                String title = "通知";
//                String message = "通知をお知らせします";
//                task.sendNotification(channelId, title, message);
                notificationManager.Notify("メッセージ");
            }
        });

        Button loginSettingButton = findViewById(R.id.login_setting_button);
        loginSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), LoginSettingActivity.class);
                startActivity(intent);
            }
        });

    }
}