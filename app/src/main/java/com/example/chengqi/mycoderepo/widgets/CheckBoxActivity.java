package com.example.chengqi.mycoderepo.widgets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.chengqi.mycoderepo.R;

public class CheckBoxActivity extends AppCompatActivity {
    private static final String LOG_TAG = "CheckBoxActivity";
    private CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        cb = (CheckBox)findViewById(R.id.checkBox2);
        cb.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(LOG_TAG, "clicked!");
            }
        });

    }
}
