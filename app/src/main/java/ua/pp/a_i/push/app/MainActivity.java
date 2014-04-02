package ua.pp.a_i.push.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class MainActivity extends Activity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    static final String TAG = "GCMDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkPlayServices()){

        }
    }

    private boolean checkPlayServices() {
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
}
