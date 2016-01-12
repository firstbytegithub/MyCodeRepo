package com.example.chengqi.mycoderepo.hardware;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.ParcelUuid;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chengqi.mycoderepo.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class HWBluetoothActivity extends Activity {
    private static final String LOG_TAG = "DemoBT";
    private static final int REQUEST_ENABLE_BT = 1;
    private Button mEnableBT;
    private Button mButtonGetPaired;
    private Button mButtonDiscovery;
    private Button mButtonPair;
    private Button mButtonClear;
    private Button mButtonConnect;
    private Button mButtonRecord;
    private Button mButtonStop;
    private Button mButtonPlay;
    private TextView mLogView;
    private String mLog = "\n";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mHfDevice = null;
    private ArrayList<BluetoothDevice> mBluetoothDevices = new ArrayList<BluetoothDevice>();
    private ClientThread mClientThread;
    private AudioManager mAudioManager;
    private MediaRecorder recorder;

    private static final String  ServiceDiscoveryServerServiceClassID_UUID = "00001000-0000-1000-8000-00805F9B34FB";
    private static final String  BrowseGroupDescriptorServiceClassID_UUID = "00001001-0000-1000-8000-00805F9B34FB";
    private static final String  PublicBrowseGroupServiceClass_UUID = "00001002-0000-1000-8000-00805F9B34FB";

    //蓝牙串口服务
    private static final String  SerialPortServiceClass_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private static final String  LANAccessUsingPPPServiceClass_UUID = "00001102-0000-1000-8000-00805F9B34FB";
    //拨号网络服务
    private static final String  DialupNetworkingServiceClass_UUID = "00001103-0000-1000-8000-00805F9B34FB";
    //信息同步服务
    private static final String  IrMCSyncServiceClass_UUID = "00001104-0000-1000-8000-00805F9B34FB";
    private static final String  SDP_OBEXObjectPushServiceClass_UUID = "00001105-0000-1000-8000-00805F9B34FB";
    //文件传输服务
    private static final String  OBEXFileTransferServiceClass_UUID = "00001106-0000-1000-8000-00805F9B34FB";
    private static final String  IrMCSyncCommandServiceClass_UUID = "00001107-0000-1000-8000-00805F9B34FB";
    private static final String  SDP_HeadsetServiceClass_UUID = "00001108-0000-1000-8000-00805F9B34FB";
    private static final String  CordlessTelephonyServiceClass_UUID = "00001109-0000-1000-8000-00805F9B34FB";
    private static final String  SDP_AudioSourceServiceClass_UUID = "0000110A-0000-1000-8000-00805F9B34FB";
    private static final String  SDP_AudioSinkServiceClass_UUID = "0000110B-0000-1000-8000-00805F9B34FB";
    private static final String  SDP_AVRemoteControlTargetServiceClass_UUID = "0000110C-0000-1000-8000-00805F9B34FB";
    private static final String  SDP_AdvancedAudioDistributionServiceClass_UUID = "0000110D-0000-1000-8000-00805F9B34FB";
    private static final String  SDP_AVRemoteControlServiceClass_UUID = "0000110E-0000-1000-8000-00805F9B34FB";
    private static final String  VideoConferencingServiceClass_UUID = "0000110F-0000-1000-8000-00805F9B34FB";
    private static final String  IntercomServiceClass_UUID = "00001110-0000-1000-8000-00805F9B34FB";
    //蓝牙传真服务
    private static final String  FaxServiceClass_UUID = "00001111-0000-1000-8000-00805F9B34FB";
    private static final String  HeadsetAudioGatewayServiceClass_UUID = "00001112-0000-1000-8000-00805F9B34FB";
    private static final String  WAPServiceClass_UUID = "00001113-0000-1000-8000-00805F9B34FB";
    private static final String  WAPClientServiceClass_UUID = "00001114-0000-1000-8000-00805F9B34FB";
    //个人局域网服务
    private static final String  PANUServiceClass_UUID = "00001115-0000-1000-8000-00805F9B34FB";
    //个人局域网服务
    private static final String  NAPServiceClass_UUID = "00001116-0000-1000-8000-00805F9B34FB";

    private static final String BluetoothPhoneBoookAccess_UUID = "0000112F-0000-1000-8000-00805F9B34FB";
    private static final String HandsfreeServiceClass_UUID = "0000111E-0000-1000-8000-00805F9B34FB";
    private static final String HandsfreeAudioGatewayServiceClass_UUID = "0000111F-0000-1000-8000-00805F9B34FB";
    private static final String BASE_UUID = "00000000-0000-1000-8000-00805F9B34FB";

    private static final String[] StandardUUID = {
            ServiceDiscoveryServerServiceClassID_UUID,
            BrowseGroupDescriptorServiceClassID_UUID,
            PublicBrowseGroupServiceClass_UUID,
            SerialPortServiceClass_UUID,
            LANAccessUsingPPPServiceClass_UUID,
            DialupNetworkingServiceClass_UUID,
            IrMCSyncServiceClass_UUID,
            SDP_OBEXObjectPushServiceClass_UUID,
            OBEXFileTransferServiceClass_UUID,
            IrMCSyncCommandServiceClass_UUID,
            SDP_HeadsetServiceClass_UUID,
            CordlessTelephonyServiceClass_UUID,
            SDP_AudioSourceServiceClass_UUID,
            SDP_AudioSinkServiceClass_UUID,
            SDP_AVRemoteControlTargetServiceClass_UUID,
            SDP_AdvancedAudioDistributionServiceClass_UUID,
            SDP_AVRemoteControlServiceClass_UUID,
            VideoConferencingServiceClass_UUID,
            IntercomServiceClass_UUID,
            FaxServiceClass_UUID,
            HeadsetAudioGatewayServiceClass_UUID,
            WAPServiceClass_UUID,
            WAPClientServiceClass_UUID,
            PANUServiceClass_UUID,
            NAPServiceClass_UUID,
            BluetoothPhoneBoookAccess_UUID,
            HandsfreeServiceClass_UUID,
            HandsfreeAudioGatewayServiceClass_UUID,
            BASE_UUID
    };

    private static final String[] StandardUUIDStr = {
            "ServiceDiscoveryServerServiceClassID_UUID",
            "BrowseGroupDescriptorServiceClassID_UUID",
            "PublicBrowseGroupServiceClass_UUID",
            "SerialPortServiceClass_UUID",
            "LANAccessUsingPPPServiceClass_UUID",
            "DialupNetworkingServiceClass_UUID",
            "IrMCSyncServiceClass_UUID",
            "SDP_OBEXObjectPushServiceClass_UUID",
            "OBEXFileTransferServiceClass_UUID",
            "IrMCSyncCommandServiceClass_UUID",
            "SDP_HeadsetServiceClass_UUID",
            "CordlessTelephonyServiceClass_UUID",
            "SDP_AudioSourceServiceClass_UUID",
            "SDP_AudioSinkServiceClass_UUID",
            "SDP_AVRemoteControlTargetServiceClass_UUID",
            "SDP_AdvancedAudioDistributionServiceClass_UUID",
            "SDP_AVRemoteControlServiceClass_UUID",
            "VideoConferencingServiceClass_UUID",
            "IntercomServiceClass_UUID",
            "FaxServiceClass_UUID",
            "HeadsetAudioGatewayServiceClass_UUID",
            "WAPServiceClass_UUID",
            "WAPClientServiceClass_UUID",
            "PANUServiceClass_UUID",
            "NAPServiceClass_UUID",
            "BluetoothPhoneBoookAccess_UUID",
            "HandsfreeServiceClass_UUID",
            "HandsfreeAudioGatewayServiceClass_UUID",
            "BASE_UUID",
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBluetoothDevices.add(device);
                output("[DISCOVERY]" + device.getName() + " " + device.getAddress());
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                if (bondState == BluetoothDevice.BOND_BONDED) {
                    output("[BOND]" + device.getName() + " BOND_BONDED");
//                    ParcelUuid[] supportedUUIDs = device.getUuids();
//                    if (supportedUUIDs != null) {
//                        for (ParcelUuid u : supportedUUIDs) {
//                            output("--[UUID]" + u.getUuid().toString());
//                        }
//                    } else {
//                        output("--[UUID] not found");
//                    }
                } else if (bondState == BluetoothDevice.BOND_NONE) {
                    output("[BOND]" + device.getName() + " BOND_NONE");
                } else if (bondState == BluetoothDevice.BOND_BONDING) {
                    output("[BOND]" + device.getName() + " BOND_BONDING");
                }
            } else if (AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED.equals(action)) {
                int state = intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1);
                if (state == AudioManager.SCO_AUDIO_STATE_CONNECTED) {
                    output("sco connected");
                    mAudioManager.setBluetoothScoOn(true);
                    mAudioManager.setMode(AudioManager.MODE_IN_CALL);
                    recorder.start();
//                    unregisterReceiver(this);
                } else if (state == AudioManager.SCO_AUDIO_STATE_DISCONNECTED) {
                    output("sco disconnected");
                } else if (state == AudioManager.SCO_AUDIO_STATE_CONNECTING) {
                    output("sco connecting");
                } else if (state == AudioManager.SCO_AUDIO_STATE_ERROR) {
                    output("sco error");
                }
            }
        }
    };

    private void output(String s) {
        mLog = mLog + s + '\n';
        mLogView.setText(mLog);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                output("enable bt return ok");
            } else if (resultCode == RESULT_CANCELED) {
                output("enable bt return canceled");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hwbluetooth);
        mEnableBT = (Button) findViewById(R.id.id_enablebt);
        mButtonGetPaired = (Button) findViewById(R.id.buttonGetPaired);
        mLogView = (TextView) findViewById(R.id.textView5);
        mButtonDiscovery = (Button) findViewById(R.id.buttonDiscovery);
        mButtonPair = (Button) findViewById(R.id.buttonPair);
        mButtonClear = (Button) findViewById(R.id.buttonClear);
        mButtonConnect = (Button) findViewById(R.id.buttonConnect);
        mButtonRecord = (Button) findViewById(R.id.buttonRecord);
        mButtonStop = (Button) findViewById(R.id.buttonStop);
        mButtonPlay = (Button) findViewById(R.id.buttonPlay);

        mEnableBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableBluetooth();
            }
        });
        mButtonGetPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPairedDevices();
            }
        });
        mButtonDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDiscovery();
            }
        });
        mButtonPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPair();
            }
        });
        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLog = "";
                mLogView.setText(mLog);
            }
        });
        mButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToDevice();
            }
        });
        mButtonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record();
            }
        });
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
        registerReceiver(mReceiver, new IntentFilter(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED));
//        registerReceiver(mReceiver, new IntentFilter(BluetoothDevice.ACTION_PAIRING_CANCEL));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void enableBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            output("there is no bt adapter in the device");
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            output("start enabling ...");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            output("already enabled");
        }
    }

    private void getPairedDevices() {
        if (mBluetoothAdapter == null) {
            output("there is no bt adapter in the device");
            return;
        }

        mHfDevice = null;

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                output("[PAIRED]" + device.getName() + " " + device.getAddress());
                if (device.getAddress().equals("1C:48:F9:B1:C1:9F")) {
                    mHfDevice = device;
                }
                ParcelUuid[] supportedUUIDs = device.getUuids();
                if (supportedUUIDs != null) {
                    for (ParcelUuid u : supportedUUIDs) {
                        String uuidstr = u.getUuid().toString().toUpperCase();
//                        Log.d(LOG_TAG, "uuid-" + uuidstr);
                        int i = 0;
                        for (String s : StandardUUID) {
//                            Log.d(LOG_TAG, "["+s+"]"+"["+uuidstr+"]");
                            if (s.equals(uuidstr)) {
                                output("--[UUID]" + StandardUUIDStr[i]);
                                break;
                            } else {
                                if (i == StandardUUID.length-1) {
                                    output("--[UUID]" + uuidstr);
                                    Log.d(LOG_TAG, "uuid-" + uuidstr);
                                }
                            }
                            ++i;
                        }
                    }
                } else {
                    output("--[UUID] not found");
                }

                BluetoothClass btclass = device.getBluetoothClass();
                Log.d(LOG_TAG, btclass.toString());
            }
        } else {
            output("no paired devices");
        }
    }

    private void startDiscovery() {
        if (mBluetoothAdapter == null) {
            output("there is no bt adapter in the device");
            return;
        }
        output("start discovery");
        mBluetoothDevices.clear();
        mBluetoothAdapter.startDiscovery();
    }

    private void startPair() {
        if (mBluetoothDevices.size() > 0) {
            for (BluetoothDevice d : mBluetoothDevices) {
                if (d.getName().equals("GT-N7102")) {
                    d.createBond();
                }
            }
        }
    }

    private class ClientThread extends Thread {
        private BluetoothSocket mSocket = null;
        private BluetoothDevice mDevice = null;

        public ClientThread(BluetoothDevice device) {
            mDevice = device;
            if (mDevice != null) {
                output("connect to " + mDevice.getName());
                try {
                    BluetoothSocket socketClient = mDevice.createRfcommSocketToServiceRecord(UUID.fromString(SerialPortServiceClass_UUID));
                    if (socketClient == null) {
                        output("can not get client socket");
                        return;
                    }
                    mSocket = socketClient;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void run() {
            if (mSocket == null) {
                Log.d(LOG_TAG, "mSocket == null");
                return;
            }

            mBluetoothAdapter.cancelDiscovery();
            Log.d(LOG_TAG, "connecting...");
            try {
                mSocket.connect();
            } catch (IOException e) {
                try {
                    mSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Log.d(LOG_TAG, "connect error");
                return;
            }

            InputStream inputStream;
            OutputStream outputStream;

            try {
                inputStream = mSocket.getInputStream();
                outputStream = mSocket.getOutputStream();
            } catch (IOException e) {
                Log.d(LOG_TAG, "can not get input/output");
                return;
            }

            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    Log.d(LOG_TAG, "reading...");
                    bytes = inputStream.read(buffer);
                    Log.d(LOG_TAG, bytes + "bytes");
                    Log.d(LOG_TAG, "[" + String.valueOf(buffer).substring(0, bytes-1)+"]");
//                    String s = String.
                    //output("[READ]" + );
                } catch (IOException e) {

                }
            }
        }
    }

    private void connectToDevice() {
        mClientThread = new ClientThread(mHfDevice);
        mClientThread.start();
    }

    private void record() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path += "/bt.3gp";
        output("record to " + path);
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(path);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            output("record prepare failed");
            e.printStackTrace();
            return;
        }

        if (mAudioManager.isBluetoothScoAvailableOffCall()) {
            output("good luck! your device support off call sco");
        } else {
            output("fuck, your device don't support off call sco");
            return;
        }

        mAudioManager.startBluetoothSco();
    }

    private void stop() {
        recorder.stop();
        recorder.release();
        recorder = null;
        if (mAudioManager.isBluetoothScoOn()) {
            mAudioManager.setBluetoothScoOn(false);
            mAudioManager.stopBluetoothSco();
        }
    }

    private void play() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path += "/bt.3gp";
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("/system/media/audio/notifications/Sage.flac");
            mediaPlayer.prepare();
            mediaPlayer.start();
            output("start playing");
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    output("play stopped");
                    mp.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
