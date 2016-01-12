package com.example.chengqi.mycoderepo.software;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chengqi.mycoderepo.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class ChooseFileActivity extends AppCompatActivity {

    private static final String TAG = "ChooseFileActivity";
    private Button mButtonGetImage;
    private Button mButtonGetFile;
    private Button mButtonGetMp3;
    private Button mButtonGetWord;
    private TextView mLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file);

        mButtonGetImage = (Button) findViewById(R.id.button79);
        mButtonGetFile = (Button) findViewById(R.id.button80);
        mButtonGetMp3 = (Button) findViewById(R.id.button81);
        mButtonGetWord = (Button) findViewById(R.id.button82);
        mLog = (TextView) findViewById(R.id.textView35);

        mButtonGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, 37);
            }
        });

        mButtonGetFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, 37);
            }
        });

        mButtonGetMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("audio/mpeg");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, 37);
            }
        });

        mButtonGetWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{
                        "text/plain",
                        "application/msword",
                        "application/vnd.ms-excel",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});

//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, 37);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final ContentResolver resolver = getContentResolver();

//        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult " + "requestCode=" + requestCode + " resultCode=" + resultCode);
        final Uri uri = data != null ? data.getData() : null;
        if (uri == null) {
            return;
        }

        Log.d(TAG, "uri=" + uri + " path=" + uri.getPath());
        mLog.setText(uri.toString());

        if (requestCode == 37) {
            try {
                resolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            InputStream is = null;
            OutputStream os = null;
            try {
                is = resolver.openInputStream(uri);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (is != null) {
                    mLog.setText(mLog.getText() + "\n" + "got input stream");
                    byte [] buf = new byte[5];
                    int len = is.read(buf);
                    Log.d(TAG, "read=" + Arrays.toString(buf) + " n=" + len);
                    is.close();

                }
                os = resolver.openOutputStream(uri);
                if (os != null) {
                    mLog.setText(mLog.getText() + "\n" + "got output stream");
                    String str = "abcdefgabcdefgabcdefgabcdefgabcdefg";
                    os.write(str.getBytes());
                    Log.d(TAG, "write=" + Arrays.toString(str.getBytes()));
                    os.flush();
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
