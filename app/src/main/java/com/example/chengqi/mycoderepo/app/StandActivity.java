package com.example.chengqi.mycoderepo.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chengqi.mycoderepo.R;

public class StandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stand);

        ((Button)findViewById(R.id.button22)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandActivity.this, DemoActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        Log.d("xxxx", this.toString());
        super.onResume();
    }
}
