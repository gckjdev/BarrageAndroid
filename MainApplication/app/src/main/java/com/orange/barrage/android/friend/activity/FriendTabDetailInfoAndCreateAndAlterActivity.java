package com.orange.barrage.android.friend.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.mission.callback.AddTagCallback;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.friend.ui.FriendIconList;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.barrage.android.util.misc.SystemUtil;
import com.orange.barrage.android.util.view.RemindboxAlertDialog;
import com.orange.protocol.message.UserProtos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Created by Administrator on 2015/3/19.
 */
public class FriendTabDetailInfoAndCreateAndAlterActivity extends BarrageCommonActivity implements FriendIconList.OnClickItemListener {

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

    //标签是否被修改了
    private int mAlterState = TAG_NO_ALTER;


    private String mTab_id;
    private String mState = SEE_STATE;

    private List<UserProtos.PBUserTag> mHaveDeletePBUser = new ArrayList<>();
    private UserProtos.PBUserTag mPBUserTag;

    @Inject
    TagManager mTagManager;

    @InjectView(R.id.frinedIconlist)
    FriendIconList mFriendIconList;

    @InjectView(R.id.tabNameEidtext)
    EditText mTagEditText;

    @InjectView(R.id.tabNameTextView)
    TextView mTagTextView;


    @Inject
    FriendManager mFriendManager;

    private Params mParams = null;

    private RemindboxAlertDialog mRemindboxAlterDialog;


    @Override
    protected  void onCreate(Bundle saveBundle){
        super.onCreate(saveBundle, R.layout.activity_tab_detailed_info , R.string.b_tab_detail_info, R.string.b_editext);

        initView();

    }

    private void initView(){

        mParams = new Params();
        mFriendIconList.setUsers(mFriendManager.getFriendList() , this);

        mTab_id = getIntentString(TABKEY);
        String tab = getIntentString(TABSTATEKEY);
        //查看是否为创建标签状态
        if(tab != null && tab.length() != 0 && tab.equals(CREATE_STATE)){
            mState = CREATE_STATE;
            setTitleRight(R.string.b_create_tab , R.string.b_OK);
            setFriendListAlter();
            showEdiText();
            showTagNametext(R.string.b_create_tab_hinit, "");
        }else {
            closeEidText();
            Ln.e("tagID:" + mTab_id);
            mPBUserTag = mTagManager.getTagById(mTab_id);
            if (mPBUserTag != null) {
               showTagNametext(-1, mPBUserTag.getName());
               mFriendIconList.setUsers(mPBUserTag.getUsersList() , this);
            }
        }

    }


    private void setFriendListAlter(){
        if(mFriendIconList != null && mState.equals(CREATE_STATE) || mState.equals(EDIT_STATE)){
            mFriendIconList.setDeleteType();
            mFriendIconList.setOnClickItemListener(this);
        }
    }


    private void showTagNametext(int hinit, String text){

        mTagTextView.setText(text);
        mTagEditText.setText(text);
        if(hinit > 0){
            mTagEditText.setHint(hinit);
        }
    }

    private void showEdiText(){
        mTagEditText.setVisibility(View.VISIBLE);
        mTagTextView.setVisibility(View.GONE);
        mTagEditText.setSelection(mTagEditText.getText().length());

        SystemUtil.showInputMethodManager(mTagEditText);

    }

    private void closeEidText(){
        mTagEditText.setVisibility(View.GONE);
        mTagTextView.setVisibility(View.VISIBLE);
        SystemUtil.closeInputMethodManager(mTagEditText);
    }

    private void setTitleRight(int titleId , int rightId) {
        mTopBarView.setTitleText(titleId);
        mTopBarView.setRightButton(rightId);
    }


    //返回到查看标签详细信息
    private void backSeeTabInfo(){
        setTitleRight(R.string.b_tab_detail_info , R.string.b_editext);
        mState = SEE_STATE;
        closeEidText();
        mFriendIconList.setIconType(FriendIconList.ICON_ORDINARY);
    }

    //跳转到编辑框界面
    private void toEditText(){
        mHaveDeletePBUser = new ArrayList<>();
        mParams = new Params();
        showEdiText();
        if(mPBUserTag != null) {
            showTagNametext(R.string.b_tag_is_not_null, mPBUserTag.getName());
        }
        setTitleRight(R.string.b_editexttab, R.string.b_OK);
        mState = EDIT_STATE;
        setFriendListAlter();
    }

    //确定修改标签
    private void oKAlterTab(){
        if(mTagEditText.getText().toString().trim().length() == 0){
            MessageCenter.postErrorMessage("请输入标签名字");
            return;
        }
    }

    //确定创建一个标签
    private void okCreateTab(){

        final String name = mTagEditText.getText().toString();
        final String tagId = StringUtil.createUUID();

        if(name.length() == 0){
            MessageCenter.postErrorMessage("请输入标签的名字");
            return;
        }

        UserProtos.PBUserTag.Builder builder = UserProtos.PBUserTag.newBuilder();
        builder.setName(name);
        builder.setTid(tagId);

        mTagMission.addTag(builder.build(), new AddTagCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUserTag userTag) {
                if (errorCode == 0){
                    MessageCenter.postSuccessMessage("标签["+name+"]已创建");
                    setTagIsAlter();
                    setResult(mAlterState);
                    finish();
                }
            }
        });
    }

    private void setTagIsAlter(){
        mAlterState = TAG_IS_ALTER;
    }

    //初始化提醒框
    private void initRemondBox(){
        if(mRemindboxAlterDialog == null)
            mRemindboxAlterDialog = new RemindboxAlertDialog(this , new String[]{"否","是"},"提示","标签已经被修改了，是否退出" , mRemindBoxAlterDialogListener);
    }



    //是否可以修改tag，通过网络
    //返回true ,标签标签已经被修改了
    private boolean isAlterTag(){
        return  mTagEditText.getText().toString().trim().length() != 0 || mParams.isAtler;
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
    public void onClickItem(int postion, View view, Object data, int iconType) {
        if(iconType == FriendIconList.OnClickItemListener.INCO_ADD_BUTTON){
            //添加头像
            ActivityIntent.startForResult(this, FriendListSelectActivity.class, 0x11);
        }else if(iconType == FriendIconList.OnClickItemListener.ICON_ORDINARY_BUTTON){
            //点击对应的头像

        }else if(iconType == FriendIconList.OnClickItemListener.ICON_TOP_DELETE_BUTTON){
            //点击头像做删除的按钮
            mParams.isAtler = true;
            mHaveDeletePBUser.add((UserProtos.PBUserTag) data);
        }

    }

    @Override
    public void onClickLeft(View v) {

        if(mState == SEE_STATE ){
            setResult(mAlterState);
            super.onClickLeft(v);
        }else if(mState == CREATE_STATE){
            showRemind("标签已经被创建了，是否退出？");
        }else if (mState == EDIT_STATE){
            showRemind("标签已经被修改了，是否退出？");
        }

    }


    private void showRemind(String msg){
        initRemondBox();
        if(isAlterTag()){
            //不可以退出
            mRemindboxAlterDialog.show(msg);
        }else{
            dealBack();
        }
    }

    private void dealBack(){
        if(mState == EDIT_STATE){
            backSeeTabInfo();
        }else if(mState == CREATE_STATE){
            finish();
        }
    }

    RemindboxAlertDialog.OnClickListener mRemindBoxAlterDialogListener = new RemindboxAlertDialog.OnClickListener(){

        @Override
        public void onClick(int position) {
            if(position == RemindboxAlertDialog.LEFTBUTTON){

            }else if(position == RemindboxAlertDialog.RIGHTBUTTON){
                dealBack();
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    class Params{
        public boolean isAtler = false;

    }

}
