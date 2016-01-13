package com.example.chengqi.mycoderepo.expert;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.example.chengqi.mycoderepo.R;

public class DemoSurfaceView extends Activity {

    private static final String LOG_TAG = "DemoSurfaceView";

    GameSurface mGameSurface;
    GameController mController;
    View v;
    SeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = getLayoutInflater().inflate(R.layout.surfaceviewwithbtn, null);

        setContentView(v);
        mGameSurface = (GameSurface) findViewById(R.id.view);
        mController = new GameController();
        mGameSurface.setController(mController);

        sb = (SeekBar) findViewById(R.id.seekBar5);
        sb.setMax(100);
        sb.setProgress(50);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = 1;
                scale = progress / 50f;
                Log.d(LOG_TAG, "scale=" + scale + " progress=" + progress);
                mGameSurface.setscale(scale);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        mController = new GameController();
//        mGameSurface = new GameSurface(this, mController);
//        setContentView(mGameSurface);
        mController.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController.stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(LOG_TAG, "on touch ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(LOG_TAG, "on touch ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(LOG_TAG, "on touch ACTION_MOVE");
                break;
            default:
                break;
        }
        return false;
    }
}

