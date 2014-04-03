package ua.pp.a_i.push.app;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class MainActivity extends Activity {

    GoogleCloudMessaging gcm;
    Context context;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();
        gcm=GoogleCloudMessaging.getInstance(this);
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
                String regid="";
                try{
                    regid=gcm.register("862066542340");
                    HttpGet request=new HttpGet("http://a-i.pp.ua/push/regdevice?id="+regid);
                    HttpClient client=new DefaultHttpClient();
                    HttpResponse response=client.execute(request);
                    InputStream stream=response.getEntity().getContent();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
                    String line="";
                    line=reader.readLine();
                    while (line!=null){
                        result+=line;
                        line=reader.readLine();
                    }
                }
                catch (Exception e){

                }
                return regid;
            }
        }.execute();

    }
}
