package ua.pp.a_i.push.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

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
            JSONObject data = new JSONObject(extras.getString("data"));
           String message=data.getString("message");
            Bundle bundle=new Bundle();
            bundle.putString("message",message);
            context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), bundle);

        }
        catch (Exception e){
            String ex=e.getMessage();

        }
    }
}
