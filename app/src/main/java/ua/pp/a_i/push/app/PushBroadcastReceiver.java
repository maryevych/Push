package ua.pp.a_i.push.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Yevhen on 03.04.2014.
 */
public class PushBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        GoogleCloudMessaging gcm=GoogleCloudMessaging.getInstance(context);
        String type=gcm.getMessageType(intent);
        Bundle extras=intent.getExtras();
        try {
            Object data = extras.get("data");
           String s= data.toString();
        }
        catch (Exception e){

        }
    }
}
