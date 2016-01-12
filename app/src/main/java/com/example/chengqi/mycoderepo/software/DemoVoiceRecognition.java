package com.example.chengqi.mycoderepo.software;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.PowerManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chengqi.mycoderepo.R;

import java.util.ArrayList;
import java.util.List;

public class DemoVoiceRecognition extends AppCompatActivity {
    private Button mButtonSpeak;
    private TextView mTxtResult;
    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_voice_recognition);

        mButtonSpeak = (Button) findViewById(R.id.button88);
        mTxtResult = (TextView) findViewById(R.id.textView39);
        mList = (ListView) findViewById(R.id.listView2);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "fddf");
        wl.acquire();

        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            mButtonSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
                    startActivityForResult(intent, 337);
                }
            });
        } else {
            mButtonSpeak.setEnabled(false);
            mTxtResult.setText("Recognizer not present");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("xxxxx", "requestCode=" + requestCode + " resultCode=" + resultCode);
        if (requestCode == 337 && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer
            // thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            mList.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    matches));
        }
    }
}
