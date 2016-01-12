package com.example.chengqi.mycoderepo.app;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chengqi.mycoderepo.R;

public class DemoMultiTask extends Activity {

    private static final String TAG = "DemoMultiTask";

    private Button mButtonSendMsg = null;

    private Button mButtonSendRunnable = null;

    private Button mButtonSendThread = null;

    public Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 12:
                    pid();
                    Log.d(TAG, "handle message 12");
            }
            super.handleMessage(msg);
        }
    };

    class MyLooperThread extends Thread {
        public Handler mHandler;
        public boolean bExit = false;
        private Looper mLooper = null;

        public void exit() {
            if (mLooper != null) {
                mLooper.quit();
            }
        }

        public void run() {
            Looper.prepare();
            mLooper = Looper.myLooper();
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    // process incoming messages here
                    switch (msg.what) {
                        case 77:
                            pid();
                            Log.d(TAG, "handle message in looper thread");
                            break;
                    }
                }
            };

            Looper.loop();
            Log.d(TAG, "mylooperthread exit");
        }
    }

    MyLooperThread mLooperThread = new MyLooperThread();
    MyThread mThread = new MyThread();

    class MyThread extends Thread {
        public boolean bExit = false;
        @Override
        public void run() {
            while (!bExit) {
                pid();
                Log.d(TAG, "run in thread");
                pid();
                Log.d(TAG, "send message in thread");
//                mHandler.sendEmptyMessage(12);
                mLooperThread.mHandler.sendEmptyMessage(77);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "my thread exit");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_multi_task);
        mButtonSendMsg = (Button) findViewById(R.id.button90);
        mButtonSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pid();
                Log.d(TAG, "send message 12");
                mHandler.sendEmptyMessage(12);
            }
        });

        mButtonSendRunnable = (Button) findViewById(R.id.button91);
        mButtonSendRunnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pid();
                Log.d(TAG, "send runnable");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pid();
                        Log.d(TAG, "run in runnable");
                    }
                });
            }
        });

        mButtonSendThread = (Button) findViewById(R.id.button92);
        mButtonSendThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThread.start();
                mLooperThread.start();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        mThread.bExit = true;
        mLooperThread.exit();
    }

    private void pid() {
        String name = Thread.currentThread().getName();
        String id = String.valueOf(Thread.currentThread().getId());
        Log.d(TAG, "Thread:" + name + " " + id);
    }
}
