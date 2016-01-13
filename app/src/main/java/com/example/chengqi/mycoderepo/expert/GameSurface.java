package com.example.chengqi.mycoderepo.expert;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;

/**
 * Created by archermind on 12/23/15.
 */
public class GameSurface extends SurfaceView
        implements Callback {

    private static final String LOG_TAG = "GameSurface";
    private DrawThread mDrawThread = null;
    private GameController mController;
    private static Object lock = new Object();

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

    private float mSxBK = 0;
    private float mSyBK = 0;

    private float mSx = 0;
    private float mSy = 0;

    private float mDownX;
    private float mDownY;

    private float mMoveX;
    private float mMoveY;

    Bitmap mBkBitmap = null;
    int minx = 0;
    int miny = 0;
    int maxx = 0;
    int maxy = 0;

    boolean mDrawInThread = false;

    SurfaceHolder mSurfaceHolder2;

    private float mScale = 1.0f;

    private TextView mTextTip = null;

    public void setscale(float scale) {
        if (scale < 0.5f) {
            return;
        }
        mScale = scale;
        generateBitmap(mScale);
        if (!mDrawInThread) {
            syncDraw();
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(LOG_TAG, "on touch ACTION_DOWN " + "x=" + event.getX() + "y=" + event.getY());
                synchronized (lock) {
                    mSxBK = mSx;
                    mSyBK = mSy;
                    mMoveX = mDownX = event.getX();
                    mMoveY = mDownY = event.getY();
                    Log.d(LOG_TAG, "dmSx=" + mSx + " dmSy=" + mSy);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d(LOG_TAG, "on touch ACTION_UP" + "x=" + event.getX() + "y=" + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(LOG_TAG, "on touch ACTION_MOVE" + "x=" + event.getX() + "y=" + event.getY());
                mMoveX = event.getX();
                mMoveY = event.getY();
                if (!mDrawInThread) {
                    syncDraw();
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void generateBitmap(float scale) {
        synchronized (lock) {
            if (mBkBitmap != null) {
                mBkBitmap.recycle();
                mBkBitmap = null;
                System.gc();
            }
            Log.d(LOG_TAG, "ww=" + getWidth() + "hh=" + getHeight());
            int ww = getWidth();
            int hh = getHeight();

            float nn = scale*ww*2;
            int bitmapw = (int)(scale*ww*2);
            int bitmaph = (int)(scale*hh*2);
            mBkBitmap = Bitmap.createBitmap(bitmapw, bitmaph, Bitmap.Config.ARGB_8888);
            Canvas bkCanvas = new Canvas(mBkBitmap);
            Log.d(LOG_TAG, "canvas w=" + bkCanvas.getWidth() + " canvas h=" + bkCanvas.getHeight() + "nn=" + nn);
            bkCanvas.drawColor(Color.WHITE);
            Paint pText = new Paint();
            pText.setAntiAlias(true);
            pText.setColor(Color.RED);
            pText.setTextSize(scale * 50);
            pText.setStrokeWidth(1);
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Black.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-BlackItalic.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-BoldItalic.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Italic.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-LightItalic.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-MediumItalic.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
//            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Thin.ttf");
////            Typeface myTF = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-ThinItalic.ttf");
//            pText.setTypeface(myTF);
//            pText.setTypeface(Typeface.DEFAULT_BOLD);
//            pText.setTypeface(Typeface.MONOSPACE);
            int y = (int)(scale*100);
            while (y < bkCanvas.getHeight()) {
                bkCanvas.drawText("abcABC央视网消息(新闻联播)：中共中央总书记、国家主席、中央军委主席习近平12日上午在中国共产党", 0, y, pText);
                y += (int)(scale*100);
            }
            maxx = bitmapw - ww;
            maxy = bitmaph - hh;
            mSx = mSx*scale;
            mSy = mSy*scale;
        }
    }

    public void setController(GameController gameController) {
        mController = gameController;

    }

    public void syncDraw() {
        float t1, t2;
        Canvas c = mSurfaceHolder2.lockCanvas();
        if (c != null) {
//                        doDraw(c);
//                        Log.d(LOG_TAG, "main canvas w=" + c.getWidth() + " canvas h=" + c.getHeight());
            synchronized (lock) {
                t1 = mSxBK + mDownX - mMoveX;
                t2 = mSyBK + mDownY - mMoveY;
                if (t1 < minx) {
                    t1 = minx;
                }
                if (t2 < miny) {
                    t2 = miny;
                }
                if (t1 > maxx) {
                    t1 = maxx;
                }
                if (t2 > maxy) {
                    t2 = maxy;
                }
                mSx = t1;
                mSy = t2;

                //                        Log.d(LOG_TAG, "mSx=" + mSx + " mSy=" + mSy);
                Rect src = new Rect((int) mSx, (int) mSy, (int) mSx + c.getWidth(), (int) mSy + c.getHeight());
                Rect des = new Rect(0, 0, c.getWidth(), c.getHeight());
                c.drawBitmap(mBkBitmap, src, des, null);

                Paint pp = new Paint();
                pp.setColor(Color.BLUE);
                pp.setTextSize(60);
                boolean b = c.isHardwareAccelerated();
                if (b) {
                    c.drawText("hardware", 200, 200, pp);
                } else {
                    c.drawText("no hardware", 200, 200, pp);
                }
            }
            mSurfaceHolder2.unlockCanvasAndPost(c);
        }
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

            float t1, t2;

            Log.d(LOG_TAG, "draw start");
            while (mRunning) {
//                Log.d(LOG_TAG, "1");
                try {
//                    Log.d(LOG_TAG, "2");
                    Log.d(LOG_TAG, "draw!");
                    Canvas c = mSurfaceHolder.lockCanvas();
                    if (c != null) {
//                        doDraw(c);
//                        Log.d(LOG_TAG, "main canvas w=" + c.getWidth() + " canvas h=" + c.getHeight());
                        synchronized (lock) {
                            t1 = mSxBK + mDownX - mMoveX;
                            t2 = mSyBK + mDownY - mMoveY;
                            if (t1 < minx) {
                                t1 = minx;
                            }
                            if (t2 < miny) {
                                t2 = miny;
                            }
                            if (t1 > maxx) {
                                t1 = maxx;
                            }
                            if (t2 > maxy) {
                                t2 = maxy;
                            }
                            mSx = t1;
                            mSy = t2;

    //                        Log.d(LOG_TAG, "mSx=" + mSx + " mSy=" + mSy);
                            Rect src = new Rect((int)mSx, (int)mSy, (int)mSx+c.getWidth(), (int)mSy+c.getHeight());
                            Rect des = new Rect(0, 0, c.getWidth(), c.getHeight());
                            c.drawBitmap(mBkBitmap, src, des, null);

                            Paint pp = new Paint();
                            pp.setColor(Color.BLUE);
                            pp.setTextSize(60);
                            boolean b = c.isHardwareAccelerated();
                            if (b) {
                                c.drawText("hardware", 200, 200, pp);
                            } else {
                                c.drawText("no hardware", 200, 200, pp);
                            }
                        }
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
//            Paint paintRed;
//            Paint paintYellow, paintBlue;
//
//            paintRed = new Paint();
//            paintRed.setColor(Color.RED);
//            paintRed.setStyle(Paint.Style.FILL);
//            paintRed.setStrokeWidth(2);
//            paintRed.setStrokeJoin(Paint.Join.ROUND);
//            paintRed.setAntiAlias(true);
//
//            paintYellow = new Paint();
//            paintYellow.setColor(Color.YELLOW);
//            paintYellow.setStyle(Paint.Style.FILL);
//            paintYellow.setStrokeWidth(2);
//
//            paintBlue = new Paint();
//            paintBlue.setColor(Color.BLUE);
//            paintBlue.setStyle(Paint.Style.STROKE);
//            paintBlue.setStrokeWidth(5);
//            paintBlue.setAntiAlias(true);
//            paintBlue.setDither(true);
//            paintBlue.setStrokeJoin(Paint.Join.ROUND);
//            paintBlue.setStrokeCap(Paint.Cap.ROUND);
//
//            c.drawColor(Color.WHITE);
//            c.drawColor(Color.argb(255, 255, 255, 255));
//            c.drawRect(0, 0, 100, 100, paintYellow);
//            c.drawArc(0, 0, 100, 100, 0, 120, true, paintRed);
//            c.save();
//            c.translate(100, 100);
//            c.drawRect(0, 0, 100, 100, paintRed);
//            c.drawArc(0, 0, 100, 100, 0, 120, true, paintYellow);
//            c.restore();
////            c.translate(-100, -100);
//            c.drawCircle(300, 100, 50, paintBlue);
//            c.drawLine(400, 50, 500, 100, paintRed);
//            float[] dots = {500,0,550,0,550,0,600,50,600,50,650,50};
//            c.drawLines(dots, paintBlue);
//            c.drawOval(new RectF(0, 200, 50, 300), paintRed);
//            Path pa = new Path();
//            pa.moveTo(100, 300);
//            pa.lineTo(100, 350);
//            pa.lineTo(200, 400);
//            pa.lineTo(100, 300);
//            c.drawPath(pa, paintYellow);
//            paintRed.setStrokeWidth(10);
//            c.drawPoint(100, 500, paintRed);
//            paintYellow.setStrokeWidth(2);
//            float[] pts = {120, 500, 130, 550, 140, 600};
//            c.drawPoints(pts, paintYellow);
//            c.drawRoundRect(300, 200, 500, 300, 30, 30, paintRed);
//            c.drawRoundRect(300, 200, 500, 300, 30, 70, paintBlue);
//            c.drawRoundRect(300, 200, 500, 300, 70, 70, paintYellow);
//            paintRed.setStrokeWidth(2);
//            c.drawText("helloworld", 300, 600, paintRed);
////            Log.d("xxx", "text size = " + paintBlue.getTextSize());
//            paintBlue.setTextSize(36);syncDraw();
//            paintYellow.setTextSize(40);
//            c.drawTextOnPath("iloveyou", pa, 0, 0, paintBlue);
//            c.translate(c.getWidth() / 2, c.getHeight() / 2);
////            c.drawCircle(0, 0, 300, paintRed);
////            String time[] = {"12","1","2","3","4","5","6","7","8","9","10","11"};
////            for (int i = 0; i < 12; i++) {
////                c.drawText(time[i], -20, -250, paintYellow);
////                c.rotate(30);
////            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(LOG_TAG, "surfaceCreated");
        mSurfaceHolder2 = holder;
        generateBitmap(1);
        if (!mDrawInThread) {
            syncDraw();
        } else {
            mDrawThread = new DrawThread(null, holder);
            mDrawThread.mRunning = true;
            mDrawThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(LOG_TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(LOG_TAG, "surfaceDestroyed");
        if (mDrawInThread) {
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
}
