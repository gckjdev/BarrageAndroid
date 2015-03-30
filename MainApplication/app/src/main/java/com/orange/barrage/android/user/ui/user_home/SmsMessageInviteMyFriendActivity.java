package com.orange.barrage.android.user.ui.user_home;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ListView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.ui.FriendListInfoNewListView;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.view.RemindboxAlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.InjectView;

public class SmsMessageInviteMyFriendActivity extends BarrageCommonActivity implements RemindboxAlertDialog.OnClickListener {
    private static final String AVAILINVITENUMBER = "avail_number";
    @InjectView(R.id.listView1)
    private FriendListInfoNewListView listView;

    private List<UserInfo> mAddressList;

    private String mValues;

    private UserInfo info;
    private Uri[] c = new Uri[]{ContactsContract.Contacts.CONTENT_URI,
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    };

    private static final int BACK = 1;
    private static final int SENDMSG = 2;

    private int mState = BACK;


    //得到系统联系人的方法
    public void getSystemContacts() {
        mAddressList = new ArrayList<UserInfo>();

        String contactsID;
        Cursor cursor = getContentResolver().query(c[0], null, null, null, null);
        while (cursor.moveToNext()) {
            info = new UserInfo();
            contactsID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String userName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            info.setUserName(userName);
            Cursor cursor2 = getContentResolver().query(c[1], null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactsID, null, null);
            while (cursor2.moveToNext()) {
                String userPhone = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                info.setPhoneNum(userPhone);
            }
            cursor2.close();
            mAddressList.add(info);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_sms_message_invite_my_friend, "短信邀请", R.string.b_send);
        initView();
        mValues = getIntentString(AVAILINVITENUMBER);
    }

    private void initView() {
        getSystemContacts();
        initListView();

    }

    private void initListView() {

        List<Map<String, Object>> data = new ArrayList<>();


        listView.setSelectOrNotSelectImageResource(R.drawable.invite_myfriend_select, R.drawable.invite_myfriend_notselect);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        for (UserInfo userInfo : mAddressList) {
            Map<String, Object> map = new HashMap<>();
            map.put("1", userInfo.getUserName());
            map.put("2", userInfo.getPhoneNum());
            map.put("3", FriendListInfoNewListView.SELECT_NOT);
            data.add(map);
        }

        listView.setData(data,
                R.layout.invite_myfriend_listitem,
                new String[]{"1", "2", "3"},
                new int[]{R.id.userName, R.id.userPhone, R.id.imageview_check},
                mAddressList, 2);

    }


    @Override
    public void onClickRight(View v) {
        super.onClickRight(v);
        mState = SENDMSG;
        List<Object> list = getSelectUserInfo();

        if (list.size() == 0) {
            MessageCenter.postInfoMessage("请选择需要发送的联系人");
            return;
        }
        showRemindboxAlertDialog(new String[]{"取消", "确定"}, "提醒", "是否发送短信", -1);
    }

    private List<Object> getSelectUserInfo() {
        return listView.getSelectObject();
    }

    @Override
    public void onRemindItemClick(int position) {
        //左边的按钮执行的动作
     /*   MessageCenter.postTestMessage(position + "");*/
        if (position == RemindboxAlertDialog.RIGHTBUTTON) {

            if(mState == SENDMSG ) {
               //发送短信
                List<Object> list = getSelectUserInfo();

                for (int i = 0; i < list.size(); i++) {
                    UserInfo userInfo = (UserInfo) list.get(i);
                    //如果短信内容过多，可以分条发送
                    if (mValues.length() >= 70) {
                        List<String> ms = SmsManager.getDefault().divideMessage(mValues);
                        for (String str : ms) {
                            SmsManager.getDefault().sendTextMessage(userInfo.getPhoneNum().toString(), null, str, null, null);
                        }
                    } else {
                        SmsManager.getDefault().sendTextMessage(userInfo.getPhoneNum().toString(), null, mValues, null, null);
                    }
                }
                MessageCenter.postInfoMessage("发送邀请短信成功");
            }
            finish();
        }
    }

    @Override
    public void onClickLeft(View v) {
        //
        mState = BACK;
        List<Object> list = getSelectUserInfo();
        if (list.size() == 0) {
            super.onClickLeft(v);
        } else {
            showRemindboxAlertDialog(new String[]{"取消", "确定"}, "提醒", "是否放弃短信邀请用户", -1);
        }
    }
}
