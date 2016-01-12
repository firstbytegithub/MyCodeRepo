package com.example.chengqi.mycoderepo.widgets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class WidgetsActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String LOG_TAG = "WidgetsActivity";
    private ListView mListView;

    final private String[] strItems = new String[]{
            "TextView",
            "Button",
            "RadioButton",
            "CheckBox",
            "Switch",
            "ToggleButton",
            "ImageButton",
            "ImageView",
            "ProgressBar",
            "SeekBar",
            "RatingBar",
            "Spinner",
            "WebView",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widgets);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_widgets);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, TextViewActivity.class);
                break;
            case 1:
                intent = new Intent(this, ButtonActivity.class);
                break;
            case 2:
                intent = new Intent(this, RadioButtonActivity.class);
                break;
            case 3:
                intent = new Intent(this, CheckBoxActivity.class);
                break;
            case 4:
                intent = new Intent(this, SwitchActivity.class);
                break;
            case 5:
                intent = new Intent(this, ToggleButtonActivity.class);
                break;
            case 6:
                intent = new Intent(this, ImageButtonActivity.class);
                break;
            case 7:
                intent = new Intent(this, ImageViewActivity.class);
                break;
            case 8:
                intent = new Intent(this, ProgressBarActivity.class);
                break;
            case 9:
                intent = new Intent(this, SeekBarActivity.class);
                break;
            case 10:
                intent = new Intent(this, RatingBarActivity.class);
                break;
            case 11:
                intent = new Intent(this, SpinnerActivity.class);
                break;
            case 12:
                intent = new Intent(this, WebViewActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
