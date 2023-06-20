package com.example.keyminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ItemRegistration extends AppCompatActivity {

    private EditText editText;
    private Button regist_button;
    private Button not_regist_button;
    private ScrollView scrollView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemregist);

        editText = findViewById(R.id.editText);
        regist_button = findViewById(R.id.regist_button);
        not_regist_button = findViewById(R.id.not_regist_button);

        regist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = editText.getText().toString();
                if (!fileName.isEmpty()) {
                    int randomNumber = generateRandomNumber();
                    saveToItemList(fileName, randomNumber);
                } else {
                    Toast.makeText(ItemRegistration.this, "Please enter a file name", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        not_regist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private int generateRandomNumber() {
        return (int) (Math.random() * 100) + 1;
    }

    private void saveToItemList(String fileName, int number) {
        // "items"フォルダーが存在しない場合は作成する
        File itemsFolder = new File(getFilesDir(), "items");
        if (!itemsFolder.exists()) {
            itemsFolder.mkdir();
        }

        // ファイルに数字を保存
        File itemFile = new File(itemsFolder, fileName + ".txt");
        try {
            FileWriter writer = new FileWriter(itemFile);
            writer.write(String.valueOf(number));
            writer.close();
            Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
