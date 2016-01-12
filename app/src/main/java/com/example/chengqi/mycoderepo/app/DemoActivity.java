package com.example.chengqi.mycoderepo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chengqi.mycoderepo.R;

public class DemoActivity extends Activity {
    private static final String TAG = "DemoActivity";
    private Button mBtn18, mBtn19, mBtn20, mBtn21;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button18) {
                Intent intent = new Intent(DemoActivity.this, StandActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.button19) {
                Intent intent = new Intent(DemoActivity.this, SingleTopActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.button20) {
                Intent intent = new Intent(DemoActivity.this, SingleTaskActivity.class);
                startActivity(intent);
            } else if (v.getId() == R.id.button21) {
                Intent intent = new Intent(DemoActivity.this, SingleInstanceActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("xxxx onCreate", this.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mBtn18 = (Button) findViewById(R.id.button18);
        mBtn19 = (Button) findViewById(R.id.button19);
        mBtn20 = (Button) findViewById(R.id.button20);
        mBtn21 = (Button) findViewById(R.id.button21);

        mBtn18.setOnClickListener(listener);
        mBtn19.setOnClickListener(listener);
        mBtn20.setOnClickListener(listener);
        mBtn21.setOnClickListener(listener);
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
