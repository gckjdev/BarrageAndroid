package com.orange.barrage.android.friend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SimpleAdapter;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.mission.TagMission;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.friend.ui.FriendListInfoListView;
import com.orange.barrage.android.friend.ui.FriendListInfoNewListView;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.view.RemindboxAlertDialog;
import com.orange.protocol.message.UserProtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;

import roboguice.inject.InjectView;
import roboguice.util.Ln;


/**
 * Created by Administrator on 2015/3/20.
 */
public class FriendListSelectActivity extends BarrageCommonActivity {

    private String mTagId;

    private  String mName;

    @InjectView(R.id.friendListView)
    FriendListInfoNewListView mFriendListInfoListView;


    @Inject
    FriendManager mFriendManager;

    @Inject
    TagManager mTagManeger;

    @Inject
    TagMission mTagMission;



    @Override
    protected void onCreate(Bundle saveBundle){
        super.onCreate(saveBundle , R.layout.activity_friend_list_select_ , R.string.b_friend_select_friend , R.string.b_complete);

        init();
}


    private void init(){
        List<UserProtos.PBUser> lists = mFriendManager.getFriendList();

        if(lists == null){
//            mFriendListInfoListView.setUserInfo(lists);
            return;
        }
        byte[] b1 = getIntentByteArrays("b1");
        UserProtos.PBUserTag pb1 = null;
        try {
            if(b1 != null)
                pb1 = UserProtos.PBUserTag.parseFrom(b1);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        lists = new ArrayList<>(lists);

        removeUser(lists , pb1);

        String []s = getIntentArraysString(FriendTabDetailInfoAndCreateAndAlterActivity.TABKEY);

        mTagId = s[0];
        mName = s[1];

        setUserInfo(lists);
    }

    public void setUserInfo(List<UserProtos.PBUser> PBUsers) {
        if(PBUsers == null) return;
        List<Map<String , Object>> data = new ArrayList<>();

        for(UserProtos.PBUser PBUser : PBUsers) {

            Map<String, Object> map = new HashMap<>();
            map.put("1", PBUser);
            map.put("2", PBUser.getNick());
            map.put("3", FriendListInfoNewListView.SELECT_NOT);
            map.put("4", "");
            data.add(map);
        }
       mFriendListInfoListView.setData(data,
               R.layout.fragment_friend_home_listitem,
               new String[]{"1", "2", "3" ,"4"},
               new int[]{R.id.friend_avatar_view, R.id.friend_nick, R.id.checkBox , R.id.friend_signature},
               PBUsers,2);

       mFriendListInfoListView.setBindView(mViewBinder);

    }

    private SimpleAdapter.ViewBinder mViewBinder = new SimpleAdapter.ViewBinder() {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {

            if(view instanceof UserAvatarView && data != null){
                UserProtos.PBUser pbUser = (UserProtos.PBUser) data;
                ((UserAvatarView)view).loadUser(pbUser);
                Ln.e("我被执行");
                return true;
            }

            if(view.getId() == R.id.checkBox){
                view.setVisibility(View.VISIBLE);
            }

            return false;
        }
    };




    private void removeUser(List<UserProtos.PBUser> pbUsers , UserProtos.PBUserTag pb1){

        if(pb1 == null) return;
        List<UserProtos.PBUser> newPbUsers = pb1.getUsersList();

        for (UserProtos.PBUser newpbUser : newPbUsers) {
            for (UserProtos.PBUser pbUser : pbUsers) {
                if(pbUser.getUserId().equals(newpbUser.getUserId())){
                    pbUsers.remove(pbUser);
                    break;
                }
            }
        }
    }



    private void addTag(){
        //确定提交
        Vector<Object> PBUsers = mFriendListInfoListView.getSelectObject();
        if(PBUsers == null || PBUsers.size() == 0){
            MessageCenter.postErrorMessage("请选择里的好友");
            return;
        }


        UserProtos.PBUserTag.Builder builder = UserProtos.PBUserTag.newBuilder();
        for(Object pbUser : PBUsers) {
            builder.addUsers((UserProtos.PBUser)pbUser);
        }

        if(mTagId == null || mTagId.trim().length() == 0) {
            mTagId = "01";
        }
        builder.setTid(mTagId);
        if(mName == null || mName.trim().length() == 0){
            mName = "1";
        }

        if(mTagId == null || mTagId.length() == 0){
            MessageCenter.postErrorMessage("出现异常");
            finish();
        }

        builder.setName(mName);
        UserProtos.PBUserTag pbUserTag = builder.build();
        byte []bs = pbUserTag.toByteArray();
        Intent intent = getIntent();
        intent.putExtra(FriendTabDetailInfoAndCreateAndAlterActivity.TABKEY , bs);
        setResult(FriendTabDetailInfoAndCreateAndAlterActivity.TAG_IS_ALTER, intent);
        finish();
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            onClickLeft(null);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClickLeft(View v) {
        //返回

        if(mFriendListInfoListView.getSelectObject().size() != 0) {

            new RemindboxAlertDialog(this, new String[]{"是", "否"}, "提醒", "你已经添加了好友，是否退出", new RemindboxAlertDialog.OnClickListener() {

                @Override
                public void onClick(int position) {
                    if (RemindboxAlertDialog.LEFTBUTTON == position) {
                        finish();
                    }
                }
            }).show();
        }else finish();
    }

    @Override
    public void onClickRight(View v) {
        super.onClickRight(v);
        addTag();
    }


//    mTagMission.addTag(builder.build() , new AddTagCallback() {
//        @Override
//        public void handleMessage(int errorCode, UserProtos.PBUserTag userTag) {
//            if(errorCode == 0) {
//                MessageCenter.postErrorMessage("添加成功");
//                finish();
//            }else
//                MessageCenter.postErrorMessage("添加失败");
//        }
//    });
}
