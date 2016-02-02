package com.example.chengqi.mycoderepo.expert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.chengqi.mycoderepo.R;

public class NdkActivity extends AppCompatActivity {

    private static final String TAG = "NdkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);

//        Log.d(TAG, "3+4=" + add(3, 4));
//        Log.d(TAG, "7-3=" + sub(7, 3));
    }

//    private native int add(int a, int b);
//
//    private native int sub(int a, int b);
//
//    static {
//        System.loadLibrary("add");
//    }
}
