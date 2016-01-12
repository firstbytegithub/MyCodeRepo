package com.example.chengqi.mycoderepo.hardware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class HardwareActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String LOG_TAG = "HardwareActivity";
    private ListView mListView;

    final private String[] strItems = new String[]{
            "Key",
            "Display Screen",
            "Touch Screen",
            "Vibrator",
            "Battery",
            "Power",
            "Bluetooth",
            "Wifi",
            "Earphone",
            "Camera & Flash",
            "Storage",
            "Sensors",
            "GPS",
            "Fingerprint",
            "NFC",
            "USB",
            "Print"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_hw);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, HWKeyActivity.class);
                break;
            case 1:
                intent = new Intent(this, HWDisplayActivity.class);
                break;
            case 2:
                intent = new Intent(this, HWTouchActivity.class);
                break;
            case 3:
                intent = new Intent(this, HWVibratorActivity.class);
                break;
            case 4:
                intent = new Intent(this, HWBatteryActivity.class);
                break;
            case 5:
                intent = new Intent(this, HWPowerActivity.class);
                break;
            case 6:
                intent = new Intent(this, HWBluetoothActivity.class);
                break;
            case 7:
                intent = new Intent(this, HWWifiActivity.class);
                break;
            case 8:
                intent = new Intent(this, HWEarphoneActivity.class);
                break;
            case 9:
                intent = new Intent(this, HWCameraActivity.class);
                break;
            case 10:
                intent = new Intent(this, HWStorageActivity.class);
                break;
            case 11:
                intent = new Intent(this, HWSensorsActivity.class);
                break;
            case 12:
                intent = new Intent(this, HWGPSActivity.class);
                break;
            case 13:
                intent = new Intent(this, HWFingerprintActivity.class);
                break;
            case 14:
                intent = new Intent(this, HWNFCActivity.class);
                break;
            case 15:
                intent = new Intent(this, HWUSBActivity.class);
                break;
            case 16:
                intent = new Intent(this, HWPrintActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
