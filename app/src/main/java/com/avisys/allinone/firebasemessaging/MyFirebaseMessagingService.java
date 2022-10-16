package com.avisys.allinone.firebasemessaging;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.avisys.allinone.R;
import com.avisys.allinone.SplashScreenActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    /**
     * Need to override the @method onMessageReceived to get title and body of
     * the message passed in FCM
     * */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification()!=null)
            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    private void showNotification(String title,String message){
        Intent intent = new Intent(this, SplashScreenActivity.class);
        // Need to create channel id
        String channel_id = "Channel-Id";
        /** Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
         * the activities present in the activity stack,
         * on the top of the Activity that is to be launched
         * channelId – The constructed Notification will be posted on this NotificationChannel.
         */
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        /**
         *  Pass the intent to PendingIntent
         *  to start the next Activity
         */
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        /**
         * Create a Builder object using NotificationCompat
         * class. This will allow control over all the flags
         */
        NotificationCompat.Builder builder
                = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.marker_image)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000,
                        1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        /**
         * A customized design for the notification can be
         * set only for Android versions 4.1 and above. Thus
         * condition for the same is checked here.
         * @Link JellyBean::16 API LEVEL
         * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            builder = builder.setContent(getCustomDesign(title, message));
         // If Android Version is lower than Jelly Beans,
        // customized layout cannot be used and thus the
        // layout is set as follows
        else
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.marker_image);

        /**
         * Create an object of NotificationManager class
         * to notify the user of events
         * that happen in the background.
         * */
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {
            /**
             * Create Class NotificationChannel which accept 3ree parameters
             * @param channelID,@param appName(Option name),@param Priority
             * */
            NotificationChannel notificationChannel
                    = new NotificationChannel(
                    channel_id, "android_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }

        notificationManager.notify(0, builder.build());

    }

    // Method to get the custom Design for the display of
    // notification.
    private RemoteViews getCustomDesign(String title,
                                        String message) {
        Log.e("Remote","getCustomDesign");
        @SuppressLint("RemoteViewLayout") RemoteViews remoteViews = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.notification);
        remoteViews.setTextViewText(R.id.title1, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.icon,
                R.drawable.marker_image);
        return remoteViews;
    }
}
