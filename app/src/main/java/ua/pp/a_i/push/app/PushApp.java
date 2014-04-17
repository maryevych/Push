package ua.pp.a_i.push.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by em on 11.04.2014.
 */
public class PushApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
