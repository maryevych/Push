package ua.pp.a_i.push.app;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Yevhen on 03.04.2014.
 */
public class PushIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager notificationManager;
    NotificationCompat.Builder builder;

    public PushIntentService() {
        super("PushIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras=intent.getExtras();
        GoogleCloudMessaging gcm=GoogleCloudMessaging.getInstance(this);
        String messageType=gcm.getMessageType(intent);
        if(!extras.isEmpty()){
            if(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)){
                sendNotification("Send error: "+extras.toString());
            }
            else if(GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)){
                sendNotification("Deleted messages on server: "+extras.toString());
            }
            else if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)){
                sendNotification("Received: "+extras.toString());
            }
        }

    }

    private void sendNotification(String msg) {
        notificationManager=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent=PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this).setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Notification").setStyle(new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg);
        builder.setContentIntent(contentIntent);
        notificationManager.notify(NOTIFICATION_ID,builder.build());

    }
}
