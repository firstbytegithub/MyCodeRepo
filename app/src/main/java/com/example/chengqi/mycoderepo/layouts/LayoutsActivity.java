package com.example.chengqi.mycoderepo.layouts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class LayoutsActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String LOG_TAG = "ExpertActivity";
    private ListView mListView;

    final private String[] strItems = new String[]{
            "FrameLayout",
            "LinearLayout (Horizontal)",
            "LinearLayout (Vertical)",
            "TableLayout",
            "TableRow",
            "GridLayout",
            "RelativeLayout",
            "DrawerLayout"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_layouts);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, FrameLayoutActivity.class);
                break;
            case 1:
                intent = new Intent(this, LinearLayoutHActivity.class);
                break;
            case 2:
                intent = new Intent(this, LinearLayoutVActivity.class);
                break;
            case 3:
                intent = new Intent(this, TableLayoutActivity.class);
                break;
            case 4:
                intent = new Intent(this, TableRowActivity.class);
                break;
            case 5:
                intent = new Intent(this, GridLayoutActivity.class);
                break;
            case 6:
                intent = new Intent(this, RelativeLayoutActivity.class);
                break;
            case 7:
                intent = new Intent(this, DrawerLayoutActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
