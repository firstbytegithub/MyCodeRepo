package com.example.chengqi.mycoderepo.software;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.chengqi.mycoderepo.expert.GameController;

/**
 * Created by archermind on 12/23/15.
 */
public class BenchMarkSurface extends SurfaceView
        implements Callback {

    private static final String TAG = "BenchMarkSurface";

    private DrawThread mDrawThread = null;

    private Paint mPaintRed = null;
    private Paint mPaintGreen = null;

    private static final int REPEAT_NUM = 10;

    private int mCurRepeat = 0;

    private long[] mTimeFillPoints = new long[REPEAT_NUM];

    private long[] mTimeFillLines = new long[REPEAT_NUM];

    public BenchMarkSurface(Context context) {
        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    public BenchMarkSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    class DrawThread extends Thread {

        Context mContext;
        SurfaceHolder mSurfaceHolder;
        boolean mRunning = false;

        public DrawThread(Context context, SurfaceHolder holder) {
            mContext = context;
            mSurfaceHolder = holder;

            mPaintRed = new Paint();
            mPaintRed.setColor(Color.RED);
            mPaintRed.setTextSize(25);

            mPaintGreen = new Paint();
            mPaintGreen.setColor(Color.GREEN);
            mPaintGreen.setTextSize(25);
        }

        @Override
        public void run() {
            Log.d(TAG, "benchmark start");
            while (mRunning) {
                if (mCurRepeat < REPEAT_NUM) {
                    try {
                        Canvas c = mSurfaceHolder.lockCanvas();
                        if (c != null) {
                            benchMark(c);
                            mSurfaceHolder.unlockCanvasAndPost(c);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                    mCurRepeat++;
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Canvas c = mSurfaceHolder.lockCanvas();
                    if (c != null) {
                        printResult(c);
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                    break;
                }
            }
            Log.d(TAG, "benchmark end");
        }

        private void benchMark(Canvas canvas) {
            long t1 = 0;

            canvas.drawColor(Color.WHITE);
            t1 = getTime();
            fillPoints(canvas);
            mTimeFillPoints[mCurRepeat] = getTime() - t1;
            Log.d(TAG, "Time to fill points=" + mTimeFillPoints[mCurRepeat] + "ms");
            canvas.drawText("fill points end", 100, 100, mPaintGreen);

            canvas.drawColor(Color.WHITE);
            t1 = getTime();
            fillLines(canvas);
            mTimeFillLines[mCurRepeat] = getTime() - t1;
            Log.d(TAG, "Time to fill lines=" + mTimeFillLines[mCurRepeat] + "ms");
            canvas.drawText("fill lines end", 100, 100, mPaintRed);
        }

        private void printResult(Canvas canvas) {
            canvas.drawColor(Color.WHITE);

            int x = 10;
            int y = 100;
            for (int i = 0; i < REPEAT_NUM; i++) {
                canvas.drawText("Time to fill points[" + i + "]=" + mTimeFillPoints[i], x, y+i*50, mPaintRed);
            }

            y = 600;
            for (int i = 0; i < REPEAT_NUM; i++) {
                canvas.drawText("Time to fill lines[" + i + "]=" + mTimeFillLines[i], x, y+i*50, mPaintGreen);
            }
        }

        private void fillPoints(Canvas canvas) {
            int w = canvas.getWidth();
            int h = canvas.getHeight();

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    canvas.drawPoint(i, j, mPaintRed);
                }
            }
        }

        private void fillLines(Canvas canvas) {
            int w = canvas.getWidth();
            int h = canvas.getHeight();

            for (int i = 0; i < h; i++) {
                canvas.drawLine(0, i, w, i, mPaintGreen);
                canvas.drawLine(0, i, w, i, mPaintGreen);
                canvas.drawLine(0, i, w, i, mPaintGreen);
                canvas.drawLine(0, i, w, i, mPaintGreen);
                canvas.drawLine(0, i, w, i, mPaintGreen);
                canvas.drawLine(0, i, w, i, mPaintGreen);
                canvas.drawLine(0, i, w, i, mPaintGreen);
                canvas.drawLine(0, i, w, i, mPaintGreen);
                canvas.drawLine(0, i, w, i, mPaintGreen);
                canvas.drawLine(0, i, w, i, mPaintGreen);
            }
        }

        private long getTime() {
            return SystemClock.elapsedRealtime();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");

        mDrawThread = new DrawThread(null, holder);
        mDrawThread.mRunning = true;
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        mDrawThread.mRunning = false;
//        try {
//            mDrawThread.join();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mDrawThread = null;
    }
}
