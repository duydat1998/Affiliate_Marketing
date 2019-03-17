package swd.affiliate_marketing.service;

import android.content.Intent;
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
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("campaignID", (remoteMessage.getData().get("ID")));
            startActivity(intent);
        }

        if (remoteMessage.getNotification() != null) {
            Log.d("Message: ", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }




}
