package com.orange.barrage.android.friend.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.mission.callback.AddTagCallback;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.friend.ui.FriendIconList;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.inject.InjectView;

/**
 * Created by Administrator on 2015/3/19.
 */
public class FriendTabDetailInfoAndCreateAndAlterActivity extends BarrageCommonActivity {

    public static final String TABKEY = "1";
    public static final String TABSTATEKEY = "2";

    public static final String SEE_STATE = "2";
    public static final String EDIT_STATE = "1";
    public static final String CREATE_STATE = "3";

    private String mTab_id;
    private String mState = SEE_STATE;
    //是否可以退出
    private boolean mIsBakc = true;

    @Inject
    TagManager mTagManager;

    @InjectView(R.id.frinedIconlist)
    FriendIconList mFriendIconList;

    @InjectView(R.id.tabNameEidtext)
    EditText mTagEditText;

    @InjectView(R.id.tabNameTextView)
    TextView mTabTextView;

    @Override
    protected  void onCreate(Bundle saveBundle){
        super.onCreate(saveBundle, R.layout.activity_tab_detailed_info , R.string.b_tab_detail_info, R.string.b_editext);

        initView();

    }

    private void initView(){


        mFriendIconList.setUsers(null , this);

        mTab_id = getIntentString(TABKEY);
        String tab = getIntentString(TABSTATEKEY);
        //查看是否为创建标签状态
        if(tab != null && tab.length() != 0 && tab.equals(CREATE_STATE)){
            mState = CREATE_STATE;
            setTitleRight(R.string.b_create_tab , R.string.b_OK);
            setFriendListAlter();
            showEidtext(R.string.b_create_tab_hinit , "");
        }else{
            closeEidText();
        }



//        UserProtos.PBUserTag userTag = mTagManager.getTagById(mTab_id);
//        if(userTag == null) return;
//
//        mTagEditText.setText(userTag.getName());
//        mTagEditText.setText("北京");
//        //设置头像
//        mFriendIconList.setUsers(userTag.getUsersList(), this);


    }


    private void setFriendListAlter(){
        if(mFriendIconList != null && mState.equals(CREATE_STATE) || mState.equals(EDIT_STATE)){
            mFriendIconList.setDeleteType();
        }
    }


    private void showEidtext(int hinit , String text){

        mTagEditText.setVisibility(View.VISIBLE);
        mTabTextView.setVisibility(View.GONE);

        mTagEditText.setText(text);
        if(hinit > 0)
            mTagEditText.setHint(hinit);
    }

    private void closeEidText(){
        mTagEditText.setVisibility(View.GONE);
        mTabTextView.setVisibility(View.VISIBLE);

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
        showEidtext(-1 , "");
        setTitleRight(R.string.b_editexttab, R.string.b_OK);
        mState = EDIT_STATE;
        setFriendListAlter();
    }

    //确定修改标签
    private void toOKAlterTab(){

    }

    //确定创建一个标签
    private void OkCreateTab(){

        final String name = mTagEditText.getText().toString();

        UserProtos.PBUserTag.Builder builder = UserProtos.PBUserTag.newBuilder();
        builder.setName(name);

        mTagMission.addTag(builder.build(), new AddTagCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUserTag userTag) {
                if (errorCode == 0){
                    MessageCenter.postSuccessMessage("标签["+name+"]已创建");
                    finish();
                }
            }
        });

    }


    @Override
    public void onClickRight(View v) {
        super.onClickRight(v);
        if(mState == SEE_STATE){
           toEditText();
        }else if(mState == EDIT_STATE){
           toOKAlterTab();
        }else if(mState == CREATE_STATE){
            OkCreateTab();
        }

    }

    @Override
    public void onClickLeft(View v) {



        if(mState == SEE_STATE || mState == CREATE_STATE){

            if(!mIsBakc){
                //不可以退出
            }else
                super.onClickLeft(v);
        }
        else if (mState == EDIT_STATE){
            //返回到查看的
            backSeeTabInfo();
        }

    }


    private void setTitleRight(int titleId , int rightId) {
        mTopBarView.setTitleText(titleId);
        mTopBarView.setRightButton(rightId);
    }
}
