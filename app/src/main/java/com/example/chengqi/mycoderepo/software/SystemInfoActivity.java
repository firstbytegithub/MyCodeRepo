package com.example.chengqi.mycoderepo.software;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.example.chengqi.mycoderepo.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class SystemInfoActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SystemInformation";
    private TextView mTextView;
    private String mOutput = "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_info);

        mTextView = (TextView) findViewById(R.id.tv_info);

        getSystemInformation();
        mTextView.setText(mOutput);
    }

    private void output(String s) {
        mOutput = mOutput + s + '\n';
    }

    private String getAvailMemory() {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存

        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// 将获取的内存大小规格化
    }
    private String getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    private String getMacAddress(){
        String result = "";
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
//        Log.i("text", "手机macAdd:" + result);
        return result;
    }

    private void getSystemInformation() {
        output("----------------------------");
        output("sdk int=" + String.valueOf(Build.VERSION.SDK_INT));
        output("Build.BOARD=" + Build.BOARD);
        output("Build.BOOTLOADER=" + Build.BOOTLOADER);
        output("Build.BRAND=" + Build.BRAND);
        output("Build.DEVICE=" + Build.DEVICE);
        output("Build.DISPLAY=" + Build.DISPLAY);
        output("Build.FINGERPRINT=" + Build.FINGERPRINT);
        output("Build.getRadioVersion=" + Build.getRadioVersion());
        output("Build.HARDWARE=" + Build.HARDWARE);
        output("Build.HOST=" + Build.HOST);
        output("Build.ID=" + Build.ID);
        output("Build.MANUFACTURER=" + Build.MANUFACTURER);
        output("Build.MODEL=" + Build.MODEL);
        output("Build.PRODUCT=" + Build.PRODUCT);
        output("Build.SERIAL=" + Build.SERIAL);
        output("Build.TAGS=" + Build.TAGS);
        output("Build.TYPE=" + Build.TYPE);
        output("Build.UNKNOWN=" + Build.UNKNOWN);
        output("Build.USER=" + Build.USER);
        if (Build.VERSION.SDK_INT >= 23) {
            output("Build.VERSION.BASE_OS=" + Build.VERSION.BASE_OS);
            output("Build.VERSION.SECURITY_PATCH=" + Build.VERSION.SECURITY_PATCH);
        }
        output("Build.VERSION.CODENAME=" + Build.VERSION.CODENAME);
        output("Build.VERSION.INCREMENTAL=" + Build.VERSION.INCREMENTAL);
        output("Build.VERSION.RELEASE=" + Build.VERSION.RELEASE);

        output("----------------------------");
        output("output of System.getenv()");
        Map<String, String> mapEnv = System.getenv();
        String ss = mapEnv.toString();
        ss = ss.replace('{', ' ');
        ss = ss.replace('}', ' ');
        ss = ss.replace(' ', '\n');
        output(ss);
        output("----------------------------");
        output("total memory=" + getTotalMemory());
        output("avail memory=" + getAvailMemory());
        output("----------------------------");
        Point pSize = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            getWindowManager().getDefaultDisplay().getRealSize(pSize);
        } else {
            getWindowManager().getDefaultDisplay().getSize(pSize);
        }
        output("display width/height=" + pSize.toString());
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        } else {
            getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        output(dm.toString());
        output("----------------------------");
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_VOICE_CALL );
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        output("STREAM_VOICE_CALL max current=" + max + " " + current);
        max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
        current = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        output("STREAM_SYSTEM max current=" + max + " " + current);
        max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_RING );
        current = mAudioManager.getStreamVolume(AudioManager.STREAM_RING);
        output("STREAM_RING max current=" + max + " " + current);
        max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
        current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        output("STREAM_MUSIC max current=" + max + " " + current);
        max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_ALARM );
        current = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        output("STREAM_ALARM max current=" + max + " " + current);
        output("----------------------------");
        TelephonyManager tm = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
        output("phone device id(imei)=" + tm.getDeviceId());
        output("phone line1 number(msisdn)=" + tm.getLine1Number());
        output("phone network operator=" + tm.getNetworkOperator());
        output("phone network country iso=" + tm.getNetworkCountryIso());
        output("phone network operator name=" + tm.getNetworkOperatorName());
        output("sim operator=" + tm.getSimOperator());
        output("sim OperatorName=" + tm.getSimOperatorName());
        output("sim serial number=" + tm.getSimSerialNumber());
        output("sim country iso=" + tm.getSimCountryIso());
        output("phone subscriberid=" + tm.getSubscriberId());
        output("----------------------------");
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        output("wifi ip=" + wifiInfo.getIpAddress());
        output("wifi mac=" + wifiInfo.getMacAddress());
        output("wifi bssid=" + wifiInfo.getBSSID());
        output("wifi ssid=" + wifiInfo.getSSID());
        if (Build.VERSION.SDK_INT >= 21) {
            output("wifi frequency=" + wifiInfo.getFrequency());
        }
        output("wifi link speed=" + wifiInfo.getLinkSpeed());
        output("wifi networkid=" + wifiInfo.getNetworkId());
        output("wifi rssi=" + wifiInfo.getRssi());
        output("----------------------------");
        System.setProperty("cheng", "qi");
        Properties p = System.getProperties();
        ss = p.toString();
        ss = ss.replace('{', ' ');
        ss = ss.replace('}', ' ');
        ss = ss.replace(',', '\n');
        output("properties=");
        output(ss);
        output("----------------------------");
        output("SystemClock.currentThreadTimeMillis=" + SystemClock.currentThreadTimeMillis());
        output("SystemClock.elapsedRealtime=" + SystemClock.elapsedRealtime());
        output("SystemClock.uptimeMillis()=" + SystemClock.uptimeMillis());
        output("----------------------------");
        output("Locale getCountry=" + Locale.getDefault().getCountry());
        output("Locale getDisplayCountry=" + Locale.getDefault().getDisplayCountry());
        output("Locale getLanguage=" + Locale.getDefault().getLanguage());
        output("Locale getDisplayLanguage=" + Locale.getDefault().getDisplayLanguage());
        output("Locale getDisplayName=" + Locale.getDefault().getDisplayName());
        output("----------------------------");
        File f0 = getFilesDir();
        File f1 = getCacheDir();
        File f2 = getExternalFilesDir(null);
        File f3 = getExternalCacheDir();
        File f4 = Environment.getDataDirectory();
        File f5 = Environment.getDownloadCacheDirectory();
        File f6 = Environment.getExternalStorageDirectory();
        File f7 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File f8 = Environment.getRootDirectory();

        output("getFilesDir()=" + f0.getPath());
        output("getCacheDir()=" + f1.getPath());
        output("getExternalFilesDir(null)=" + f2.getPath());
        output("getExternalCacheDir()=" + f3.getPath());
        output("Environment.getDataDirectory()=" + f4.getPath());
        output("Environment.getDownloadCacheDirectory()=" + f5.getPath());
        output("Environment.getExternalStorageDirectory()=" + f6.getPath());
        output("Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);=" + f7.getPath());
        output("Environment.getRootDirectory()=" + f8.getPath());
        output("----------------------------");
    }
}
