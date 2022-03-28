package com.example.prueba;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.util.Log;



public class FirebaseMessagingServ extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM
    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage) {
        // First case when notifications are received via data event
        // Here, 'title' and 'message' are the assumed names of JSON attributes.
        // Since here we do not have any data payload, This section is commented out. It is here only for reference purposes.

        /*if(remoteMessage.getData().size()>0){
            showNotification(remoteMessage.getData().get("title"),
                          remoteMessage.getData().get("message"));
        }*/

        // Second case when notification payload is received.
        if (remoteMessage.getNotification() != null) {
            // Since the notification is received directly from FCM, the title and the body can be fetched directly as below.
            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    // [START on_new_token]
    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any
     * server-side account maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    //Get the custom Design for notifications.
    private RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        return remoteViews;
    }

    // Method to display the notifications
    public void showNotification(String title, String message) {

        // Pass the intent to switch to the MainActivity
        Intent intent = new Intent(this, MainActivity.class);

        // Assign channel ID
        String channel_id = "notification_channel";

        //Clear activity from the bar
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Create a Builder object using NotificationCompat class. This will allow control over all the flags
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(getApplicationContext(), channel_id)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        // Custom design for the versions that support it, 4.1 and above. Default design for versions that cannot use it
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder = builder.setContent(getCustomDesign(title, message));
        }
        else {
            builder = builder.setContentTitle(title).setContentText(message);
        }

        // Create an object of NotificationManager class to notify the user of events that happen in the background.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        notificationManager.notify(0, builder.build());
    }
}


