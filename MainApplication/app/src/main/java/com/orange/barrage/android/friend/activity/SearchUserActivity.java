package com.orange.barrage.android.friend.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.barrage.android.R;
import com.orange.barrage.android.user.mission.SearchUserCallback;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;

import roboguice.inject.InjectView;

public class SearchUserActivity extends BarrageCommonActivity {

    private static final int SEARCH_USER_RESULT_LIMIT = 30;

    private int mOffset = 0;

    @InjectView(R.id.search_edit_text)
    private EditText mSearchEditText;

    @InjectView(R.id.search_listview)
    private ListView mSearchResultListView;

    @Inject
    SearchFriendListAdapter mAdapter;

    Handler handler = new Handler();

    //设置ListView的Adapter
    private void initAdapter() {
        mSearchResultListView.setAdapter(mAdapter);
    }

    //设置文本框中文本的监听
    private void setSearchTextChanged() {
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //刚开始的时候设置ListView不可见
                // mSearchResultListView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  mSearchResultListView.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                //如果编辑框为空，则列表消失
//                if (s.length() == 0) {
//                   mSearchResultListView.setVisibility(View.INVISIBLE);
//                } else {
//                   // mSearchResultListView.setVisibility(View.VISIBLE);
//                }
//
//                handler.post(eChanged);
//
//                doSearch(mSearchEditText.getText().toString());

            }
        });
    }

    Runnable eChanged = new Runnable() {
        @Override
        public void run() {
//            String data = mSearchEditText.getText().toString();
//            mData.clear();
//            getmDataSub(mData, data);
//            simpleAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState, R.layout.activity_search_user, "搜索", -1);

        //刚开始的时候ListView是隐藏起来的
        mSearchResultListView.setVisibility(View.INVISIBLE);
        initAdapter();
        //添加，imeOptions的监听
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //如果动作是搜索的话
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //首先判断输入框是否是空，如果为空，则提示用户，并且软键盘不会消失，如果不为空才执行搜索
                    if (TextUtils.isEmpty(mSearchEditText.getText().toString())) {
                        Toast.makeText(SearchUserActivity.this, "搜索框不能为空", Toast.LENGTH_LONG).show();
                    } else {
                        //先隐藏软键盘
                        ((InputMethodManager) mSearchEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(
                                        SearchUserActivity.this.getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);

                        mSearchResultListView.setVisibility(View.VISIBLE);
//                        //这里的监听是判断文本框输入是否为空，如果为空，则隐藏ListView
//                        setSearchTextChanged();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                doSearch(mSearchEditText.getText().toString());
                            }
                        }).start();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    public void doSearch(String searchKeyword) {

        mUserMission.searchUser(searchKeyword, mOffset, SEARCH_USER_RESULT_LIMIT, new SearchUserCallback() {
            @Override
            public void handleMessage(int errorCode, List<UserProtos.PBUser> pbUserList) {
                // update data and reload UI

                if (pbUserList.size()>0)
                {
                    mAdapter.setFriendList(pbUserList);
                    mSearchResultListView.setBackgroundColor(Color.WHITE);
                    mAdapter.notifyDataSetChanged();
                }
                else
                {
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(SearchUserActivity.this,"错误的输入,请重新输入搜索条件哦",Toast.LENGTH_LONG).show();
                    mAdapter.setFriendList(null);
                    mSearchEditText.setText("");
                    mSearchResultListView.setBackgroundColor(Color.TRANSPARENT);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
