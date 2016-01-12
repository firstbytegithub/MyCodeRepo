package com.example.chengqi.mycoderepo.expert;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Created by archermind on 12/23/15.
 */
public class GameSurface extends SurfaceView
        implements Callback {

    private static final String LOG_TAG = "GameSurface";
    private DrawThread mDrawThread = null;
    private GameController mController;

    public GameSurface(Context context) {
        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    public GameSurface(Context context, GameController gameController) {
        this(context);
        mController = gameController;
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    public void setController(GameController gameController) {
        mController = gameController;
    }

    class DrawThread extends Thread {

        Context mContext;
        SurfaceHolder mSurfaceHolder;
        boolean mRunning = false;
        float radius = 10f;


        public DrawThread(Context context, SurfaceHolder holder) {
            mContext = context;
            mSurfaceHolder = holder;
        }

        @Override
        public void run() {

            Log.d(LOG_TAG, "draw start");
            while (mRunning) {
//                Log.d(LOG_TAG, "1");
                try {
//                    Log.d(LOG_TAG, "2");
                    Canvas c = mSurfaceHolder.lockCanvas();
                    if (c != null) {
                        doDraw(c);

                        mSurfaceHolder.unlockCanvasAndPost(c);

                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            Log.d(LOG_TAG, "draw end");
        }

        public void doDraw(Canvas c) {
//            c.drawColor(Color.WHITE);
//            c.translate(200, 200);
//            c.drawCircle(0, 0, mController.getGameData().getRadius1(), paint1);
//            c.drawCircle(0, 0, mController.getGameData().getRadius2(), paint2);
            Paint paintRed;
            Paint paintYellow, paintBlue;

            paintRed = new Paint();
            paintRed.setColor(Color.RED);
            paintRed.setStyle(Paint.Style.FILL);
            paintRed.setStrokeWidth(2);
            paintRed.setStrokeJoin(Paint.Join.ROUND);
            paintRed.setAntiAlias(true);

            paintYellow = new Paint();
            paintYellow.setColor(Color.YELLOW);
            paintYellow.setStyle(Paint.Style.FILL);
            paintYellow.setStrokeWidth(2);

            paintBlue = new Paint();
            paintBlue.setColor(Color.BLUE);
            paintBlue.setStyle(Paint.Style.STROKE);
            paintBlue.setStrokeWidth(5);
            paintBlue.setAntiAlias(true);
            paintBlue.setDither(true);
            paintBlue.setStrokeJoin(Paint.Join.ROUND);
            paintBlue.setStrokeCap(Paint.Cap.ROUND);

            c.drawColor(Color.WHITE);
            c.drawColor(Color.argb(255, 255, 255, 255));
            c.drawRect(0, 0, 100, 100, paintYellow);
            c.drawArc(0, 0, 100, 100, 0, 120, true, paintRed);
            c.save();
            c.translate(100, 100);
            c.drawRect(0, 0, 100, 100, paintRed);
            c.drawArc(0, 0, 100, 100, 0, 120, true, paintYellow);
            c.restore();
//            c.translate(-100, -100);
            c.drawCircle(300, 100, 50, paintBlue);
            c.drawLine(400, 50, 500, 100, paintRed);
            float[] dots = {500,0,550,0,550,0,600,50,600,50,650,50};
            c.drawLines(dots, paintBlue);
            c.drawOval(new RectF(0, 200, 50, 300), paintRed);
            Path pa = new Path();
            pa.moveTo(100, 300);
            pa.lineTo(100, 350);
            pa.lineTo(200, 400);
            pa.lineTo(100, 300);
            c.drawPath(pa, paintYellow);
            paintRed.setStrokeWidth(10);
            c.drawPoint(100, 500, paintRed);
            paintYellow.setStrokeWidth(2);
            float[] pts = {120, 500, 130, 550, 140, 600};
            c.drawPoints(pts, paintYellow);
            c.drawRoundRect(300, 200, 500, 300, 30, 30, paintRed);
            c.drawRoundRect(300, 200, 500, 300, 30, 70, paintBlue);
            c.drawRoundRect(300, 200, 500, 300, 70, 70, paintYellow);
            paintRed.setStrokeWidth(2);
            c.drawText("helloworld", 300, 600, paintRed);
//            Log.d("xxx", "text size = " + paintBlue.getTextSize());
            paintBlue.setTextSize(36);
            paintYellow.setTextSize(40);
            c.drawTextOnPath("iloveyou", pa, 0, 0, paintBlue);
            c.translate(c.getWidth() / 2, c.getHeight() / 2);
            c.drawCircle(0, 0, 300, paintRed);
            String time[] = {"12","1","2","3","4","5","6","7","8","9","10","11"};
            for (int i = 0; i < 12; i++) {
                c.drawText(time[i], -20, -250, paintYellow);
                c.rotate(30);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(LOG_TAG, "surfaceCreated");
        mDrawThread = new DrawThread(null, holder);
        mDrawThread.mRunning = true;
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(LOG_TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(LOG_TAG, "surfaceDestroyed");
        mDrawThread.mRunning = false;
        try {
            mDrawThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mDrawThread.interrupt();
        mDrawThread = null;
    }
}
