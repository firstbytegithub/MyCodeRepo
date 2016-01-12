package com.example.chengqi.mycoderepo.expert;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.chengqi.mycoderepo.R;

public class DemoSurfaceView extends Activity {

    GameSurface mGameSurface;
    GameController mController;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = getLayoutInflater().inflate(R.layout.surfaceviewwithbtn, null);

        setContentView(v);
        mGameSurface = (GameSurface) findViewById(R.id.view);
        mController = new GameController();
        mGameSurface.setController(mController);

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
}
