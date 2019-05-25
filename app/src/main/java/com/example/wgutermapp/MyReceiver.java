package com.example.wgutermapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    static int notificationID;
    String channel_id="sample";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Sample Alarm", Toast.LENGTH_LONG).show();
        createNotificationChannel(context,channel_id);

        Notification n= new NotificationCompat.Builder(context, channel_id)
             .setSmallIcon(R.drawable.ic_school_black_24dp)
             .setContentText("This is a sample alarm")
             .setContentTitle("Sample Alarm: " + notificationID).build();

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++,n);

    }
    private void createNotificationChannel(Context context, String CHANNEL_ID) {

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              CharSequence name = context.getResources().getString(R.string.channel_name);
              String description = context.getString(R.string.channel_description);
              int importance = NotificationManager.IMPORTANCE_DEFAULT;
              NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
              channel.setDescription(description);
              NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
              notificationManager.createNotificationChannel(channel);
        }
    }
}
