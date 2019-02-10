package org.kidzonshock.acase.acase.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.kidzonshock.acase.acase.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private final int NOTIFICATION_ID = 345;
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG,"relationID" + remoteMessage.getData().get("relation_id"));

        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String msgBody = remoteMessage.getNotification().getBody();
            String client_id = remoteMessage.getData().get("client_id");
            String relation_id = remoteMessage.getData().get("relation_id");
            preAppointNotification(msgBody,client_id,relation_id);
        }

    }
    // [END receive_message]

    private void preAppointNotification(String messageBody, String client_id, String relation_id) {
        Intent acceptIntent = new Intent(this, MyService.class);
        Intent rejectIntent = new Intent(this, MyService.class);
        //accept
        acceptIntent.putExtra("client_id",client_id);
        acceptIntent.putExtra("relation_id",relation_id);
        //reject
        rejectIntent.putExtra("client_id",client_id);
        rejectIntent.putExtra("relation_id",relation_id);

        acceptIntent.setAction(MyService.Accept);
        rejectIntent.setAction(MyService.Reject);
        PendingIntent acceptPendingIntent = PendingIntent.getService(this, 0 , acceptIntent,PendingIntent.FLAG_ONE_SHOT);
        PendingIntent rejectPendingIntent = PendingIntent.getService(this, 0 , rejectIntent,PendingIntent.FLAG_ONE_SHOT);

        String channelId = "MyChannel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Pre-Appointment")
                        .setContentText(messageBody)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setWhen(0)
                        .addAction(R.drawable.outline_check_black_18dp,"Accept",acceptPendingIntent)
                        .addAction(R.drawable.outline_close_black_18dp,"Reject",rejectPendingIntent)
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setSound(defaultSoundUri);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
