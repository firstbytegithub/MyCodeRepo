package com.example.chengqi.mycoderepo.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chengqi.mycoderepo.R;

public class AppBroadCastReceiverActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AppBroadCastReceiver";
    private DemoBroadCastReceiver receiver = new DemoBroadCastReceiver();
    private Button btnSend;
    private Button btnSend2;
    private Button btnSendSticky;
    private Button btnSendOrdered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_broad_cast_receiver);
        btnSend = (Button)findViewById(R.id.button32);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "send broadcast!");
                Intent intent = new Intent();
                intent.putExtra("name", "chengqi");
                intent.setAction("com.example.chengqi.mycoderepo.app.bc");
                sendBroadcast(intent);
            }
        });
        btnSend2 = (Button)findViewById(R.id.button33);
        btnSend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "send broadcast bc2!");
                Intent intent = new Intent();
                intent.putExtra("name", "chengqi bc2");
                intent.setAction("com.example.chengqi.mycoderepo.app.bc2");
                sendBroadcast(intent);
            }
        });
        btnSendSticky = (Button)findViewById(R.id.button34);
        btnSendSticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "send sticky broadcast!");
                Intent intent = new Intent();
                intent.putExtra("name", "chengqi sticky");
                intent.setAction("com.example.chengqi.mycoderepo.app.bc");
                sendBroadcast(intent);
            }
        });
        btnSendOrdered = (Button)findViewById(R.id.button35);
        btnSendOrdered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "send ordered broadcast!");
                Intent intent = new Intent();
                intent.putExtra("name", "chengqi ordered");
                intent.setAction("com.example.chengqi.mycoderepo.app.bc");
//                sendBroadcast(intent);
                sendOrderedBroadcast(intent, null);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.chengqi.mycoderepo.app.bc");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(receiver);
    }
}
