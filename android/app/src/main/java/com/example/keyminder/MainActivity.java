package com.example.keyminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.keyminder.notification.NotificationManager;

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