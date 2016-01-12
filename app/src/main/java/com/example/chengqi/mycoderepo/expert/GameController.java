package com.example.chengqi.mycoderepo.expert;

import android.util.Log;

/**
 * Created by archermind on 12/23/15.
 */
public class GameController {
    private static final String LOG_TAG = "GameController";
    private GameData mData = null;
    private int speed = 50; // ms
    private GameThread mThread = null;

    public GameController() {
        mData = new GameData();
    }

    public int start() {
        Log.d(LOG_TAG, "start GameThread");
        mThread = new GameThread();
        mThread.start();
        return 0;
    }

    public int pause() {
        return 0;
    }

    public int resume() {
        return 0;
    }

    public int stop() {
        Log.d(LOG_TAG, "stop GameThread +");
        mThread.bExit = true;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "stop GameThread -");
        mThread = null;
        return 0;
    }

    public GameData getGameData() {
        return mData;
    }

    class GameThread extends Thread {
        private boolean bExit = false;

        public GameThread() {

        }

        @Override
        public void run() {
            while (!bExit) {
                mData.setRadius1(mData.getRadius1() + mData.getIncre1());
                mData.setRadius2(mData.getRadius2() + mData.getIncre2());
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "break");
                    break;
                }
            }
        }
    }
}
