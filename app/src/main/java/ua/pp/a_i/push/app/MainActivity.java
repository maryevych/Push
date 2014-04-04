package ua.pp.a_i.push.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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


public class MainActivity extends Activity {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    static final String TAG="Push";
    GoogleCloudMessaging gcm;
    Context context;
    static String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkPlayServices()){
            Log.i("wsdfgs","rfgw");
        }


        if(regid==null){
            context = getApplicationContext();
            gcm = GoogleCloudMessaging.getInstance(this);
            new AsyncTask<Void,Void,Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        String model=Build.MODEL;
                        String user=Build.USER;
                        regid = gcm.register("862066542340");
                        HttpGet request = new HttpGet("http://a-i.pp.ua/push/regdevice?id=" + regid+"&model="+model+"&user="+user);
                        HttpClient client = new DefaultHttpClient();
                        client.execute(request);
                    } catch (Exception e) {

                    }
                    return null;
                }
            }.execute();
        }
    }

    private Boolean checkPlayServices() {
        int resultCode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode!= ConnectionResult.SUCCESS){
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("regid",regid);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        regid=savedInstanceState.getString("regid");
    }
}
