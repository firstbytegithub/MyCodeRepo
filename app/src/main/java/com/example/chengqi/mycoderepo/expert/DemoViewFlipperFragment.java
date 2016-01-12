package com.example.chengqi.mycoderepo.expert;

import android.app.TaskStackBuilder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.chengqi.mycoderepo.R;

public class DemoViewFlipperFragment extends AppCompatActivity
        implements View.OnTouchListener {
    private ViewFlipper vf;
    private ImageView iv;
    private int pos = 0;
    private Animation leftIn;
    private Animation leftOut;
    private Animation rightIn;
    private Animation rightOut;

    private float touchDownX;
    private float touchUpX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view_flipper_fragment);

        vf = (ViewFlipper) findViewById(R.id.viewFlipper2);
        vf.setOnTouchListener(this);

        iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.pos1);

        leftIn = AnimationUtils.loadAnimation(this, R.anim.leftin);
        leftOut = AnimationUtils.loadAnimation(this, R.anim.leftout);
        rightIn = AnimationUtils.loadAnimation(this, R.anim.rightin);
        rightOut = AnimationUtils.loadAnimation(this, R.anim.rightout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("xxxx", "touch 2!!");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchDownX = event.getX();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            touchUpX = event.getX();
            if (touchUpX - touchDownX > 100) {
//                vf.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
//                vf.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.leftin));
                vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.rightout));
                vf.showPrevious();
                if (pos == 0) {
                    iv.setImageResource(R.drawable.pos2);
                    pos = 1;
                } else {
                    iv.setImageResource(R.drawable.pos1);
                    pos = 0;
                }
                return true;
            } else if (touchDownX - touchUpX > 100) {
                vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.rightin));
                vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.leftout));
//                vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.leftout));
//                vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.rightin));
                vf.showNext();
                if (pos == 0) {
                    iv.setImageResource(R.drawable.pos2);
                    pos = 1;
                } else {
                    iv.setImageResource(R.drawable.pos1);
                    pos = 0;
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onCreateNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateNavigateUpTaskStack(builder);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("xxxx", "touch !!");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchDownX = event.getX();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            touchUpX = event.getX();
            if (touchUpX - touchDownX > 100) {
//                vf.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
//                vf.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
                vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.leftin));
                vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.rightout));
                vf.showPrevious();
                return true;
            } else if (touchDownX - touchUpX > 100) {
                vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.rightin));
                vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.leftout));
//                vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.leftout));
//                vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.rightin));
                vf.showNext();
                return true;
            }
        }
        return false;
    }
}
