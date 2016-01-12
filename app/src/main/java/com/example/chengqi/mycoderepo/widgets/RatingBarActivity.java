package com.example.chengqi.mycoderepo.widgets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.chengqi.mycoderepo.R;

public class RatingBarActivity extends AppCompatActivity {
    private static final String LOG_TAG = "RatingBarActivity";
    private RatingBar rb;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);
        rb = (RatingBar) findViewById(R.id.ratingBar);
        btn= (Button) findViewById(R.id.button77);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb.setRating(3);
            }
        });
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.d(LOG_TAG, "change to " + rating);
            }
        });
    }
}
