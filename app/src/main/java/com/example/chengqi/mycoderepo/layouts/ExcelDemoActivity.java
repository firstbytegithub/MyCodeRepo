package com.example.chengqi.mycoderepo.layouts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.chengqi.mycoderepo.R;

public class ExcelDemoActivity extends Activity {

    private static final String LOG_TAG = "ExcelDemoActivity";
    private TableLayout tl;
    private TableRow[] tr;
    private Button btn1, btn2;
    private Button[][] btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_demo);
        tl = (TableLayout) findViewById(R.id.excel);
        btns = new Button[30][10];
        tr = new TableRow[30];
        for (int i = 0; i < 30; i++) {
            tr[i] = new TableRow(this);
            for (int j = 0; j < 10; j++) {
                btns[i][j] = new Button(this);
                btns[i][j].setText(String.valueOf(i));
//                btns[i][j].setBackgroundColor(0xff0000);
//                btns[i][j].setTag(new Map<Integer, Integer>(i, j) {
//                });
                tr[i].addView(btns[i][j]);
            }
            tl.addView(tr[i]);
        }
    }
}
