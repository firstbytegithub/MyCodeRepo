package com.example.chengqi.mycoderepo.textfield;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class TextFieldsActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String LOG_TAG = "TextFieldsActivity";
    private ListView mListView;

    final private String[] strItems = new String[]{
            "Plain Text",
            "Person Name",
            "Password",
            "E-mail",
            "Phone",
            "Postal Address",
            "Multiline Text",
            "Time",
            "Date",
            "Number",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_fields);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_textfields);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, PlainTextActivity.class);
                break;
            case 1:
                intent = new Intent(this, PersonNameActivity.class);
                break;
            case 2:
                intent = new Intent(this, PersonNameActivity.class);
                break;
            case 3:
                intent = new Intent(this, EmailActivity.class);
                break;
            case 4:
                intent = new Intent(this, PhoneActivity.class);
                break;
            case 5:
                intent = new Intent(this, PostalAddressActivity.class);
                break;
            case 6:
                intent = new Intent(this, MultilineTextActivity.class);
                break;
            case 7:
                intent = new Intent(this, TimeActivity.class);
                break;
            case 8:
                intent = new Intent(this, DateActivity.class);
                break;
            case 9:
                intent = new Intent(this, NumberActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
