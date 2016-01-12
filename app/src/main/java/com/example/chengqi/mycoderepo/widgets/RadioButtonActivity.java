package com.example.chengqi.mycoderepo.widgets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.example.chengqi.mycoderepo.R;

public class RadioButtonActivity extends AppCompatActivity {
    private static final String LOG_TAG = "RadioButtonActivity";
    private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button);

        rg = (RadioGroup)findViewById(R.id.radiogroup1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton2) {
                    Log.d(LOG_TAG, "radiobutton2 clicked");
                } else if (checkedId == R.id.radioButton3) {
                    Log.d(LOG_TAG, "radiobutton3 clicked");
                } else if (checkedId == R.id.radioButton4) {
                    Log.d(LOG_TAG, "radiobutton4 clicked");
                }
            }
        });
    }
}
