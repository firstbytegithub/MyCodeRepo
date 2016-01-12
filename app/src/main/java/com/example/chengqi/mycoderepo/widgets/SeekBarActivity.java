package com.example.chengqi.mycoderepo.widgets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.chengqi.mycoderepo.R;

public class SeekBarActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SeekBarActivity";
    private SeekBar sb;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        sb = (SeekBar)findViewById(R.id.seekBar);
        btn = (Button)findViewById(R.id.button76);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.setProgress(90);
            }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(LOG_TAG, "change " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(LOG_TAG, "s +");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(LOG_TAG, "s +");
            }
        });
    }
}
