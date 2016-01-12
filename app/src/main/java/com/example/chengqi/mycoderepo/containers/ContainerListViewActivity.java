package com.example.chengqi.mycoderepo.containers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.chengqi.mycoderepo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ContainerListViewActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ContainerListView";
    private ListView listView;
    private Button btn5, btn6, btn7, btn8, btn9, btn10;
    private static final String items[] = {"111", "222", "333", "444", "555", "111", "222", "333", "444", "555", "111", "222", "333", "444", "555"};

    private class ViewHolder {
        public ImageView image;
        public TextView text1;
        public TextView text2;
        public CheckBox checkBox;
    }

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
//                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
//                holder = new ViewHolder();
//                holder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
//                holder.text1.setText("hooo");
                convertView = mInflater.inflate(R.layout.custom_list_item, null);
                holder = new ViewHolder();
//                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                holder.text1 = (TextView) convertView.findViewById(R.id.textView5);
                holder.text2 = (TextView) convertView.findViewById(R.id.textView6);
//                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
//                holder.image.setImageResource(R.drawable.actionbarlogo);
                holder.text1.setText("text1");
                holder.text2.setText("text2");
//                holder.checkBox.setChecked(true);
//                holder.checkBox.setFocusable(false);
//                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        Log.d(LOG_TAG, "check change");
//                    }
//                });
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            holder.image =
            convertView.setFocusable(true);
            convertView.setSelected(true);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(LOG_TAG, "clicked!!");
//                    v.setBackgroundColor(Color.rgb(255, 0, 0));
                }
            });

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_list_view);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "click " + position);
            }
        });
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "sel " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG_TAG, "onNothingSelected");
            }
        });
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button36);
        btn10 = (Button) findViewById(R.id.button37);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(new ArrayAdapter<String>(ContainerListViewActivity.this, android.R.layout.simple_list_item_checked, items));
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(new ArrayAdapter<String>(ContainerListViewActivity.this, android.R.layout.simple_list_item_checked, items));
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(new ArrayAdapter<String>(ContainerListViewActivity.this, android.R.layout.simple_list_item_single_choice, items));
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(new ArrayAdapter<String>(ContainerListViewActivity.this, android.R.layout.simple_list_item_multiple_choice, items));
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < 4; i++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemImage", R.drawable.actionbarlogo);//加入图片
                    map.put("ItemTitle", "第" + i + "行");
                    map.put("ItemText", "这是第" + i + "行");
                    listItem.add(map);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(ContainerListViewActivity.this, listItem, R.layout.custom_list_item,
                        new String[]{"ItemImage", "ItemTitle", "ItemText"}, new int[]{R.id.imageView, R.id.textView5, R.id.textView6});
                listView.setAdapter(simpleAdapter);
                listView.setFocusable(true);
//                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyAdapter md = new MyAdapter(ContainerListViewActivity.this);
                listView.setAdapter(md);
            }
        });
    }
}
