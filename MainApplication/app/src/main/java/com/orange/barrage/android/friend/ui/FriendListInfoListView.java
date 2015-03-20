package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.model.FriendManager;
import com.orange.barrage.android.user.ui.view.UserAvatarView;
import com.orange.protocol.message.UserProtos;


import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;


/**
 * Created by Administrator on 2015/3/20.
 */
public class FriendListInfoListView extends ListView implements SimpleAdapter.ViewBinder, View.OnClickListener {

    public static final int FRIENDLIST_AND_SELECT = 1;

    public static final int FRIENDLIST_AND_ORDINARY = 2;

    public static final String LETTE = "&&&||||";

    private int mType;
    @Inject
    FriendManager mFriendManager;




    private SimpleAdapter mAdapter;

    private List<Map<String, Object>> data = new ArrayList<>();
    private Vector<UserProtos.PBUser> mVectorUserSelect = new Vector<>();
    private SortParam mSortParams[] = null;

    public FriendListInfoListView(Context context) {
        super(context);
    }

    public FriendListInfoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public FriendListInfoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//    public void setUserInfo( List<UserProtos.PBUser> PBUsers){
////        List<UserProtos.PBUser> PBUsers = mFriendManager.getFriendList();
//
//
//        for (UserProtos.PBUser PBUser :PBUsers){
//            Map<String , Object> map = new HashMap<>();
//            map.put("1",PBUser);
//            map.put("2" , PBUser.getNick());
//            map.put("3" , PBUser.getSignature());
//            map.put("4" , false);
//            map.put("5" , LETTE);
//            data.add(map);
//        }
//
//        setAdapter();
//    }


    public void setUserInfo() {

        List<UserProtos.PBUser> PBUsers = mFriendManager.getFriendList();
        for(UserProtos.PBUser PBUser : PBUsers)
        addData(PBUser);

        setAdapter();
    }

    private void addData(UserProtos.PBUser PBUser) {

        Map<String, Object> map = new HashMap<>();
        map.put("1", PBUser);
        map.put("2", PBUser.getNick());
        map.put("3", "");
        map.put("4", "");
        data.add(map);
    }


    private void setAdapter() {
        mAdapter = new SimpleAdapter(getContext(), data,
                R.layout.fragment_friend_home_listitem,
                new String[]{"1", "2", "3" ,"4"},
                new int[]{R.id.friend_avatar_view, R.id.friend_nick, R.id.checkBox , R.id.friend_signature});

        mAdapter.setViewBinder(this);
        setAdapter(mAdapter);
    }


    private char[] dealWord(String s) {
        char cs[] = s.toCharArray();
        char letter[] = new char[cs.length];

        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == ' ') continue;
//            String s[] = PinyinHelper


            String S[] = PinyinHelper.toHanyuPinyinStringArray(cs[i]);

            if (S == null) return cs;
            letter[i] = S[0].charAt(0);
        }
        return letter;
    }


    public Vector<UserProtos.PBUser> getSelectedUser() {
        return mVectorUserSelect;
    }


    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {


        if (view instanceof UserAvatarView && data != null) {
            UserProtos.PBUser pbUser = (UserProtos.PBUser) data;
            ((UserAvatarView) view).loadUser(pbUser);
            if (view.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                viewGroup.setTag(pbUser);
                viewGroup.setOnClickListener(this);
            }
            return true;
        } else if (textRepresentation.length() == 0 && view instanceof ImageView) {
            view.setVisibility(VISIBLE);
            return true;
        }

        return false;
    }


//        if(textRepresentation.equals(LETTE)) {
//
//            //设置英文字母
//            TextView textView = (TextView) view;
//            textView.setVisibility(GONE);
//            Ln.e("显示字母出现");
//            if(mSortParam.isFirst){
//               textView.setText(mSortParam.firstLetter[0] +"");
//               textView.setVisibility(VISIBLE);
//                Ln.e(mSortParam.firstLetter[0] +"");
//            }
//            return true;
//        }
//        if(view instanceof UserAvatarView && data != null){
//            UserProtos.PBUser pbUser =(UserProtos.PBUser) mSortParam.obj;
//            ((UserAvatarView)view).loadUser(pbUser);
//            Ln.e("我显示了："+pbUser.getAvatar());
//            if(view.getParent() instanceof  ViewGroup){
//                ViewGroup viewGroup = (ViewGroup)view.getParent();
//                viewGroup.setTag(pbUser);
//                viewGroup.setOnClickListener(this);
//            }
//            return true;
//        }else if(view instanceof  TextView){
//            Ln.e("我被显示");
//            view.setVisibility(VISIBLE);
//        }
//        return false;


    @Override
    public void onClick(View v) {

        final ImageView textView = (ImageView) v.findViewById(R.id.checkBox);



        if (textView.getTag() == null || textView.getTag().equals("N")) {
            textView.setImageResource(R.drawable.x_friendlist_select);
            mVectorUserSelect.add((UserProtos.PBUser) v.getTag());
            textView.setTag("Y");
        } else if (textView.getTag() != null && textView.getTag().equals("Y")) {
            textView.setImageResource(R.drawable.x_comments_white);
            mVectorUserSelect.remove(v.getTag());
            textView.setTag("N");
        }
    }


    static class SortParam {

        public boolean isFirst = false;

        public Object obj;

        public char[] firstLetter;


        public static void sort(SortParam[] sortParams) {

            boolean is = true;

            while (is) {

                for (int i = 0; i < sortParams.length - 1; i++) {
                    sortParams[i + 1].isFirst = false;
                    is = bijiao(sortParams, i, 0);
                }

            }


        }

        static private boolean bijiao(SortParam[] sortParams, int i, int letteId) {

            if (sortParams[i].firstLetter[letteId] > sortParams[i + 1].firstLetter[letteId]) {
                change(sortParams, i);

                return true;
            } else if (sortParams[i].firstLetter[letteId] == sortParams[i + 1].firstLetter[letteId]) {
                return bijiao(sortParams, i, letteId + 1);
            } else {
                if (letteId == 0)
                    sortParams[i + 1].isFirst = true;
            }
            return false;
        }

        static private void change(SortParam[] sortParams, int i) {
            SortParam sortParam = sortParams[i];
            sortParams[i] = sortParams[i + 1];
            sortParams[i + 1] = sortParam;
        }


    }

    public List<UserProtos.PBUser> getAddPBUser(){
        return mVectorUserSelect;
    }





}
