package com.example.keyminder;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.example.keyminder.notification.NotificationManager;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private TextView fileContentTextView;
    private SharedPreferences preferences;

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

        radioGroup = findViewById(R.id.radioGroup);
        fileContentTextView = findViewById(R.id.fileContentTextView);
        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        populateRadioGroup();
        restoreSelectedRadioButton();


        Button wifiActivitySwitchButton = (Button) findViewById(R.id.wifi_button);
        wifiActivitySwitchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplication(), WiFiRegistration.class);
                startActivity(intent);
            }
        });

        Button itemConfirmActivitySwitchButton = (Button) findViewById(R.id.item_button);
        itemConfirmActivitySwitchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplication(), ItemConfirmation.class);
                startActivity(intent);
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedFileName = radioButton.getText().toString();
                String fileContent = readFileContent(selectedFileName);
                fileContentTextView.setText(fileContent);

                // 選択された項目をSharedPreferencesに保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("selectedButtonId", checkedId);
                editor.apply();
            }
        });
    }

    private void populateRadioGroup() {
        File folder = new File(getFilesDir(), "items");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        addRadioButton(fileName);
                    }
                }
            }
        }
    }


    private void addRadioButton(String fileName) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(fileName);

        radioGroup.addView(radioButton);
    }

    private void restoreSelectedRadioButton() {
        int selectedButtonId = preferences.getInt("selectedButtonId", -1);
        if (selectedButtonId != -1) {
            RadioButton selectedButton = findViewById(selectedButtonId);
            selectedButton.setChecked(true);
        }
    }

    private String readFileContent(String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            File file = new File(getFilesDir() + "/items", fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
