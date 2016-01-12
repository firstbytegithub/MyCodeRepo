package com.example.chengqi.mycoderepo.layouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.chengqi.mycoderepo.R;

public class Excel2DemoActivity extends AppCompatActivity {
    GridLayout gl;
    Button btns[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel2_demo);
        LayoutInflater ll = getLayoutInflater();
        View vv = ll.inflate(R.layout.grid_button, null);
        Button bbb = (Button) vv.findViewById(R.id.button119);
        btns = new Button[200];
        gl = (GridLayout) findViewById(R.id.excelgrid);
        gl.setColumnCount(10);

        gl.setRowCount(20);
        for (int i = 0; i < 200; i++) {
            btns[i] = new Button(this);
            btns[i].setText(String.valueOf(i));
            if (i == 4) {
                gl.addView(bbb);
            } else {
                gl.addView(btns[i]);
            }
        }

    }
}
