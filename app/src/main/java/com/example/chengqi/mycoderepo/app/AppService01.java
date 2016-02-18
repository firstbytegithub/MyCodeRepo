package com.example.chengqi.mycoderepo.app;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class AppService01 extends Service {
    private final String TAG = "AppService01";
    private final IBinder mBinder = new LocalBinder();

    public static final String ACTION01 = "com.example.chengqi.mycoderepo.app.appservice01.action01";

    private String docPath;

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

    private void savetofile(String s) {
        try {
            boolean append = true;
            FileOutputStream os = new FileOutputStream(new File(docPath), append);
            os.write(s.getBytes());
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate, registerReceiver");
        super.onCreate();

        IntentFilter commandFilter = new IntentFilter();
        commandFilter.addAction(ACTION01);
        registerReceiver(mIntentReceiver, commandFilter);

        File f6 = Environment.getExternalStorageDirectory();
        String path = f6.getPath();
        String folder = path + "/english-cut";
        File ffolder = new File(folder);
        if (!ffolder.exists()) {
            boolean dirCreated = ffolder.mkdir();
            if (!dirCreated) {
                Log.e(TAG, "create " + folder + " failed");
                return;
            }
        }

        File clipFile = new File(folder + "/english.txt");
        if (!clipFile.exists()) {
            try {
                boolean fileCreated = clipFile.createNewFile();
                if (!fileCreated) {
                    Log.d(TAG, "create " + clipFile.getPath() + " failed");
                    return;
                }
                docPath = clipFile.getPath();
                savetofile("english from cut\n\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "create " + docPath + "ok");

        final ClipboardManager cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cb.setPrimaryClip(ClipData.newPlainText("", ""));
        cb.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {

            @Override
            public void onPrimaryClipChanged() {
                ClipData cd = cb.getPrimaryClip();
                Log.d(TAG, "clipdata=" + cd.toString());
                int N = cd.getItemCount();
                for (int i = 0; i < N; i++) {
                    ClipData.Item item = cd.getItemAt(i);
                    String s = String.valueOf(item.getText()) + "\n\n";
                    Log.d(TAG, "s=" + s);
                    savetofile(s);
                }
            }
        });
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String a = intent.getAction();
        Log.d(TAG, "onStartCommand " + this);

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
