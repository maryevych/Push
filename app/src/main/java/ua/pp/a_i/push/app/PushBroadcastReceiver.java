package ua.pp.a_i.push.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

/**
 * Created by Yevhen on 03.04.2014.
 */
public class PushBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName comp = new ComponentName(context.getPackageName(), PushIntentService.class.getName());
        startWakefullService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }



    private void startWakefullService(Context context, Intent intent) {
        context.startService(intent);

    }
}
