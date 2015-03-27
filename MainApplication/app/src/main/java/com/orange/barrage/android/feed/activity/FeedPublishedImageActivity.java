package com.orange.barrage.android.feed.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
import com.orange.barrage.android.feed.mission.PublishFeedMission;
import com.orange.barrage.android.friend.mission.callback.GetTagListCallback;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.friend.ui.FriendIconList;
import com.orange.barrage.android.friend.ui.FriendTagItemView;
import com.orange.barrage.android.friend.ui.FriendTagList;
import com.orange.barrage.android.friend.ui.FriendTagView;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.FileUtil;
import com.orange.barrage.android.util.view.RemindboxAlertDialog;
import com.orange.protocol.message.BarrageProtos;
import com.orange.protocol.message.UserProtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Created by youjiannuo on 2015/3/24.
 */
public class FeedPublishedImageActivity extends BarrageCommonActivity
        implements FriendTagView.OnClickTabIconItemListener, FriendIconList.OnClickItemListener {


    @Inject
    FriendManager mFriendManager;

    @Inject
    UserManager mUserManger;

    @Inject
    FeedMission mFeedMission;

    @InjectView(R.id.friendIconList)
    FriendIconList mFriendIconList;

    @InjectView(R.id.chengyuan)
    TextView mMenberTextView;

    @Inject
    TagManager mTagManager;

    private FriendTagList mFriendTagList;

    private int mNum = 0;

    private Map<String, FriendTagItemView> maps = new HashMap<>();

    private int statue = 0;

    private UserProtos.PBUser pbUser;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle, R.layout.activity_publishimage, R.string.b_share_to_who, R.string.b_OK);

        initView();
    }

    private void initView() {

        showProgress("");
        mFriendIconList.initData(mTagManager, "", this);

        mFriendTagList = new FriendTagList(this, mTagManager, this);

        pbUser = mUserManger.getUser();


        if (pbUser != null) {
            mFriendIconList.setUser(pbUser, this, FriendIconList.ICON_ADD_AND_DELETE_BUTTON);
            setMenBerTextView();
        } else {
            mFriendIconList.setUsers(new ArrayList<UserProtos.PBUser>(), this, FriendIconList.ICON_ADD_AND_DELETE_BUTTON);
            setMenBerTextView();
        }
        //注册监听
        mFriendIconList.setOnClickItemListener(this);

        mTagMission.getAllTags(new GetTagListCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUserTagList tagList) {
                if (errorCode == 0) {
                    // reload tag view
                    mFriendTagList.loadLocalTagList(FriendTagView.Params.PARAMS_HOLLOW);

                }
                mFriendIconList.setVisibility(View.VISIBLE);
                dismissProgress();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFriendIconList.startForResult(resultCode, data)) ;
    }

    public void setMenBerTextView() {
        mFriendIconList.setMenbertext("已经选择的成员");
    }

    @Override
    public void onClickLeft(View v) {

        statue = 0;

        //返回
        if (mFriendIconList.getIconCount() == 0) super.onClickLeft(v);
        else {
            showRemindboxAlertDialog(new String[]{"是", "否"}, "提示", "是否退出", -1);

        }
    }


    @Override
    public void onClickRight(View v) {
        super.onClickRight(v);

        //发表
        statue = 1;
        if (mFriendIconList.getIconCount() == 0) {
            MessageCenter.postTestMessage("你还没有选择好友");
            return;
        }
        showRemindboxAlertDialog(new String[]{"是", "否"}, "提示", "是否发表", -1);
    }


    @Override
    public void onRemindItemClick(int position) {
        if (position == RemindboxAlertDialog.LEFTBUTTON) {

            if (statue == 0) {
                //退出
                finish();
            } else if (statue == 1) {
                //发表
                publishImage();
            }

        }
    }


    private void publishImage(){

        Bitmap bitmap = getPublishImage();
        if(bitmap == null){
            MessageCenter.postErrorMessage("图片获取失败");
            return;
        }
        showProgress("正在发表");
        mFeedMission.createFeed(bitmap , "", mFriendIconList.getPBUser() , new FeedMissionCallbackInterface(){

            @Override
            public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {
                finish();
                MessageCenter.postSuccessMessage("发送成功");
                deletePublishImage();
                dismissProgress();
            }

            @Override
            public void handleFailure(int errorCode) {
                if(errorCode != 0 ){
                    MessageCenter.postErrorMessage("发送失败");
                }
                dismissProgress();
            }
        });
    }


    private Bitmap getPublishImage(){
        return FileUtil.getPhotoFromSDCard(HomeActivity.PHOTOPATH, HomeActivity.PHOTONAME);
    }

    private void deletePublishImage(){
        FileUtil.deleteFile(HomeActivity.PHOTOPATH+"/"+HomeActivity.PHOTONAME);
    }

    private void deleteTag(String tagId) {

        List<UserProtos.PBUser> pbUsers = getUser(tagId);
        if (pbUsers == null) return;
        mFriendIconList.removeUsers(pbUsers);

    }

    private void addTag(String tagId) {
        List<UserProtos.PBUser> pbUsers = getUser(tagId);
        if (pbUsers == null || pbUsers.size() == 0) return;
        mFriendIconList.addUsers(pbUsers);

        pbUsers.get(0).getTagsList();

    }

    List<UserProtos.PBUser> getUser(String tagId) {
        UserProtos.PBUserTag pbUserTag = mTagManager.getTagById(tagId);
        return pbUserTag.getUsersList();
    }

    @Override
    public void onClickItem(String tagId, FriendTagItemView v, FriendTagView friendTagView) {
        //点击标签


        FriendTagView.Params params = v.getParams();

        maps.put(tagId, v);

        if (params.state == FriendTagView.Params.PARAMS_SOLID) {
            //修改成为空心
            params.state = FriendTagView.Params.PARAMS_HOLLOW;

            deleteTag(tagId);
        } else {
            //修改成为实心
            params.state = FriendTagView.Params.PARAMS_SOLID;

            addTag(tagId);
        }

        v.setParams(params);
    }


    @Override
    public boolean onClickItem(int postion, View view, Object data, int iconType) {
        //点击头像
//        if(iconType == )

        if (iconType == FriendIconList.OnClickItemListener.ICON_TOP_DELETE_BUTTON) {

            if (pbUser != null && data != null && pbUser.getUserId().equals(((UserProtos.PBUser) data).getUserId())) {
                MessageCenter.postInfoMessage("不可以把自己删除");
                return true;
            }

            if (data == null || !(data instanceof UserProtos.PBUser)) return false;
            dealDeleteIcon(data);
        }
        return false;
    }


    private void dealDeleteIcon(Object data) {
        List<UserProtos.PBUserTag> pbUserTags = getTag((UserProtos.PBUser) data, mTagManager);

        for (int i = 0; i < pbUserTags.size(); i++) {
            String tagId = pbUserTags.get(i).getTid();
            FriendTagItemView friendTagItemView = maps.get(tagId);

            if (friendTagItemView == null) continue;

            FriendTagView.Params params = friendTagItemView.getParams();

            if (params.state == FriendTagView.Params.PARAMS_SOLID) {
                //修改成为空心
                params.state = FriendTagView.Params.PARAMS_HOLLOW;
            } else {
                //修改成为实心
                params.state = FriendTagView.Params.PARAMS_SOLID;
            }

            friendTagItemView.setParams(params);

            maps.remove(tagId);

        }
    }


}
