package com.orange.barrage.android.friend.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.user.mission.SearchUserCallback;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.KeyboardUtil;
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

    @Inject
    FriendManager mFriendManager;

    Handler handler = new Handler();

    //设置ListView的Adapter
    private void initAdapter() {
        mSearchResultListView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState, R.layout.activity_search_user, "搜索",R.string.y_tijiao);

        initAdapter();
        initTopBar();

        updateSearchResultListView(null);

        //刚开始的时候ListView是隐藏起来的
        mSearchResultListView.setBackgroundColor(Color.WHITE);
        mSearchResultListView.setVisibility(View.INVISIBLE);

        //添加，搜索事件的监听
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //如果动作是搜索的话
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    clickSearchButton();
                    return true;
                }
                return false;
            }
        });

    }

    private void initTopBar() {

        // TODO set right button text
//        mTopBarView.setRightButton();

        mTopBarView.setOnClickRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSearchButton();
            }
        });
    }

    private void clickSearchButton(){
        //首先判断输入框是否是空，如果为空，则提示用户，并且软键盘不会消失，如果不为空才执行搜索
        if (TextUtils.isEmpty(mSearchEditText.getText().toString())) {
            // TODO
            MessageCenter.postInfoMessage(getResources().getString(R.string.search_friend_inputnull));
            //KeyboardUtil.hideKeyboard(mSearchEditText,this);
        } else {
            KeyboardUtil.hideKeyboard(mSearchEditText, this);
            doSearch(mSearchEditText.getText().toString());
        }
    }

    public void doSearch(String searchKeyword) {

        mUserMission.searchUser(searchKeyword, mOffset, SEARCH_USER_RESULT_LIMIT, new SearchUserCallback() {
            @Override
            public void handleMessage(int errorCode, List<UserProtos.PBUser> pbUserList) {
                // update data and reload UI

                if (errorCode == 0) {

                    updateSearchResultListView(pbUserList);

                    if (pbUserList.size() == 0) {
                        // TODO use MessageCenter
                        MessageCenter.postInfoMessage(getResources().getString(R.string.search_friend_find)+" "+mSearchEditText.getText().toString()+" "+getResources().getString(R.string.search_friend_result));
                    }
                }
            }
        });
    }


    public void updateSearchResultListView(List<UserProtos.PBUser> pbUserList){
        mAdapter.setFriendList(pbUserList);

        if (pbUserList == null || pbUserList.size() == 0){
            mSearchResultListView.setVisibility(View.INVISIBLE);
            mSearchResultListView.setBackgroundColor(Color.TRANSPARENT);
            mAdapter.notifyDataSetChanged();
            return;
        }

        mSearchResultListView.setVisibility(View.VISIBLE);
        mSearchResultListView.setBackgroundColor(Color.WHITE);
        mAdapter.notifyDataSetChanged();
    }
}
