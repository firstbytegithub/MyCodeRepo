package com.example.chengqi.mycoderepo.expert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chengqi.mycoderepo.R;

public class ExpertActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final String LOG_TAG = "ExpertActivity";
    private ListView mListView;

    final private String[] strItems = new String[]{
            "Space",
            "CheckedTextView",
            "QuickContactBadge",
            "ExtractEditText",
            "AutoCompleteTextView",
            "MultiAutoCompleteTextView",
            "NumberPicker",
            "ZoomButton",
            "ZoomControls",
            "MediaController",
            "GestureOverlayView",
            "SurfaceView",
            "TextureView",
            "StackView",
            "ViewStub",
            "ViewAnimator",
            "ViewFlipper",
            "ViewSwitcher",
            "ImageSwitcher",
            "TextSwitcher",
            "AdapterViewFlipper",
            "DemoViewFlipperFragment",
            "ViewPager",
            "Dialog",
            "List Search",
            "Animation"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);
        initListView();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.lv_expert);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "click " + position);
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(this, SpaceActivity.class);
                break;
            case 1:
                intent = new Intent(this, CheckedTextViewActivity.class);
                break;
            case 2:
                intent = new Intent(this, QuickContactBadgeActivity.class);
                break;
            case 3:
                intent = new Intent(this, ExtractEditTextActivity.class);
                break;
            case 4:
                intent = new Intent(this, AutoCompleteTextViewActivity.class);
                break;
            case 5:
                intent = new Intent(this, MultiAutoCompleteTextViewActivity.class);
                break;
            case 6:
                intent = new Intent(this, NumberPickerActivity.class);
                break;
            case 7:
                intent = new Intent(this, ZoomButtonActivity.class);
                break;
            case 8:
                intent = new Intent(this, ZoomControlsActivity.class);
                break;
            case 9:
                intent = new Intent(this, MediaControllerActivity.class);
                break;
            case 10:
                intent = new Intent(this, GestureOverlayViewActivity.class);
                break;
            case 11:
                intent = new Intent(this, DemoSurfaceView.class);
                break;
            case 12:
                intent = new Intent(this, TextureViewActivity.class);
                break;
            case 13:
                intent = new Intent(this, StackViewActivity.class);
                break;
            case 14:
                intent = new Intent(this, ViewStubActivity.class);
                break;
            case 15:
                intent = new Intent(this, ViewAnimatorActivity.class);
                break;
            case 16:
                intent = new Intent(this, ViewFlipperActivity.class);
                break;
            case 17:
                intent = new Intent(this, ViewSwitcherActivity.class);
                break;
            case 18:
                intent = new Intent(this, ImageSwitcherActivity.class);
                break;
            case 19:
                intent = new Intent(this, TextSwitcherActivity.class);
                break;
            case 20:
                intent = new Intent(this, AdapterViewFlipperActivity.class);
                break;
            case 21:
                intent = new Intent(this, DemoViewFlipperFragment.class);
                break;
            case 22:
                intent = new Intent(this, DemoViewPager.class);
                break;
            case 23:
                intent = new Intent(this, DemoDialog.class);
                break;
            case 24:
                intent = new Intent(this, DemoListSearch.class);
                break;
            case 25:
                intent = new Intent(this, DemoAnimation.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
