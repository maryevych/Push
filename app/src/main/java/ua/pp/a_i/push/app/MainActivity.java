package ua.pp.a_i.push.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends Activity {

    private static final String PROPERTY_REG_ID="registration_id";
    private static final String PROPERTY_APP_VERSION="0.0.1 dev";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST=9000;
    String SENDER_ID="862066542340";
    private final static String TAG="PushApp";

    GoogleCloudMessaging gcm;
    AtomicInteger msgId=new AtomicInteger();
    SharedPreferences prefs;
    Context context;
    String regId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regId = getRegistrationId(context);
            if (regId.isEmpty()) {
                registerInBackground();
            }

        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    private boolean checkPlayServices() {
        int resultCode=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode!=ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else{
                Log.i(TAG,"This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        final  SharedPreferences prefs=getGCMPreferences(context);
        String registrationId=prefs.getString(PROPERTY_REG_ID,"");
        if(registrationId.isEmpty()){
            Log.i(TAG,"Registration not found.");
            return "";
        }
        int registeredVersion=prefs.getInt(PROPERTY_APP_VERSION,Integer.MIN_VALUE);
        int currentVersion=getAppVersion(context);
        if(registeredVersion!=currentVersion){
            Log.i(TAG,"App version changed.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e){
            throw new RuntimeException("Could not et package name: "+e);
        }
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(MainActivity.class.getSimpleName(),Context.MODE_PRIVATE);
    }

    private void registerInBackground() {
        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... params) {
                String msg="";
                try{
                    if(gcm==null){
                        gcm=GoogleCloudMessaging.getInstance(context);
                    }
                    regId=gcm.register(SENDER_ID);
                    msg="Device registered. Registration ID="+regId;
                    sendRegistrationIdToBackend();
                    storeRegistrationId(context,regId);
                }
                catch (IOException e){
                    msg="Error:"+e.getMessage();
                }
                return msg;
            }
        }.execute();
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs=getGCMPreferences(context);
        int appVersion=getAppVersion(context);
        Log.i(TAG,"Saving regId on app version "+appVersion);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(PROPERTY_REG_ID,regId);
        editor.putInt(PROPERTY_APP_VERSION,appVersion);
        editor.commit();
    }

    private void sendRegistrationIdToBackend() {

    }

}
