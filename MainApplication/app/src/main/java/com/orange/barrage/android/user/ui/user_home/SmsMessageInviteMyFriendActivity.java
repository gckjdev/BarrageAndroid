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

    private RemindboxAlertDialog mRemindBoxAlertDialog;

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
    }/*


    class BBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAddressList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAddressList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().from(SmsMessageInviteMyFriendActivity.this).inflate(R.layout.invite_myfriend_listitem, null);
                holder = new Holder();
                holder.userName = (TextView) convertView.findViewById(R.id.userName);
                holder.userPhone = (TextView) convertView.findViewById(R.id.userPhone);
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageview_check);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.userName.setText(mAddressList.get(position).getUserName());
            holder.userPhone.setText(mAddressList.get(position).getPhoneNum());
            holder.imageView.setImageResource(R.drawable.checkbox_checked);
            return convertView;
        }

        class Holder {
            TextView userName, userPhone;
            Button inviteButton;
            ImageView imageView;
        }

    }*/

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

        List<Object> list = getSelectUserInfo();

        if (list.size() == 0) {
            MessageCenter.postInfoMessage("请选择需要发送的联系人");
            return;
        }

        initRemindAlterDialog();

        mRemindBoxAlertDialog.show();

    }

    private List<Object> getSelectUserInfo() {
        return listView.getSelectObject();
    }

    private void initRemindAlterDialog() {
        mRemindBoxAlertDialog = mRemindBoxAlertDialog == null ?
                new RemindboxAlertDialog(this, new String[]{"不发送", "发送"}, "提醒", "是否发送短信", -1, this)
                : mRemindBoxAlertDialog;
    }

    @Override
    public void onClick(int position) {
        if (position == RemindboxAlertDialog.RIGHTBUTTON) {
            //发送短信
            List<Object> list = getSelectUserInfo();

            for (int i = 0; i < list.size(); i++) {
                UserInfo userInfo = (UserInfo) list.get(i);
                //如果短信内容过多，可以分条发送
                if(mValues.length()>=70)
                {
                    List<String> ms= SmsManager.getDefault().divideMessage(mValues);
                    for(String str:ms)
                    {
                        SmsManager.getDefault().sendTextMessage(userInfo.getPhoneNum().toString(), null, str, null, null);
                    }
                }
                else
                {
                    SmsManager.getDefault().sendTextMessage(userInfo.getPhoneNum().toString(), null, mValues, null, null);
                }
               /* Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:"+userInfo.getPhoneNum().toString()));
                intent.putExtra("sms_body",mValues);
                SmsMessageInviteMyFriendActivity.this.startActivity(intent);*/
            }
            MessageCenter.postInfoMessage("发送邀请短信成功");
            finish();
        }
    }
}
