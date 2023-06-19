package com.example.keyminder;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.keyminder.line.LineNotificationTask;
import com.example.keyminder.line.VerifyTokenTask;

import java.util.concurrent.ExecutionException;

public class NotificationManager {
    private Activity parentActivity;
    NativeNotificationTask nativeNotificationTask;
    LineNotificationTask lineNotificationTask;

    VerifyTokenTask verifyTokenTask;
    SharedPreferences pref;

    public NotificationManager(Activity parentActivity) {
        this.parentActivity = parentActivity;
        nativeNotificationTask = new NativeNotificationTask(this.parentActivity);
        lineNotificationTask = new LineNotificationTask();
        verifyTokenTask = new VerifyTokenTask();
        pref = this.parentActivity.getSharedPreferences("prefs", MODE_PRIVATE);
    }

    public void Notify(String... params) {

        String mode = null;
        String message = params[0];

        if (params.length > 1) {
            mode = params[1];
        }

        if (mode == "native") {
            NotifyWithNativeNotification(message);
        } else {
            String access_token = pref.getString("access_token", "");
            String userId = pref.getString("userId", "");

            Log.d("saved access_token", access_token);
            Log.d("saved userId", userId);

            String token_state = "";
            try {
                token_state = verifyTokenTask.execute(access_token).get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (token_state == "valid") {
                NotifyWithLineNotification(userId, message);
            } else  {
                NotifyWithNativeNotification(message);
            }

        }
    }

    private void NotifyWithNativeNotification(String message) {
        String channelId = "12345";
        String title = "KeyMinder";
        nativeNotificationTask.sendNotification(channelId, title, message);
    }

    private void NotifyWithLineNotification(String userId, String message) {
        lineNotificationTask.execute(userId, message);
    }



}
