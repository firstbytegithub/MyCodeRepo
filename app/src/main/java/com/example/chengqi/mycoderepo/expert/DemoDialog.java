package com.example.chengqi.mycoderepo.expert;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chengqi.mycoderepo.R;

public class DemoDialog extends AppCompatActivity {
    private static final String TAG = "DemoDialog";
    Button mDialog1;
    Button mDialog2;
    Button mDialog3;
    Button mDialog4;
    Button mDialog5;
    Button mDialog6;
    Button mDialog7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_dialog);

        mDialog1 = (Button) findViewById(R.id.button11);
        mDialog2 = (Button) findViewById(R.id.button12);
        mDialog3 = (Button) findViewById(R.id.button13);
        mDialog4 = (Button) findViewById(R.id.button14);
        mDialog5 = (Button) findViewById(R.id.button15);
        mDialog6 = (Button) findViewById(R.id.button16);
        mDialog7 = (Button) findViewById(R.id.button17);

        mDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DemoDialog.this);
                builder.setIcon(R.drawable.bdspeech_help_deep)
                        .setTitle("this is title")
                        .setMessage("this is message")
                        .setCancelable(false)
                        .setPositiveButton("positive", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "onClick positive");
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("negative", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "onClick negative");
                            }
                        })
                        .setNeutralButton("neutral1", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "onClick neutral1");
                            }
                        })
                        .setNeutralButton("neutral2", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "onClick neutral2");
                            }
                        });
                builder.show();
            }
        });
        mDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DemoDialog.this);
                builder.setView(new EditText(DemoDialog.this)
                ).setPositiveButton("OK", null).setNegativeButton("cancel", null);
                builder.show();
            }
        });
        mDialog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(DemoDialog.this);
//                builder.setIcon(R.drawable.bdspeech_help_deep).setTitle("title2").setView(R.layout.fragment02)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                builder.show();
                LayoutInflater inflater = getLayoutInflater();
                View vv = inflater.inflate(R.layout.fragment02, null);
                final EditText tt = (EditText) vv.findViewById(R.id.editText2);
                tt.setText("change me");

                AlertDialog.Builder builder = new AlertDialog.Builder(DemoDialog.this);
                builder.setIcon(R.drawable.bdspeech_help_deep).setTitle("title2").setView(vv)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, tt.getText().toString());
                            }
                        });
                builder.show();
            }
        });
        mDialog4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DemoDialog.this);
                builder.setIcon(R.drawable.bdspeech_help_deep).setTitle("title2")
                        .setSingleChoiceItems(new String[]{"111", "222", "333"}, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "click " + which);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });
        mDialog5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DemoDialog.this);
                builder.setIcon(R.drawable.bdspeech_help_deep).setTitle("title2")
                        .setMultiChoiceItems(new String[]{"111", "222", "333"}, new boolean[]{true, true, false}, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Log.d(TAG, which + " " + isChecked);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });
        mDialog6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DemoDialog.this);
                builder.setIcon(R.drawable.bdspeech_help_deep)
                        .setTitle("tttitle")
                        .setItems(new String[]{"aaa", "bbb", "ccc"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "select " + which);
                        dialog.dismiss();
                    }
                });
                Log.d(TAG, "before show");
                builder.show();
                Log.d(TAG, "after show");
            }
        });
        mDialog7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dlg = new ProgressDialog(DemoDialog.this);
                dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                dlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dlg.setMessage("loading");
                dlg.setCancelable(true);
                Log.d(TAG, "before show");
                dlg.show();
                Log.d(TAG, "after show");
            }
        });
    }
}
