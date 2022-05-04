package com.example.wguscheduler_marywilliams.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.wguscheduler_marywilliams.R;

public class MyReceiver extends BroadcastReceiver {
    static  int notificationId;
    String channelId = "WGU Scheduler";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("alertType"),Toast.LENGTH_LONG).show();
        createNotificationChannel(context,channelId);

        Notification n = new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setContentText(intent.getStringExtra("alertType"))
                .setContentTitle(intent.getStringExtra("alertTitle")).build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId++, n);


        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "WGU Scheduler Alerts";
            String description = "Alerts/Notifications for Start and End dates in the WGU Scheduler.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}