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

public class HWSensorsActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {
    final private String LOG_TAG = "HWSensorsActivity";
    private ListView mListView;

//    1)加速度
//    2)磁力
//    3)方向
//    4)陀螺仪
//    5)光线
//    6)压力
//    7)温度
//    8)接近
//    9)重力
//    10)线性加速度
//    11)旋转矢量
//    12)相对湿度
//    13)环境温度
//    14)未校准磁力
//    15)未校准旋转矢量
//    16)未校准陀螺仪
//    17)运动传感器
//    18)步伐检测器
//    19)步伐计数器
//    20)地磁旋转矢量
//    21)心率计
//    22)倾斜检测
//    23)唤醒手势
//    24)glance手势
//    25)pick up手势
//    26)手腕倾斜手势
    final private String[] strItems = new String[] {
            "Accelerometer",
            "MagneticField",
            "Orientation",
            "Gyroscope",
            "Light",
            "Pressure",
            "Temperature",
            "Proximity",
            "Gravity",
            "LinearAcceleration",
            "RotationVector",
            "RelativeHumidity",
            "AmbientTemperature",
            "UncalibratedMagneticField",
            "UncalibratedRotationVector",
            "UncalibratedGyroscope",
            "SignificantMotion",
            "StepDetector",
            "StepCounter",
            "GeoMagneticRotationVector",
            "HeartRate",
            "TiltDetector",
            "WakeGesture",
            "GlanceGesture",
            "PickUp",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwsensors);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_sensor);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, AccelerometerActivity.class);
                break;
            case 1:
                intent = new Intent(this, MagneticFieldActivity.class);
                break;
            case 2:
                intent = new Intent(this, OrientationActivity.class);
                break;
            case 3:
                intent = new Intent(this, GyroscopeActivity.class);
                break;
            case 4:
                intent = new Intent(this, LightActivity.class);
                break;
            case 5:
                intent = new Intent(this, PressureActivity.class);
                break;
            case 6:
                intent = new Intent(this, TemperatureActivity.class);
                break;
            case 7:
                intent = new Intent(this, ProximityActivity.class);
                break;
            case 8:
                intent = new Intent(this, GravityActivity.class);
                break;
            case 9:
                intent = new Intent(this, LinearAccelerationActivity.class);
                break;
            case 10:
                intent = new Intent(this, RotationVectorActivity.class);
                break;
            case 11:
                intent = new Intent(this, RelativeHumidityActivity.class);
                break;
            case 12:
                intent = new Intent(this, AmbientTemperatureActivity.class);
                break;
            case 13:
                intent = new Intent(this, UncalibratedMagneticFieldActivity.class);
                break;
            case 14:
                intent = new Intent(this, UncalibratedRotationVectorActivity.class);
                break;
            case 15:
                intent = new Intent(this, UncalibratedGyroscopeActivity.class);
                break;
            case 16:
                intent = new Intent(this, SignificantMotionActivity.class);
                break;
            case 17:
                intent = new Intent(this, StepDetectorActivity.class);
                break;
            case 18:
                intent = new Intent(this, StepCounterActivity.class);
                break;
            case 19:
                intent = new Intent(this, GeoMagneticRotationVectorActivity.class);
                break;
            case 20:
                intent = new Intent(this, HeartRateActivity.class);
                break;
            case 21:
                intent = new Intent(this, TiltDetectorActivity.class);
                break;
            case 22:
                intent = new Intent(this, WakeGestureActivity.class);
                break;
            case 23:
                intent = new Intent(this, GlanceGestureActivity.class);
                break;
            case 24:
                intent = new Intent(this, PickUpActivity.class);
                break;
            case 25:
//                手腕倾斜手势 Android L
//                intent = new Intent(this, significant motionActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
