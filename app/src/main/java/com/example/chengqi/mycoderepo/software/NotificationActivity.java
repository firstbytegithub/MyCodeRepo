package com.example.chengqi.mycoderepo.software;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.chengqi.mycoderepo.R;

public class NotificationActivity extends AppCompatActivity {

    private Button mBtn83;
    private Button mBtn84;
    private Button mBtn85;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mBtn83 = (Button) findViewById(R.id.button83);
        mBtn84 = (Button) findViewById(R.id.button84);
        mBtn85 = (Button) findViewById(R.id.button85);

        mBtn83.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent intent = PendingIntent.getActivity(NotificationActivity.this, 37,
                        new Intent(NotificationActivity.this, NotificationActivity.class), 0);
                Notification notify = new Notification.Builder(NotificationActivity.this)
                        .setSmallIcon(R.drawable.bdspeech_help_light)
                        .setTicker("i'm ticker")
                        .setContentTitle("content title")
                        .setContentText("content text")
                        .setContentInfo("content info")
                        .setContentIntent(intent)
                        .setNumber(1)
                        .build();
                notify.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_SHOW_LIGHTS ;
                nm.notify(1, notify);

            }
        });

        mBtn84.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent intent = PendingIntent.getActivity(NotificationActivity.this, 37,
                        new Intent(NotificationActivity.this, NotificationActivity.class), 0);
                RemoteViews rv = new RemoteViews(getPackageName(), R.layout.mynotify);
                Notification notify = new Notification.Builder(NotificationActivity.this)
                        .setSmallIcon(R.drawable.bdspeech_help_light)
                        .setTicker("i'm ticker")
                        .setContentTitle("content title")
                        .setContentText("content text")
                        .setContentInfo("content info")
                        .setContentIntent(intent)
                        .setNumber(1)
                        .setContent(rv)
                        .build();
                nm.notify(1, notify);
            }
        });

        mBtn85.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                nm.cancel(1);
            }
        });
    }
}
