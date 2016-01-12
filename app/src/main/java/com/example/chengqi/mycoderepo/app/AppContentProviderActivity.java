package com.example.chengqi.mycoderepo.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chengqi.mycoderepo.R;

public class AppContentProviderActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static String LOG_TAG = "AppContentProviderActivity";
    private EditText editTextId;
    private EditText editTextName;
    private Button btnInsert;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnQuery;
    private TextView txtAll;
    private TextView txtQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_content_provider);

        editTextId = (EditText) findViewById(R.id.editText2);
        editTextName = (EditText) findViewById(R.id.editText3);
        btnInsert = (Button) findViewById(R.id.button28);
        btnDelete = (Button) findViewById(R.id.button29);
        btnUpdate = (Button) findViewById(R.id.button30);
        btnQuery = (Button) findViewById(R.id.button31);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        txtAll = (TextView)findViewById(R.id.textView3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button28:
                doInsert();
                break;
            case R.id.button29:
                doDelete();
                break;
            case R.id.button30:
                doUpdate();
                break;
            case R.id.button31:
                doQuery();
                break;
        }
    }

    void doInsert() {
        String id = editTextId.getText().toString();
        String name = editTextName.getText().toString();

        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);

        Uri newrow = getContentResolver().insert(DemoContentProvider.CONTENT_URI, values);
//        getContentResolver().insert(Uri.parse("content://com.example.chengqi.mycoderepo.app.DemoContentProvider"), values);
        Log.d(LOG_TAG, "insert = " + newrow.toString());
        doQuery();
//        getContentResolver().registerContentObserver();
    }

    void doDelete() {
        String id = editTextId.getText().toString();
        String selection = "id = \"" + id + "\"";
        int deleted = getContentResolver().delete(DemoContentProvider.CONTENT_URI, selection, null);
        Log.d(LOG_TAG, "delete count = " + deleted);
        doQuery();
    }

    void doUpdate() {
        String id = editTextId.getText().toString();
        String name = editTextName.getText().toString();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        String where = "id = \"" + id + "\"";
        int updated = getContentResolver().update(DemoContentProvider.CONTENT_URI, values, where, null);
        Log.d(LOG_TAG, "update count = " + updated);
        doQuery();
    }

    void doQuery() {
        String id = editTextId.getText().toString();
//        String selection = "id = \"" + id + "\"";
        Cursor cursor = getContentResolver().query(DemoContentProvider.CONTENT_URI, null, null, null, null);
        Log.d(LOG_TAG, "query count = " + cursor.getCount());
        txtAll.setText(null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String _name = cursor.getString(1);
            String s = _id + _name + '\n';
            txtAll.append(s);
        }
    }
}
