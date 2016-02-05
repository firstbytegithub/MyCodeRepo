package com.example.chengqi.mycoderepo.expert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chengqi.mycoderepo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class InternetActivity extends AppCompatActivity {

    private static final String TAG = "InternetActivity";

    private Button mBtnGet;
    private Button mBtnPut;
    private Button mBtnPost;
    private Button mBtnDelete;

    private String mMethod = "GET";

    private static final String URL_API = "http://coolyea-hello.aliapp.com/api/youhaocalc/test.php";

    class HttpThread extends Thread {

        @Override
        public void run() {
            HttpURLConnection httpConnection = null;
            InputStream is = null;
            try {
                URL mUrl = new URL(URL_API);
                httpConnection = (HttpURLConnection)mUrl.openConnection();
                httpConnection.setDoInput(true);
                if (mMethod.equals("POST")) {
                    httpConnection.setDoOutput(true);
                }
                httpConnection.setRequestMethod(mMethod);
//                    httpConnection.connect();
                is = httpConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String resultData = "";
                String inputLine  = "";
                while((inputLine = bufferedReader.readLine()) != null){
                    resultData += inputLine + "\n";
                }
                Log.d(TAG, resultData);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if(httpConnection != null){
                    httpConnection.disconnect();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        mBtnGet = (Button)findViewById(R.id.button102);
        mBtnPut = (Button)findViewById(R.id.button103);
        mBtnPost = (Button)findViewById(R.id.button104);
        mBtnDelete = (Button)findViewById(R.id.button105);

        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMethod = "GET";
                new HttpThread().start();
            }
        });

        mBtnPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMethod = "POST";
                new HttpThread().start();
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
