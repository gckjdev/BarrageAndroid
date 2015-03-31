package com.orange.barrage.android.friend.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.activity.FeedPublishedWhatchImageActivity;
import com.orange.barrage.android.feed.ui.PhotoAndCamera;
import com.orange.barrage.android.feed.ui.ShowPublishFeedView;
import com.orange.barrage.android.friend.mission.TagMission;
import com.orange.barrage.android.friend.mission.callback.AddTagCallback;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.friend.ui.FriendIconList;
import com.orange.barrage.android.home.HomeActivity;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.FileUtil;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.barrage.android.util.misc.SystemUtil;
import com.orange.barrage.android.util.view.RemindboxAlertDialog;
import com.orange.protocol.message.UserProtos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import roboguice.inject.InjectView;

import static com.orange.barrage.android.friend.ui.FriendIconList.*;

/**
 * Created by Administrator on 2015/3/19.
 */
public class FriendTabDetailInfoAndCreateAndAlterActivity extends BarrageCommonActivity implements OnClickItemListener {

    public static final String TABKEY = "1";
    public static final String TABSTATEKEY = "2";

    //查看标签
    public static final String SEE_STATE = "2";
    //编辑标签
    public static final String EDIT_STATE = "1";
    //创建标签
    public static final String CREATE_STATE = "3";

    public static final int TAG_IS_ALTER = 0x12;

    public static final int TAG_NO_ALTER = 0x11;

    public static final int TAG_IS_DELETE = 0x13;

    public static final int TAG_IS_CREATE = 0x14;

    //标签是否被修改了
    private int mAlterState = TAG_NO_ALTER;


    private String mTabId;
    private String mState = SEE_STATE;

    private List<UserProtos.PBUserTag> mHaveDeletePBUser = new ArrayList<>();


    private ShowPublishFeedView mShowPublisFeedView;

    @Inject
    TagManager mTagManager;

    @InjectView(R.id.friendIconList)
    FriendIconList mFriendIconList;

    @InjectView(R.id.tabNameEidtext)
    EditText mTagEditText;

    @InjectView(R.id.tabNameTextView)
    TextView mTagTextView;


    @InjectView(R.id.shareOrDeletebutton)
    Button mShareOrDeleteButton;
    @Inject
    FriendManager mFriendManager;

    @Inject
    TagMission mTagMission;

    private Params mParams = new Params();

    private RemindboxAlertDialog mRemindboxAlterDialog;


    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle, R.layout.activity_tab_detailed_info, R.string.b_tab_detail_info, R.string.b_editext);

        initView();

    }

    private void initView() {

        mTabId = getIntentString(TABKEY);
        String tab = getIntentString(TABSTATEKEY);
        //查看是否为创建标签状态
        if (tab != null && tab.length() != 0 && tab.equals(CREATE_STATE)) {
            mState = CREATE_STATE;
            setTitleRight(R.string.b_create_tab, R.string.b_OK);
            mFriendIconList.setUsers(null, this);
            setFriendListAlter();
            showEdiText();
            showTagNametext(R.string.b_create_tab_hinit, "");
            mFriendIconList.setMenbertext(0);
            if (mShareOrDeleteButton != null) {
                mShareOrDeleteButton.setVisibility(View.GONE);
            }
        } else {
            closeEidText();
//            mPBUserTag = mTagManager.getTagById(mTabId);
//            if (mPBUserTag != null) {
//                showTagNametext(-1, mPBUserTag.getName());
//                mFriendIconList.setUsers(mPBUserTag.getUsersList(), this);
//                mFriendIconList.setMenbertext(mPBUserTag.getUsersList().size());
//            } else mFriendIconList.setMenbertext(0);

        }

        UserProtos.PBUserTag userTag = mFriendIconList.initData(mTagManager, mTabId, this);

        if (userTag != null) {
            showTagNametext(-1, userTag.getName());
        }


    }

    private void initPhoto() {
        mShowPublisFeedView = mShowPublisFeedView == null ? new ShowPublishFeedView(this, new PhotoAndCamera.onGetPhotoCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                FileUtil.savePhotoToSDCard(bitmap, HomeActivity.PHOTOPATH, HomeActivity.PHOTONAME);
                ActivityIntent.startIntent(FriendTabDetailInfoAndCreateAndAlterActivity.this, FeedPublishedWhatchImageActivity.class);
                finish();
            }

            @Override
            public void onError(String reason) {

            }
        }) : mShowPublisFeedView;
    }


    private void setFriendListAlter() {
        if (mFriendIconList != null && mState.equals(CREATE_STATE) || mState.equals(EDIT_STATE)) {
            mFriendIconList.setDeleteType();
            mFriendIconList.setOnClickItemListener(this);
        }
    }


    private void showTagNametext(int hinit, String text) {

        mTagTextView.setText(text);
        mTagEditText.setText(text);
        if (hinit > 0) {
            mTagEditText.setHint(hinit);
        }
    }

    private void showEdiText() {
        mTagEditText.setVisibility(View.VISIBLE);
        mTagTextView.setVisibility(View.GONE);


    }


    private void closeEidText() {
        mTagEditText.setVisibility(View.GONE);
        mTagTextView.setVisibility(View.VISIBLE);
        SystemUtil.closeInputMethodManager(mTagEditText);
    }

    private void setTitleRight(int titleId, int rightId) {
        mTopBarView.setTitleText(titleId);
        mTopBarView.setRightButton(rightId);
    }


    private void setButtonText(int stringResoureId) {

        if (stringResoureId == R.string.b_share_photo) {
            mShareOrDeleteButton.setText(stringResoureId);
            mShareOrDeleteButton.setTag("share");
        } else if (stringResoureId == R.string.b_delete_tag) {
            mShareOrDeleteButton.setText(stringResoureId);
            mShareOrDeleteButton.setTag("delete");
        }

    }



    //返回到查看标签详细信息
    private void backSeeTabInfo() {

        MessageCenter.postInfoMessage("退出编辑模式");

        setTitleRight(R.string.b_tab_detail_info, R.string.b_editext);
        mState = SEE_STATE;
        closeEidText();
        setButtonText(R.string.b_share_photo);
        //刷新好友列表
        mFriendIconList.refresh();

    }



    //跳转到编辑框界面
    private void toEditText() {

        MessageCenter.postInfoMessage("进入编辑模式");

        //保持过去的数据
        mFriendIconList.saveOldBuilder();

        setButtonText(R.string.b_delete_tag);
        mParams.isAtler = false;
        mHaveDeletePBUser = new ArrayList<>();
        mParams = new Params();
        mParams.startText = mTagEditText.getText().toString();
        mFriendIconList.setIconType(ICON_ADD_AND_DELETE_BUTTON);
        showEdiText();
        if (mFriendIconList.getOldPBUserTag() != null) {
            showTagNametext(R.string.b_tag_is_not_null, mFriendIconList.getOldPBUserTag().getName());
        }
        setTitleRight(R.string.b_editexttab, R.string.b_OK);
        mState = EDIT_STATE;
        setFriendListAlter();
    }


    private String isTagNameIsEmtry() {
        String name = mTagEditText.getText().toString().trim();
        if (name.length() < 1) {
            MessageCenter.postErrorMessage("标签最少1个字");
            return null;
        } else if (name.length() > 20) {
            MessageCenter.postErrorMessage("标签最多20个字");
            return null;
        }

        if (mFriendIconList.getIconCount() == 0) {
            MessageCenter.postErrorMessage("请选择你的好友");
            return null;
        }

        return name;
    }

    //确定修改标签
    private void oKAlterTab() {

        String name = "";

        if ((name = isTagNameIsEmtry()) == null) return;

        if (!isAlterTag()) {
            MessageCenter.postErrorMessage("你没有做任何的修改");
            return;
        }

        List<UserProtos.PBUser> list = mFriendIconList.getPBUser();
        List<String> idList = new ArrayList<>();

        for (UserProtos.PBUser pbUser : list) {
            idList.add(pbUser.getUserId());
        }


        showProgress("正在修改");
        mTagMission.updateUserTag(mFriendIconList.getBuilder(name), idList, new AddTagCallback() {

            @Override
            public void handleMessage(int errorCode, UserProtos.PBUserTag userTag) {
                if (errorCode == 0) {
                    MessageCenter.postErrorMessage("修改成功");
                    setResult(FriendTabDetailInfoAndCreateAndAlterActivity.TAG_IS_ALTER);
                    finish();
                } else MessageCenter.postErrorMessage("修改失败");
                dismissProgress();
            }
        });


    }

    //确定创建一个标签
    private void okCreateTab() {


        final String name = mTagEditText.getText().toString();
        final String tagId = StringUtil.createUUID();
        if (isTagNameIsEmtry() == null) return;

        showProgress("正在创建标签");

        mTagMission.addTag(mFriendIconList.getBuilder(name, tagId), new AddTagCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUserTag userTag) {
                if (errorCode == 0) {
                    MessageCenter.postSuccessMessage("标签[" + name + "]已创建");
                    setTagIsAlter();
                    setResult(TAG_IS_CREATE);
                    finish();
                }
                dismissProgress();
            }
        });

    }

    private void setTagIsAlter() {
        mAlterState = TAG_IS_ALTER;
    }

    //初始化提醒框
    private void initRemondBox() {
        if (mRemindboxAlterDialog == null)
            mRemindboxAlterDialog = new RemindboxAlertDialog(this, new String[]{"否", "是"}, "提示", "标签已经被修改了，是否退出", mRemindBoxAlterDialogListener);
    }


    //是否可以修改tag，通过网络
    //返回true ,标签标签已经被修改了
    private boolean isAlterTag() {

        String text = mTagEditText.getText().toString().trim();

        if (!text.equals(mParams.startText.trim())) {
            return true;
        }

        mParams.isAtler = mFriendIconList.isAlterIcon();

        return mParams.isAtler;
    }


    @Override
    public void onClickRight(View v) {
        super.onClickRight(v);
        if (mState == SEE_STATE) {
            toEditText();
        } else if (mState == EDIT_STATE) {
            oKAlterTab();
        } else if (mState == CREATE_STATE) {
            okCreateTab();
        }

    }

    @Override
    public boolean onClickItem(int postion, View view, Object data, int iconType) {

        if (iconType == OnClickItemListener.ICON_TOP_DELETE_BUTTON) {
            //点击头像做删除的按钮
            mParams.isAtler = true;
        }
        return false;
    }

    @Override
    public void onClickLeft(View v) {

        if (mState == SEE_STATE) {
            setResult(mAlterState);
            super.onClickLeft(v);
        } else if (mState == CREATE_STATE) {
            showRemind("标签已经被创建了，确定退出？");
        } else if (mState == EDIT_STATE) {
            showRemind("标签已经被修改了，确定退出？");
        }

    }

    public void onClickSharePhotOrDeleteTag(View v) {

        if (mState == EDIT_STATE) {
            //编辑界面
            //打开照片
            showRemindboxAlertDialog(new String[]{"是", "否"}, "提醒", "确定删除标签", -1);
        } else if (mState == SEE_STATE) {
            //查看
            initPhoto();
            mShowPublisFeedView.showPublishFeedView();
        }


    }

    @Override
    public void onRemindItemClick(int position) {
        if (position == RemindboxAlertDialog.LEFTBUTTON) {
            deleteTag();
        }
    }

    private void deleteTag() {

        showProgress("正在删除，请稍等...");
        //删除标签
        mTagMission.deleteTag(mFriendIconList.getPBUserTag(), new AddTagCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUserTag userTag) {
                if (errorCode != 0) {
                    MessageCenter.postErrorMessage("删除失败，请重试");
                } else {
                    setResult(FriendTabDetailInfoAndCreateAndAlterActivity.TAG_IS_DELETE);
                    MessageCenter.postErrorMessage("删除成功");
                    finish();
                }

                dismissProgress();
            }
        });

    }


    private void showRemind(String msg) {
        initRemondBox();
        if (isAlterTag()) {
            //不可以退出
            mRemindboxAlterDialog.show(msg);
        } else {
            dealBack();
        }
    }

    private void dealBack() {
        if (mState == EDIT_STATE) {
            backSeeTabInfo();
        } else if (mState == CREATE_STATE) {
            finish();
        }
    }

    RemindboxAlertDialog.OnClickListener mRemindBoxAlterDialogListener = new RemindboxAlertDialog.OnClickListener() {

        @Override
        public void onRemindItemClick(int position) {
            if (position == RemindboxAlertDialog.LEFTBUTTON) {

            } else if (position == RemindboxAlertDialog.RIGHTBUTTON) {
                dealBack();
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        mParams.isAtler = mFriendIconList.startForResult(resultCode, data);


        if (mShowPublisFeedView != null) {
            PhotoAndCamera photoAndCamera = mShowPublisFeedView.getPhotoAndCamera();

            if (photoAndCamera != null) {
                photoAndCamera.getPicture(requestCode, resultCode, data);
            }
        }
    }


    class Params {
        public boolean isAtler = false;
        public String startText = "";
    }

}
