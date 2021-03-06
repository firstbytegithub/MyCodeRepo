package com.example.chengqi.mycoderepo.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class AppActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String LOG_TAG = "AppActivity";
    private ListView mListView;

    final private String[] strItems = new String[]{
            "Activity",
            "Service",
            "Content Provider",
            "Broadcast Receiver",
            "Window", // demo show/hide statusbar/navi/fullscreen
            "Theme",
            "Theme2",
            // get camera shot/open file manager/device manager/dial/sens sms,
            // mms,email/open web page/play audio,video/open galaeryy/open contact ......
            "Multitask"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_app);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, AppActivityActivity.class);
                break;
            case 1:
                intent = new Intent(this, AppServiceActivity.class);
                break;
            case 2:
                intent = new Intent(this, AppContentProviderActivity.class);
                break;
            case 3:
                intent = new Intent(this, AppBroadCastReceiverActivity.class);
                break;
            case 4:
                intent = new Intent(this, AppWindowActivity.class);
                break;
            case 5:
                intent = new Intent(this, AppThemeActivity.class);
                break;
            case 6:
                intent = new Intent(this, AppTheme2Activity.class);
                break;
            case 7:
                intent = new Intent(this, DemoMultiTask.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
