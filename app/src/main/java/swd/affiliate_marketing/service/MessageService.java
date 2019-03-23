package swd.affiliate_marketing.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import swd.affiliate_marketing.MainActivity;
import swd.affiliate_marketing.R;
import swd.affiliate_marketing.global.GlobalVariable;
import swd.affiliate_marketing.model.Campaign;
import swd.affiliate_marketing.model.Notification;

public class MessageService extends FirebaseMessagingService {
    private NotificationManager notifManager;
    private String content;
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
        } else {
            showNewCampaignNotification(remoteMessage);
        }
        if (remoteMessage.getNotification() != null) {
            Log.d("Message: ", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    private void showPromotionCodeTrackingNotification(String promotionCode, String time) {
        String date = time.substring(0,10);
        String hour = time.substring(11,19);
        time = hour+" "+date;
        String message = "Promotion code: " + promotionCode+" is used\n"+"At " + time;


        Intent intent = new Intent(this, MainActivity.class);

        String timeStamp = new SimpleDateFormat("HH:mm dd-MM-yyyy").format(Calendar.getInstance().getTime());
        createNotifyPromotionCodeContent(promotionCode, time);
        swd.affiliate_marketing.model.Notification notification = new Notification(timeStamp, "New promotion code tracking", content );
        intent.putExtra("promotionCodeNotify", notification);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        if (notifManager == null) {
            notifManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        final int NOTIFY_ID = 0; // ID of notification
        String id = getApplicationContext().getString(R.string.default_notification_channel_id); // default_channel_id
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, "promotionCode", importance);
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
            notificationBuilder = new NotificationCompat.Builder(this, id)
                    .setContentTitle("Promotion code used")
                    .setSmallIcon(R.drawable.digital_campaign)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setChannelId(id);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this, id)
                    .setContentTitle("Promotion code used")
                    .setSmallIcon(R.drawable.digital_campaign)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        }

        notifManager.notify(NOTIFY_ID /* ID of notification */, notificationBuilder.build());
    }

    private void showNewCampaignNotification(RemoteMessage remoteMessage){
        if (notifManager == null) {
            notifManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (remoteMessage.getData().size() > 0) {

            final int NOTIFY_ID = 0; // ID of notification
            String id = getApplicationContext().getString(R.string.default_notification_channel_id); // default_channel_id
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, "promotionCode", importance);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }
                if(remoteMessage.getNotification() != null){
                    String click_action = remoteMessage.getNotification().getClickAction();
                    Intent intent = new Intent(click_action);
                    intent.putExtra("campaignID", (remoteMessage.getData().get("ID")));
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                    notificationBuilder = new NotificationCompat.Builder(this, id)
                            .setContentTitle("New campaign")
                            .setChannelId(id)
                            .setContentText("New campaign with id: "+ remoteMessage.getData().get("ID"))
                            .setSmallIcon(R.drawable.digital_campaign)
                            .setSound(defaultSoundUri)
                            .setAutoCancel(true);
                    notificationBuilder.setContentIntent(pendingIntent);
//                    Intent intent = new Intent(this, MainActivity.class);
//                    intent.putExtra("campaignID", (remoteMessage.getData().get("ID")));
//                    startActivity(intent);
                }
            } else {
                if(remoteMessage.getNotification() != null){
                    String click_action = remoteMessage.getNotification().getClickAction();
                    Intent intent = new Intent(click_action);
                    intent.putExtra("campaignID", (remoteMessage.getData().get("ID")));
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                    notificationBuilder = new NotificationCompat.Builder(this, id)
                            .setContentTitle("New campaign")
                            .setChannelId(id)
                            .setContentText("New campaign with id: "+ remoteMessage.getData().get("ID"))
                            .setSmallIcon(R.drawable.digital_campaign)
                            .setSound(defaultSoundUri)
                            .setAutoCancel(true);
                    notificationBuilder.setContentIntent(pendingIntent);

//                    Intent intent = new Intent(this, MainActivity.class);
//                    intent.putExtra("campaignID", (remoteMessage.getData().get("ID")));
//                    startActivity(intent);
                }
            }
            notifManager.notify(NOTIFY_ID, notificationBuilder.build());

        }
    }

    private void createNotifyPromotionCodeContent(final String promotionCode, final String time){
        content = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type campaignType = Types.newParameterizedType(Campaign.class);
        final JsonAdapter<Campaign> jsonAdapter = moshi.adapter(campaignType);

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/CampaignRegistrations/"+promotionCode+"/Campaign";
        Request request = new Request.Builder().url(url).build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Get data API Error: ", e.getMessage());
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                final Campaign campaign = jsonAdapter.fromJson(json);
                if(campaign != null){
                    content = "Your promotion code "+promotionCode +
                            " on register on campaign \""+campaign.campaignName+"\" of advertiser "+
                            ((GlobalVariable) getApplication()).getAdvertiserName(campaign.advertiserID)
                            + " is used at "+time+"\n"
                            + "Please check the Campaign detail to see more";
                }
                countDownLatch.countDown();
            }
        });
        try{
            countDownLatch.await();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
