package com.example.chengqi.mycoderepo.expert;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chengqi.mycoderepo.R;

import java.util.ArrayList;
import java.util.List;

public class DemoListSearch extends AppCompatActivity {
    private List<String> mData = new ArrayList<String>();  // 这个数据会改变
    private List<String> mBackData;  // 这是原始的数据

    private ListView mListView;
    private SearchView mSearchView;

    private MyAdapter2 mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list_search);

        mListView = (ListView) super.findViewById(R.id.list);
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new ItemClick());
        initData();

        mAdapter = new MyAdapter2();
        mListView.setAdapter(mAdapter);
    }

    // 必须实现Filterable接口
    private class MyAdapter2 extends BaseAdapter implements Filterable {
        private MyFilter mFilter;

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = View.inflate(DemoListSearch.this, R.layout.item1,
                        null);
            }

            TextView show = (TextView) convertView.findViewById(R.id.show);

            show.setText(mData.get(position));

            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (null == mFilter) {
                mFilter = new MyFilter();
            }
            return mFilter;
        }

        // 自定义Filter类
        class MyFilter extends Filter {
            @Override
            // 该方法在子线程中执行
            // 自定义过滤规则
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                List<String> newValues = new ArrayList<String>();
                String filterString = constraint.toString().trim()
                        .toLowerCase();

                // 如果搜索框内容为空，就恢复原始数据
                if (TextUtils.isEmpty(filterString)) {
                    newValues = mBackData;
                } else {
                    // 过滤出新数据
                    for (String str : mBackData) {
                        if (-1 != str.toLowerCase().indexOf(filterString)) {
                            newValues.add(str);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mData = (List<String>) results.values;

                if (results.count > 0) {
                    mAdapter.notifyDataSetChanged();  // 通知数据发生了改变
                } else {
                    mAdapter.notifyDataSetInvalidated(); // 通知数据失效
                }
            }
        }
    }

    // 搜索文本监听器
    private class QueryListener implements SearchView.OnQueryTextListener {
        // 当内容被提交时执行
        @Override
        public boolean onQueryTextSubmit(String query) {
            return true;
        }

        // 当搜索框内内容发生改变时执行
        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                mListView.clearTextFilter();  // 清楚ListView的过滤
            } else {
                mListView.setFilterText(newText); // 设置ListView的过滤关键词
            }
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // 获取Menu中searchView组件
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu
                .findItem(R.id.search));

        // 设置监听器
        mSearchView.setOnQueryTextListener(new QueryListener());
        return true;
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            mData.add("hello android " + i);
        }
        mBackData = mData;
    }

    private class ItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(DemoListSearch.this, mData.get(position),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
