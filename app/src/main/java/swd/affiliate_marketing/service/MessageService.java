package swd.affiliate_marketing.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import swd.affiliate_marketing.MainActivity;

public class MessageService extends FirebaseMessagingService {


    public MessageService() {

    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {

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

        if (remoteMessage.getNotification() != null) {
            Log.d("Message: ", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }




}
