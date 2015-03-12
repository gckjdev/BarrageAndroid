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
import com.orange.barrage.android.friend.mission.callback.GetFriendListCallback;
import com.orange.barrage.android.friend.ui.FriendListAdapter;
import com.orange.barrage.android.user.mission.SearchUserCallback;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.protocol.message.UserProtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                mSearchResultListView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchResultListView.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                if (s.length() == 0) {
                    mSearchResultListView.setVisibility(View.INVISIBLE);
                } else {
                    mSearchResultListView.setVisibility(View.VISIBLE);
                }
                handler.post(eChanged);

                doSearch(mSearchEditText.getText().toString());
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
        setSearchTextChanged();
        initAdapter();
    }

    public void doSearch(String searchKeyword){

        // just for test
//        mFriendMission.syncFriend(new GetFriendListCallback() {
//            @Override
//            public void handleMessage(int errorCode, UserProtos.PBUserFriendList friendList) {
//                // update data
//                mAdapter.setFriendList(friendList.getFriendsList());
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        mUserMission.searchUser(searchKeyword, mOffset, SEARCH_USER_RESULT_LIMIT, new SearchUserCallback() {
            @Override
            public void handleMessage(int errorCode, List<UserProtos.PBUser> pbUserList) {
                // update data and reload UI
                mAdapter.setFriendList(pbUserList);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
