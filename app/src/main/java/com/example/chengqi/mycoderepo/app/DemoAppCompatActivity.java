package com.example.chengqi.mycoderepo.app;

//import android.app.ActionBar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.chengqi.mycoderepo.R;

import android.support.v7.app.ActionBar;

/**
 * Created by xuweiwei on 15/12/8.
 */
public class DemoAppCompatActivity extends AppCompatActivity
        implements Button.OnClickListener {

    private static final String LOG_TAG = "DemoAppCompatActivity";

    private Button btnShowActionBar;
    private Button btnShowActions;
    private Button btnShowTitle;
    private Button btnShowSubtitle;
    private Button btnShowCustomView;
    private Button btnShowHome;
    private Button btnShowBack;

    private ActionBar actionBar;

    private boolean bShowActionBar = true;
    private boolean bShowActions = true;
    private boolean bShowTitle = true;
    private boolean bShowSubtitle = false;
    private boolean bShowCustomView = false;
    private boolean bShowHome = false;
    private boolean bShowBack = false;

    private String strTitle;

    @Override
    protected void onRestart() {
        Log.d(LOG_TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        boolean b = getWindow().hasFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        if (b)
            Log.d("LOG_TAG", "has feature");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_app_demoappcompatactivity);

        actionBar = getSupportActionBar();
        strTitle = (String) actionBar.getTitle();

        btnShowActionBar = (Button) findViewById(R.id.button9);
        btnShowActions = (Button) findViewById(R.id.button10);
        btnShowTitle = (Button) findViewById(R.id.button11);
        btnShowSubtitle = (Button) findViewById(R.id.button12);
        btnShowCustomView = (Button) findViewById(R.id.button13);
        btnShowHome = (Button) findViewById(R.id.button15);
        btnShowBack = (Button) findViewById(R.id.button16);

        btnShowActionBar.setOnClickListener(this);
        btnShowActions.setOnClickListener(this);
        btnShowTitle.setOnClickListener(this);
        btnShowSubtitle.setOnClickListener(this);
        btnShowCustomView.setOnClickListener(this);
        btnShowHome.setOnClickListener(this);
        btnShowBack.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
//        onCreateOptionsMenu()只会被调用一次。若想改变Menu，可以调用invalidateOptionsMenu()，这会使onCreateOptionsMenu()将会再次被调用
        if (bShowActions) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_app_demoappcompatactivity, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "id home");
            case R.id.action_01:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_02:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_03:
//                getWindow().
//                        getDecorView().
//                        setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "onClick");
        switch (v.getId()) {
            case R.id.button9:
                if (bShowActionBar) {
                    bShowActionBar = false;
                    btnShowActionBar.setText("show action bar");
                    actionBar.hide();
                } else {
                    bShowActionBar = true;
                    btnShowActionBar.setText("hide action bar");
                    actionBar.show();
                }
                break;
            case R.id.button10:
                if (bShowActions) {
                    bShowActions = false;
                    btnShowActions.setText("show actions");
                } else {
                    bShowActions = true;
                    btnShowActions.setText("hide actions");
                }
                supportInvalidateOptionsMenu();
                break;
            case R.id.button11:
                if (bShowTitle) {
                    bShowTitle = false;
                    actionBar.setTitle("");
                    btnShowTitle.setText("show title");
                } else {
                    bShowTitle = true;
                    actionBar.setTitle(strTitle);
                    btnShowTitle.setText("hide title");
                }
                break;
            case R.id.button12:
                if (bShowSubtitle) {
                    bShowSubtitle = false;
                    actionBar.setSubtitle("");
                    btnShowSubtitle.setText("show subtitle");
                } else {
                    bShowSubtitle = true;
                    actionBar.setSubtitle("sub title");
                    btnShowSubtitle.setText("hide subtitle");
                }
                break;
            case R.id.button13:
                if (bShowCustomView) {
                    bShowCustomView = false;
                    actionBar.setDisplayShowCustomEnabled(false);
                    btnShowCustomView.setText("show custom view");
                } else {
                    bShowCustomView = true;
                    actionBar.setDisplayShowCustomEnabled(true);
                    actionBar.setCustomView(R.layout.actionbar_custom);
                    btnShowCustomView.setText("hide custom view");
                }
                break;
            case R.id.button15:
                if (bShowHome) {
                    bShowHome = false;
                    actionBar.setDisplayShowHomeEnabled(false);
                    btnShowHome.setText("show home icon");
                } else {
                    bShowHome = true;
                    actionBar.setDisplayShowHomeEnabled(true);
                    actionBar.setIcon(R.drawable.actionbarlogo);
                    btnShowHome.setText("hide home icon");
                }
                break;
            case R.id.button16:
                if (bShowBack) {
                    bShowBack = false;
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    btnShowBack.setText("show back icon");
                } else {
                    bShowBack = true;
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    //actionBar.setIcon(R.drawable.actionbarlogo);
                    btnShowBack.setText("hide back icon");
                }
                break;
        }
    }
}
