package com.example.chengqi.mycoderepo.widgets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.chengqi.mycoderepo.R;

public class SwitchActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SwitchActivity";
    private Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        sw = (Switch)findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(LOG_TAG, "clicked!");
            }
        });
    }
}
