package com.example.keyminder;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import com.example.keyminder.notification.NotificationManager;
import com.example.keyminder.raspi.GetWeightTask;
import com.example.keyminder.raspi.WeightChecker;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private TextView fileContentTextView;
    private SharedPreferences preferences;
    private boolean isWifiConnected = false;
    private String savedIPAddress = "";

    private TextView txtMatchStatus;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMatchStatus = findViewById(R.id.txtMatchStatus);

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
                String fileContent = readFileContent(selectedFileName + ".txt");
                // fileContentTextView.setText(fileContent);

                // 選択された項目をSharedPreferencesに保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("selectedButtonId", checkedId);
                editor.apply();
            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!savedIPAddress.isEmpty()) {
                    checkIPAddressMatch();
                }
            }
        }, 0, 10000); // 5秒ごとに実行
    }

    private void populateRadioGroup() {
        File folder = new File(getFilesDir(), "items");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (file.isFile()) {
                        String fileName = file.getName();
                        addRadioButton(fileName, i);
                    }
                }
            }
        }
    }

    private void addRadioButton(String fileName, final int radioButtonIndex) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(fileName.substring(0,fileName.length()-4));
        radioButton.setTextSize(20);

        // ラジオボタンの位置（インデックス）をタグとして設定
        radioButton.setTag(radioButtonIndex);

        radioGroup.addView(radioButton);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton selectedButton = (RadioButton) view;
                int selectedButtonIndex = (int) selectedButton.getTag();
                String selectedFileName = selectedButton.getText().toString() + ".txt";
                String fileContent = readFileContent(selectedFileName);
                // fileContentTextView.setText(fileContent);

                // 選択された項目の位置（インデックス）をSharedPreferencesに保存
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("selectedButtonIndex", selectedButtonIndex);
                editor.apply();
            }
        });
    }

    private void restoreSelectedRadioButton() {
        int selectedButtonIndex = preferences.getInt("selectedButtonIndex", -1);
        if (selectedButtonIndex != -1 && selectedButtonIndex < radioGroup.getChildCount()) {
            RadioButton selectedButton = (RadioButton) radioGroup.getChildAt(selectedButtonIndex);
            selectedButton.setChecked(true);
            // 選択された項目の内容を表示
            String selectedFileName = selectedButton.getText().toString();
            String fileContent = readFileContent(selectedFileName);
            // fileContentTextView.setText(fileContent);
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

    private String readTextFromFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = openFileInput("saved_text.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String getIPAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address.getAddress().length == 4) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void checkIPAddressMatch() {
        String ipAddress = getIPAddress();
        String savedIPAddress = readTextFromFile();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                int checkedId = preferences.getInt("selectedButtonIndex", -1);
//                double savedWeight = 0.0;
//                if (checkedId != -1) {
//                    RadioButton radioButton = findViewById(checkedId);
//                    String selectedFileName = radioButton.getText().toString();
//                    String fileContent = readFileContent(selectedFileName);
//                    savedWeight = Double.parseDouble(fileContent);
//                }

                double savedWeight = 0.0;
                int selectedButtonIndex = preferences.getInt("selectedButtonIndex", -1);
                if (selectedButtonIndex != -1 && selectedButtonIndex < radioGroup.getChildCount()) {
                    RadioButton selectedButton = (RadioButton) radioGroup.getChildAt(selectedButtonIndex);
                    selectedButton.setChecked(true);
                    // 選択された項目の内容を表示
                    String selectedFileName = selectedButton.getText().toString() + ".txt";;
                    String fileContent = readFileContent(selectedFileName);
                    savedWeight = Double.parseDouble(fileContent);
                }

                if (isWifiConnected && ipAddress.equals(savedIPAddress)) {

                    WeightChecker weightChecker = new WeightChecker();

                    if  (weightChecker.isCloseToDetectedWeight(savedWeight)) {
                        // txtMatchStatus.setText("帰宅時かつitemがある");
                        // 通知しない

                    } else {
                        // txtMatchStatus.setText("帰宅時かつitemがない");
                        // 通知する
                        NotificationManager notificationManager = new NotificationManager(MainActivity.this);
                        notificationManager.notify("アイテムをKeyMinderの上においてください", "native");
                    }
                    // isWifiConnected && ipAddress.equals(savedIPAddress) ... 帰宅状態
                    // 重量の判定を組み合わせるといい
                } else {
                    WeightChecker weightChecker = new WeightChecker();
                    // if  (not item on keyminder) ...
                    if  (weightChecker.isCloseToDetectedWeight(savedWeight)) {
                        // txtMatchStatus.setText("外出時かつitemがある");
                        // 通知する
                        NotificationManager notificationManager = new NotificationManager(MainActivity.this);
                        notificationManager.notify("アイテムをKeyMinderの上に忘れています", "native");
                    } else {
                        // txtMatchStatus.setText("外出かつitemがない");
                        // 通知しない
                    }
                }
            }
        });
    }

    private boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo != null && wifiNetworkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isWifiConnected = isWifiConnected();
        savedIPAddress = readTextFromFile();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}


