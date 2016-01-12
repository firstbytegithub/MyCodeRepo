package com.example.chengqi.mycoderepo.custom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class CustomActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String LOG_TAG = "CustomActivity";
    private ListView mListView;

    final private String[] strItems = new String[]{
            "CustomView",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_custom);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, CustomViewActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
