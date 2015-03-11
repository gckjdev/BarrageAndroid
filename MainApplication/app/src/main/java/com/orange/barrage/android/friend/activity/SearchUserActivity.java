package com.orange.barrage.android.friend.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchUserActivity extends BarrageCommonActivity {
    private EditText mEditText;
    private ListView mListView;
    //用来匹配listview的中图像和文本
    ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
    SimpleAdapter simpleAdapter;
    Handler handler = new Handler();

    //保存图像的
    ArrayList<Object> mImageView=new ArrayList<Object>();
    //用文本来匹配
    ArrayList<String> mTextView = new ArrayList<String>();

    //设置ListView的Adapter
    private void set_mListView_adapter() {
        getmData(mData);
        simpleAdapter = new SimpleAdapter(this, mData, R.layout.search_user_activity_listitem, new String[]{"image", "text"}, new int[]{R.id.search_imageview, R.id.search_friend_nick});
        mListView.setAdapter(simpleAdapter);
    }

    //设置文本框中文本的监听
    private void set_search_TextChanged() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //刚开始的时候设置ListView不可见
                mListView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mListView.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                */
                if (s.length() == 0) {
                    mListView.setVisibility(View.INVISIBLE);
                } else {
                    mListView.setVisibility(View.VISIBLE);
                }
                handler.post(eChanged);
            }
        });
    }

    Runnable eChanged = new Runnable() {
        @Override
        public void run() {
            String data = mEditText.getText().toString();
            mData.clear();
            getmDataSub(mData, data);
            simpleAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 获得根据搜索框的数据data来从元数据筛选，筛选出来的数据放入mDataSubs里
     *
     * @param mDataSubs
     * @param data
     */
    private void getmDataSub(ArrayList<Map<String, Object>> mDataSubs, String data) {
        int length = mTextView.size();
        for (int i = 0; i < length; i++) {
            if (mTextView.get(i).contains(data))
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("image",R.drawable.ic_launcher);
                map.put("text", mTextView.get(i));
                mDataSubs.add(map);
            }
        }
    }

    //初始化一些数据
    private void getmData(ArrayList<Map<String, Object>> mDatas) {
        Map<String, Object> map = new HashMap<String, Object>();

        mImageView.add(R.drawable.ic_launcher);
        mTextView.add("这是文本，1234");

        map.put("image", mImageView.get(0));
        map.put("text", mTextView.get(0));
        mDatas.add(map);

        Map<String, Object> map1=new HashMap<String,Object>();
        mImageView.add(R.drawable.ic_launcher);
        mTextView.add("1234");

        map1.put("image",mImageView.get(0));
        map1.put("text",mTextView.get(0));
        mDatas.add(map1);

        Map<String, Object> map2=new HashMap<String,Object>();
        mImageView.add(R.drawable.ic_launcher);
        mTextView.add("1234567");
        map2.put("image",mImageView.get(0));
        map2.put("text",mTextView.get(0));
        mDatas.add(map2);

        Map<String, Object> map3=new HashMap<String,Object>();
        mImageView.add(R.drawable.ic_launcher);
        mTextView.add("Hello");
        map3.put("image",mImageView.get(0));
        map3.put("text",mTextView.get(0));
        mDatas.add(map2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_search_user, "搜索", -1);
        mEditText = (EditText) findViewById(R.id.search_edittest);
        mListView = (ListView) findViewById(R.id.search_listview);
        //刚开始的时候ListView是隐藏起来的

        mListView.setVisibility(View.INVISIBLE);
        set_search_TextChanged();
        set_mListView_adapter();
    }
}
