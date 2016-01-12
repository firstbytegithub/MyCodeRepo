package com.example.chengqi.mycoderepo.software;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chengqi.mycoderepo.R;

import java.io.File;
import java.io.IOException;

public class DemoShare extends AppCompatActivity {
    Button mShareText;
    Button mShareFile;
    Button mShareImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_share);

        mShareText = (Button) findViewById(R.id.btn_shareto);
        mShareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent();
                send.setAction(Intent.ACTION_SEND);
                send.setType("text/plain");
                send.putExtra(Intent.EXTRA_TEXT, "chengqi");
                startActivity(send);
            }
        });

        mShareFile = (Button) findViewById(R.id.button7);
        mShareFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = Environment.getExternalStorageDirectory().getPath()+"/"+"plan.xls";
                Log.d("xxx", filePath);
                File f = new File(filePath);
                if (!f.exists()) {
                    try {
                        f.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent send = new Intent();
                send.setAction(Intent.ACTION_SEND);
                send.setType("*/*");
                send.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                startActivity(send);
            }
        });

        mShareImage = (Button) findViewById(R.id.button8);
        mShareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imagePath = Environment.getExternalStorageDirectory().getPath()+"/"+"IMAG0001.jpg";
                Log.d("xxx", imagePath);
                File f = new File(imagePath);
                if (!f.exists()) {
                    try {
                        f.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent send = new Intent();
                send.setAction(Intent.ACTION_SEND);
                send.setType("image/*");
                send.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
                startActivity(send);
            }
        });
    }
}
