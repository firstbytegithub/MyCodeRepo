package com.example.chengqi.mycoderepo.app;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class AppService01 extends Service {
    private final String TAG = "AppService01";
    private final IBinder mBinder = new LocalBinder();

    public static final String ACTION01 = "com.example.chengqi.mycoderepo.app.appservice01.action01";

    public class LocalBinder extends Binder {
        AppService01 getService() {
            return AppService01.this;
        }
    }

    public AppService01() {
    }

    public String getYourSecret() {
        return "i am a girl!";
    }

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "on receive");
            String action = intent.getAction();
            if (action.equals(ACTION01)) {
                Log.d(TAG, "Got action01");
            }
        }
    };

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate, registerReceiver");
        super.onCreate();

        IntentFilter commandFilter = new IntentFilter();
        commandFilter.addAction(ACTION01);
        registerReceiver(mIntentReceiver, commandFilter);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String a = intent.getAction();
        Log.d(TAG, "onStartCommand " + a);

        if (a.equals(ACTION01)) {
            NewAppWidget appWidget = NewAppWidget.getInstance();
            appWidget.notifyChange(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public String getShowText() {
        return "fill in service";
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(mIntentReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent);
    }
}
