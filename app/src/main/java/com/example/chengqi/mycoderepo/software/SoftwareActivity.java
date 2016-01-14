package com.example.chengqi.mycoderepo.software;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class SoftwareActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    final private String LOG_TAG = "SoftwareActivity";
    private ListView mListView;

    final private String[] strItems = new String[] {
            "System Information",
            "Net",
            "Time",
            "OpenGL",
            "TTS",
            "Crypto",
            "Audio/Video",
            "AppWidget",
            "APK",
            "Gesture",
            "Wallpaper",
            "animation",
            "Share text/img/file",
            "Voice Recognition",
            "choose file",
            "notification",
            "voice recognition",
            "IFlyTekSpeechRecognition",
            "Excel",
            "BenchMark"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_software);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, SystemInfoActivity.class);
                break;
            case 1:
                intent = new Intent(this, NetActivity.class);
                break;
            case 2:
                intent = new Intent(this, SoftTimeActivity.class);
                break;
            case 3:
                intent = new Intent(this, OpenGLActivity.class);
                break;
            case 4:
                intent = new Intent(this, TtsActivity.class);
                break;
            case 5:
                intent = new Intent(this, CryptoActivity.class);
                break;
            case 6:
                intent = new Intent(this, AudioVideoActivity.class);
                break;
            case 7:
                intent = new Intent(this, AppWidgetActivity.class);
                break;
            case 8:
                intent = new Intent(this, APKActivity.class);
                break;
            case 9:
                intent = new Intent(this, GestureActivity.class);
                break;
            case 10:
                intent = new Intent(this, WallpaperActivity.class);
                break;
            case 11:
                intent = new Intent(this, DemoAnimation.class);
                break;
            case 12:
                intent = new Intent(this, DemoShare.class);
                break;
            case 13:
                intent = new Intent(this, DemoVoice.class);
                break;
            case 14:
                intent = new Intent(this, ChooseFileActivity.class);
                break;
            case 15:
                intent = new Intent(this, NotificationActivity.class);
                break;
            case 16:
                intent = new Intent(this, DemoVoiceRecognition.class);
                break;
            case 17:
                intent = new Intent(this, IFlyTekSpeechRecognition.class);
                break;
            case 18:
                intent = new Intent(this, DemoExcel.class);
                break;
            case 19:
                intent = new Intent(this, BenchMark.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
