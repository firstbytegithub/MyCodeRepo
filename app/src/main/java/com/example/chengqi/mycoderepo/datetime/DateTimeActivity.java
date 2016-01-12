package com.example.chengqi.mycoderepo.datetime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class DateTimeActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String LOG_TAG = "CustomActivity";
    private ListView mListView;

    final private String[] strItems = new String[]{
            "TextClock",
            "AnalogClock",
            "Chronometer",
            "DatePicker",
            "TimePicker",
            "CalendarView",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_datetime);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, TextClockActivity.class);
                break;
            case 1:
                intent = new Intent(this, AnalogClockActivity.class);
                break;
            case 2:
                intent = new Intent(this, ChronometerActivity.class);
                break;
            case 3:
                intent = new Intent(this, DatePickerActivity.class);
                break;
            case 4:
                intent = new Intent(this, TimePickerActivity.class);
                break;
            case 5:
                intent = new Intent(this, CalendarViewActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
