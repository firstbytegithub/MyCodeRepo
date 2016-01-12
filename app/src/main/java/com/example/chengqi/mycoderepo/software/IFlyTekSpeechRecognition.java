package com.example.chengqi.mycoderepo.software;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chengqi.mycoderepo.R;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

//import com.iflytek.speech.util.JsonParser;

public class IFlyTekSpeechRecognition extends Activity {
    private static final String TAG = "xxx";
    private Button mStart;
    private TextView mLog;

    private SpeechRecognizer mIat;
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private HashMap<String, String> txt2audio = new HashMap<>();
    private static final String unknown_audio = "unknown.aac";
    private static final String error_audio = "error.aac";

    private AudioManager mAudioManager;

    private boolean btAudio = false;
    private boolean bTTS = true;
    private SpeechSynthesizer mTts;

    private boolean mIsPhoneInterrupt = false;

    private static final int STATE_IDLE = 0;
    private static final int STATE_RECORD = 1;
    private static final int STATE_INCALL = 2;
    private int mCurState = STATE_IDLE;


    private LexiconListener mLexiconListener = new LexiconListener() {

        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
                output(error.toString());
            } else {
                output("热词上传成功");
            }
        }
    };

    private RecognizerListener mRecoListener = new RecognizerListener() {

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
//            output("volume=" + i);
        }

        @Override
        public void onBeginOfSpeech() {
            output("onBeginOfSpeech");
        }

        @Override
        public void onEndOfSpeech() {
            output("onEndOfSpeech");
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean isLast) {
            boolean found = false;
            String result = recognizerResult.getResultString();

            output("isLast=" + isLast + ", got cmd = " + result);
            if (result.length() == 0) {
                output("0 length");
                return;
            }

            Set<String> set = txt2audio.keySet();
            for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
                String txt = iter.next();
                if (result.equals(txt)) {
                    found = true;
                    break;
                }
            }

            stopRecording();

            if (found) {
                if (bTTS) {
                    tts(txt2audio.get(result));
                } else {
                    play(txt2audio.get(result));
                }
            } else {
                if (bTTS) {
                    tts(txt2audio.get("unknown"));
                } else {
                    play(unknown_audio);
                }
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            output("error=" + speechError.getPlainDescription(true));
//            mIat.startListening(mRecoListener);
            stopRecording();
            play(error_audio);
//            startRecording();
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            output("onEvent");
        }
    };

    private String getStateTip(int state) {
        switch (state) {
            case AudioManager.SCO_AUDIO_STATE_CONNECTED:
                return "CONNECTED";
            case AudioManager.SCO_AUDIO_STATE_DISCONNECTED:
                return "DISCONNECTED";
            case AudioManager.SCO_AUDIO_STATE_CONNECTING:
                return "CONNECTING";
            case AudioManager.SCO_AUDIO_STATE_ERROR:
                return "ERROR";
        }

        return null;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED.equals(action)) {
                int state = intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1);
                int preState = intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_PREVIOUS_STATE, -1);
                output("state from " + getStateTip(preState) + " to " + getStateTip(state));
                if (state == AudioManager.SCO_AUDIO_STATE_CONNECTED) {
                    output("sco connected");
                    if (mCurState == STATE_INCALL) {
                        Log.d(TAG, "in call, destroy all");
                        stopRecording();
                    } else {
                        mAudioManager.setBluetoothScoOn(true);
                        startRecognizer();
                    }
                } else if (state == AudioManager.SCO_AUDIO_STATE_DISCONNECTED) {
                    output("sco disconnected");
                    if (preState == AudioManager.SCO_AUDIO_STATE_CONNECTING) {
                        if (mCurState == STATE_INCALL) {
                            Log.d(TAG, "in call, don't restart all");
                        } else if (mCurState == STATE_IDLE) {
                            Log.d(TAG, "idle state");
                        } else {
                            Log.d(TAG, "restart all");
                            stopRecording();
                            startRecording();
                        }
                    }
                } else if (state == AudioManager.SCO_AUDIO_STATE_CONNECTING) {
                    output("sco connecting");
                } else if (state == AudioManager.SCO_AUDIO_STATE_ERROR) {
                    output("sco error");
                }
            } else if (Intent.ACTION_MEDIA_BUTTON.equals(action)) {
                KeyEvent key=(KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                int keycode=key.getKeyCode();
                output("keycode=" + keycode);
                if(keycode==KeyEvent.KEYCODE_VOLUME_UP) {
                    output("KEYCODE_VOLUME_UP");
                }
                else if (keycode==KeyEvent.KEYCODE_VOLUME_DOWN) {
                    output("KEYCODE_VOLUME_DOWN");
                }
                else if (keycode==KeyEvent.KEYCODE_HEADSETHOOK) {
                    output("KEYCODE_HEADSETHOOK");
                }
            }
        }
    };

//    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
//        @Override
//        public void onAudioFocusChange(int focusChange) {
//            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
//                Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
//            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
//                Log.d(TAG, "AUDIOFOCUS_LOSS");
//            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
//                Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
//            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
//                Log.d(TAG, "AUDIOFOCUS_GAIN");
//            }
//        }
//    };

    class OnePhoneStateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.i(TAG, "[Listener]电话号码:"+incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i(TAG, "[Listener]等待接电话:" + incomingNumber);
                    if (mCurState == STATE_RECORD) {
                        mCurState = STATE_INCALL;
                        stopRecording();
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i(TAG, "[Listener]电话挂断:"+incomingNumber);
                    if (mCurState == STATE_INCALL) {
                        mCurState = STATE_RECORD;
                        startRecording();
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i(TAG, "[Listener]通话中:"+incomingNumber);
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ifly_tek_speech_recognition);

        TelephonyManager telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new OnePhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);

        if (bTTS) {
            txt2audio.put("零级", "零级加一");
            txt2audio.put("一级", "一级加一");
            txt2audio.put("二级", "二级加一");
            txt2audio.put("三级", "三级加一");
            txt2audio.put("四级", "四级加一");
            txt2audio.put("五级", "五级加一");
            txt2audio.put("六级", "六级加一");
            txt2audio.put("七级", "七级加一");
            txt2audio.put("八级", "八级加一");
            txt2audio.put("九级", "九级加一");
            txt2audio.put("十级", "十级加一");
            txt2audio.put("回退", "已回退至");
            txt2audio.put("下一行", "已切换至下一行");
            txt2audio.put("暂停采集", "采集已暂停");
            txt2audio.put("继续采集", "采集已继续");
            txt2audio.put("结束采集", "采集已结束");
            txt2audio.put("unknown", "无法识别");
            txt2audio.put("error", "出现错误，请重试");
        } else {
            txt2audio.put("零级", "0.aac");
            txt2audio.put("一级", "1.aac");
            txt2audio.put("二级", "2.aac");
            txt2audio.put("三级", "3.aac");
            txt2audio.put("四级", "4.aac");
            txt2audio.put("五级", "5.aac");
            txt2audio.put("六级", "6.aac");
            txt2audio.put("七级", "7.aac");
            txt2audio.put("八级", "8.aac");
            txt2audio.put("九级", "9.aac");
            txt2audio.put("十级", "10.aac");
            txt2audio.put("回退", "huitui.aac");
            txt2audio.put("下一行", "xiayihang.aac");
            txt2audio.put("暂停采集", "pause.aac");
            txt2audio.put("继续采集", "start.aac");
            txt2audio.put("结束采集", "end.aac");
            txt2audio.put("unknown", "unknown.aac");
            txt2audio.put("error", "error.aac");
        }

        mStart = (Button) findViewById(R.id.button89);
        mLog = (TextView) findViewById(R.id.textView40);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=567d6258");

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        registerReceiver(mReceiver, new IntentFilter(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED));
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_MEDIA_BUTTON));

        startTTS();

        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            if (mAudioManager.isBluetoothScoAvailableOffCall()) {
                output("in bt audio mode");
                btAudio = true;
            } else {
                output("sorry, your device don't support off call sco");
                btAudio = false;
            }
        } else {
            output("in mic audio mode");
            btAudio = false;
        }

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurState = STATE_RECORD;
                startRecording();
            }
        });
    }

    private void startRecording() {
        Log.d(TAG, "startRecording");
        if (btAudio) {
            startBluetoothSCO();
        } else {
            startRecognizer();
        }
    }

    private void stopRecording() {
        Log.d(TAG, "stopRecording");
        stopRecognizer();
        if (btAudio) {
            stopBluetoothSCO();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTTS();
        stopRecording();
        mCurState = STATE_IDLE;
        unregisterReceiver(mReceiver);
    }

//    private void requestAudioFocus() {
//        int ret = mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE);
//        if (ret == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
//            Log.d(TAG, "AUDIOFOCUS_REQUEST_FAILED");
//        } else if (ret == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//            Log.d(TAG, "AUDIOFOCUS_REQUEST_GRANTED");
//        }
//    }
//
//    private void abandonAudioFocus() {
//        mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
//    }

    private void startBluetoothSCO() {
        Log.d(TAG, "startBluetoothSCO +");
        mAudioManager.startBluetoothSco();
        Log.d(TAG, "startBluetoothSCO -");
    }

    private void stopBluetoothSCO() {
        Log.d(TAG, "stopBluetoothSCO +");
        if (mAudioManager.isBluetoothScoOn()) {
            mAudioManager.setBluetoothScoOn(false);
        }
        mAudioManager.stopBluetoothSco();
        Log.d(TAG, "stopBluetoothSCO -");
    }

    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            output("onSpeakBegin");
        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {
            output("onBufferProgress");
        }

        @Override
        public void onSpeakPaused() {
            output("onSpeakPaused");
        }

        @Override
        public void onSpeakResumed() {
            output("onSpeakResumed");
        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {
            output("onSpeakProgress");
        }

        @Override
        public void onCompleted(SpeechError speechError) {
            output("onSpeakProgress");
            if (btAudio) {
                startBluetoothSCO();
            } else {
                startRecognizer();
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            output("onSpeakProgress");
        }
    };

    private void startTTS() {
        output("startTTS");
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量,范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
    }

    private void stopTTS() {
        output("stopTTS");
        if (mTts != null) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
            mTts = null;
        }
    }

    private void tts(String s) {
        output("tts " + s);
        if (mTts != null) {
            mTts.startSpeaking(s, mTtsListener);
        }
    }

    private void play(String sound) {
        AssetManager am = getAssets();
        AssetFileDescriptor afd = null;

        try {
            afd = am.openFd(sound);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
            output("play tip");
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    output("play stopped");
                    mp.release();
                    startRecording();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startRecognizer() {
        mIat = SpeechRecognizer.createRecognizer(this, null);
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        setParam();
        mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "6");
        String ss = mIat.getParameter(SpeechConstant.AUDIO_SOURCE);
        output("audio source=" + ss);
        mIat.startListening(mRecoListener);
    }

    private void stopRecognizer() {
        if (mIat != null) {
            mIat.stopListening();
            mIat.cancel();
            mIat.destroy();
            mIat = null;
        }
//        stopTTS();
    }

    public void setParam() {
        int ret = 0;
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "plain");

//        String lag = mSharedPreferences.getString("iat_language_preference",
//                "mandarin");
        String lag = "zh_cn";
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "10000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");

        // 设置听写结果是否结果动态修正，为“1”则在听写过程中动态递增地返回结果，否则只在听写结束之后返回最终结果
        // 注：该参数暂时只对在线听写有效
        mIat.setParameter(SpeechConstant.ASR_DWA, "0");

        // 指定引擎类型
//        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        String contents = FucUtil.readFile(this, "userwords","utf-8");
        ret = mIat.updateLexicon("userword", contents, mLexiconListener);
        if (ret != ErrorCode.SUCCESS)
            output("上传热词失败,错误码：" + ret);
    }

    private void output(String out) {
        String s = mLog.getText().toString();
        s = s + "\n" + out;
        mLog.setText(s);
        Log.d(TAG, out);
    }
}
