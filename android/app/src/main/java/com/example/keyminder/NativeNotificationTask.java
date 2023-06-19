package com.example.keyminder;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import java.io.IOException;
import java.net.URL;

public class NativeNotificationTask {

    private Activity parentActivity;

    public NativeNotificationTask(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    private void createNotificationChannel(String channelId) {
        // APIレベルが26以上だと必要らしい
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, "チャンネル名", importance);

            channel.setDescription("チャンネルの説明");

            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);

            NotificationManager notificationManager = (NotificationManager) parentActivity.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(String channelId, String title, String message) {
        createNotificationChannel(channelId);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // initialize Uri here
        NotificationCompat.Builder builder = new NotificationCompat.Builder(parentActivity, "CHANNEL_ID")
                .setSmallIcon(com.google.android.material.R.drawable.notification_icon_background)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(uri)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager
                = NotificationManagerCompat.from(parentActivity);

        if (ActivityCompat.checkSelfPermission(parentActivity, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(R.string.app_name, builder.build());
    }

}
