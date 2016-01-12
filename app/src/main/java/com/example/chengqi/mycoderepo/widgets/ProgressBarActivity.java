package com.example.chengqi.mycoderepo.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.chengqi.mycoderepo.R;

public class ProgressBarActivity extends Activity {
    private static final String LOG_TAG = "ProgressBarActivity";
    private Button btn;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_progress_bar);
        setProgressBarIndeterminateVisibility(true);
        btn = (Button) findViewById(R.id.button75);
        pb = (ProgressBar) findViewById(R.id.progressBar4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setProgress(60);
                pb.setSecondaryProgress(90);
            }
        });
    }
}
