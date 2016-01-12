package com.example.chengqi.mycoderepo.layouts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chengqi.mycoderepo.R;

public class TableLayoutActivity extends AppCompatActivity {

    private static final String LOG_TAG = "TableLayoutActivity";
    private Button btnExcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout);
        btnExcel = (Button)findViewById(R.id.button39);
        btnExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableLayoutActivity.this, ExcelDemoActivity.class);
                startActivity(intent);
            }
        });
    }
}
