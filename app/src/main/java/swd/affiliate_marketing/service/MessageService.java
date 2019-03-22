package swd.affiliate_marketing.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import swd.affiliate_marketing.MainActivity;
import swd.affiliate_marketing.R;

public class MessageService extends FirebaseMessagingService {
    public MessageService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("Affiliate Marketing", "Firebase message: received message from "+remoteMessage.getFrom());
        String from = remoteMessage.getFrom();
        Log.d("from", from);
        if(remoteMessage.getFrom().contains("promotionCodeTracking")){
            // Check if message contains a data payload.
            super.onMessageReceived(remoteMessage);
            if (remoteMessage.getData().size() > 0) {
                showPromotionCodeTrackingNotification(remoteMessage.getData().get("promotionCode"), remoteMessage.getData().get("timeOfUsing"));
            }

            // Check if message contains a notification payload.
        } else {
            if (remoteMessage.getData().size() > 0) {

                if(remoteMessage.getNotification() != null){
                    String click_action = remoteMessage.getNotification().getClickAction();
                    Intent intent = new Intent(click_action);
                    intent.putExtra("campaignID", (remoteMessage.getData().get("ID")));
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
                    notificationBuilder.setContentIntent(pendingIntent);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notificationBuilder.build());

//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("campaignID", (remoteMessage.getData().get("ID")));
//            startActivity(intent);
                }
            }
        }
        if (remoteMessage.getNotification() != null) {
            Log.d("Message: ", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    private void showPromotionCodeTrackingNotification(String promotionCode, String time) {
        String message = "Your promotion code: " + promotionCode+" is used\n"+"At " + time;
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("New promotion code tracking")
                .setSmallIcon(R.drawable.digital_campaign)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
