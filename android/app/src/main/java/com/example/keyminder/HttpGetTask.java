package com.example.keyminder;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetTask {
    private HttpURLConnection httpConn = null;

    protected String execute(String... strings) {

        Log.d("AsyncGetTask", "AsyncGetTask Called");

        String requestURL = strings[0];
        String contentType = strings[1];
        OutputStream outputStream;
        InputStream inputStream;

        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(requestURL);

            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setConnectTimeout(100000);
            httpConn.setReadTimeout(100000);
            httpConn.addRequestProperty("User-Agent", "Android");
            httpConn.addRequestProperty("Content-Type", contentType);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            // HTTPレスポンスコード
            final int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // 通信に成功した
                // テキストを取得する
                final InputStream in = httpConn.getInputStream();
                String encoding = httpConn.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                // 1行ずつテキストを読み込む
                while ((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            } else {
                // 通信が失敗した場合のレスポンスコードを表示
                System.out.println(status);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (httpConn != null) {
                // コネクションを切断
                httpConn.disconnect();
            }
        }
        Log.d("Debug", result.toString());
        return result.toString();
    }

}
