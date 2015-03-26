package com.orange.barrage.android.user.ui.user_home;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

public class SmsMessageInviteMyFriendActivity extends BarrageCommonActivity{
    private static final String AVAILINVITENUMBER="avail_number";
    @InjectView(R.id.listView1)
    private ListView listView;

    private List<UserInfo> addressList;

    private UserInfo info;
    private Uri[] c = new Uri[]{ContactsContract.Contacts.CONTENT_URI,
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    };

    //得到系统联系人的方法
    public void getSystemContacts() {
        addressList = new ArrayList<UserInfo>();

        String contactsID;
        Cursor cursor = getContentResolver().query(c[0], null, null, null, null);
        while(cursor.moveToNext()){
            info = new UserInfo();
            contactsID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String userName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            info.setUserName(userName);
            Cursor cursor2 = getContentResolver().query(c[1], null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactsID, null, null);
            while(cursor2.moveToNext()){
                String userPhone = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                info.setPhoneNum(userPhone);
            }
            cursor2.close();
            addressList.add(info);
        }
    }
    class BBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return addressList.size();
        }

        @Override
        public Object getItem(int position) {
            return addressList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder ;
            if(convertView == null){
                convertView = getLayoutInflater().from(SmsMessageInviteMyFriendActivity.this).inflate(R.layout.invite_myfriend_listitem, null);
                holder = new Holder();
                holder.userName = (TextView)convertView.findViewById(R.id.userName);
                holder.userPhone = (TextView)convertView.findViewById(R.id.userPhone);
                holder.imageView=(ImageView)convertView.findViewById(R.id.imageview_check);
                convertView.setTag(holder);
            }else{
                holder = (Holder)convertView.getTag();
            }
            holder.userName.setText(addressList.get(position).getUserName());
            holder.userPhone.setText(addressList.get(position).getPhoneNum());
            holder.imageView.setImageResource(R.drawable.checkbox_checked);
            return convertView;
        }
        class Holder{
            TextView userName,userPhone;
            Button inviteButton;
            ImageView imageView;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_sms_message_invite_my_friend,"短信邀请",R.string.b_send);
        getSystemContacts();
        Intent intent=getIntent();
        String value=intent.getStringExtra(AVAILINVITENUMBER);
        //MessageCenter.postInfoMessage("邀请码为:"+value);
        listView.setAdapter(new BBaseAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  final String phonenumber=addressList.get(position).getPhoneNum();
                 if (view.getTag() instanceof BBaseAdapter.Holder)
                 {
                     BBaseAdapter.Holder holder=(BBaseAdapter.Holder)view.getTag();
                    holder.imageView.setImageResource(R.drawable.checkbox_checked);
                    // new BBaseAdapter().notifyDataSetChanged();
                 }
            }
        });
    }
}
