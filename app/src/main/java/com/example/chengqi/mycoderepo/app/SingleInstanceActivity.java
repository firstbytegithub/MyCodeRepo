package com.example.chengqi.mycoderepo.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chengqi.mycoderepo.R;

public class SingleInstanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);

        ((Button)findViewById(R.id.button25)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleInstanceActivity.this, DemoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d("xxxx onStart", this.toString());
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d("xxxx onRestart", this.toString());
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("xxxx onResume", this.toString());
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        Log.d("xxxx onPostResume", this.toString());
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        Log.d("xxxx onPause", this.toString());
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("xxxx onStop", this.toString());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("xxxx onDestroy", this.toString());
        super.onDestroy();
    }
}
