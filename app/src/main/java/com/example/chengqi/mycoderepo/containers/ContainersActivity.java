package com.example.chengqi.mycoderepo.containers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class ContainersActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String LOG_TAG = "ContainersActivity";
    private ListView mListView;

    final private String[] strItems = new String[]{
            "RadioGroup",
            "ListView",
            "GridView",
            "ExpandableListView",
            "ScrollView",
            "HorizontalScrollView",
            "SearchView",
            "TabHost",
            "VideoView",
            "DialerFilter",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containers);
        initListView();
    }
    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_container);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, ContainerRadioGroupActivity.class);
                break;
            case 1:
                intent = new Intent(this, ContainerListViewActivity.class);
                break;
            case 2:
                intent = new Intent(this, ContainerGridViewActivity.class);
                break;
            case 3:
                intent = new Intent(this, ContainerExpandableListViewActivity.class);
                break;
            case 4:
                intent = new Intent(this, ContainerScrollViewActivity.class);
                break;
            case 5:
                intent = new Intent(this, ContainerHorizontalScrollViewActivity.class);
                break;
            case 6:
                intent = new Intent(this, ContainerSearchViewActivity.class);
                break;
            case 7:
                intent = new Intent(this, ContainerTabHostActivity.class);
                break;
            case 8:
                intent = new Intent(this, ContainerVideoViewActivity.class);
                break;
            case 9:
                intent = new Intent(this, ContainerDialerFilterActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
