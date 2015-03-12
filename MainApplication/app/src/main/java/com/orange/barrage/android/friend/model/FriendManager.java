package com.orange.barrage.android.friend.model;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.user.model.UserManager;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.barrage.android.util.model.CommonManager;
import com.orange.barrage.android.util.persistent.ShareUserDBDAO;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.util.Ln;

/**
 * Created by pipi on 15/3/3.
 */

@Singleton
public class FriendManager extends CommonManager {

    private static final String KEY_USER_FRIEND_LIST = "KEY_USER_FRIEND_LIST";
    UserProtos.PBUserFriendList mFriendList;

    @Inject
    ShareUserDBDAO mUserDB;

    @Inject
    UserManager mUserManager;

    public void storeFriendList(UserProtos.PBUserFriendList newFriendList) {

        if (newFriendList == null){
            return;
        }

        byte[] data = newFriendList.toByteArray();
        if (data == null){
            return;
        }

        mUserDB.getDB().put(KEY_USER_FRIEND_LIST, data);
        mFriendList = newFriendList;
    }

    public void storeOnlyFriendList(List<UserProtos.PBUser> friendsList) {

        UserProtos.PBUserFriendList current = loadFriendListFromStroage();

        UserProtos.PBUserFriendList.Builder builder = UserProtos.PBUserFriendList.newBuilder();
        if (current != null){
            builder = UserProtos.PBUserFriendList.newBuilder(current);
        }

        if (friendsList != null) {
            builder.addAllFriends(friendsList);
        }
        else{
            builder.clearFriends();
        }

        storeFriendList(builder.build());
    }

    public void storeRequestFriendList(List<UserProtos.PBUser> requestFriendsList, int requestFriendsCount) {
        UserProtos.PBUserFriendList current = loadFriendListFromStroage();

        UserProtos.PBUserFriendList.Builder builder = UserProtos.PBUserFriendList.newBuilder();
        if (current != null){
            builder = UserProtos.PBUserFriendList.newBuilder(current);
        }

        if (requestFriendsList != null) {
            builder.addAllRequestFriends(requestFriendsList);
        }
        else{
            builder.clearRequestFriends();
        }
        builder.setRequestNewCount(requestFriendsCount);

        storeFriendList(builder.build());
    }

    private UserProtos.PBUserFriendList loadFriendListFromStroage() {

        byte[] data = mUserDB.getDB().getBytes(KEY_USER_FRIEND_LIST);
        if (data == null){
            return null;
        }

        UserProtos.PBUserFriendList list = null;
        try {
            list = UserProtos.PBUserFriendList.parseFrom(data);
            mFriendList = list;
        } catch (InvalidProtocolBufferException e) {
            Ln.e(e, "<loadFriendListFromStroage> but catch exception="+e.toString());
        }
        return list;
    }

    public void printFriendList(){
        UserProtos.PBUserFriendList list = loadFriendListFromStroage();
        Ln.d("<print friend list> total %d friends", list.getFriendsCount());
        int i=0;
        for (UserProtos.PBUser user : list.getFriendsList()){
            Ln.d("[%d] user=%s", i, user.toString());
            i++;
        }
    }

    private UserProtos.PBUserFriendList getUserFriendList(){
        if (mFriendList != null){
            return mFriendList;
        }

        return loadFriendListFromStroage();
    }

    public UserProtos.PBUserFriendList getAllList() {
        return getUserFriendList();
    }

    public List<UserProtos.PBUser> getFriendList() {
        UserProtos.PBUserFriendList list = getUserFriendList();
        if (list == null){
            return null;
        }

        return list.getFriendsList();
    }

    public List<UserProtos.PBUser> getRequestFriendList() {
        UserProtos.PBUserFriendList list = getUserFriendList();
        if (list == null){
            return null;
        }

        return list.getRequestFriendsList();
    }

    public int getRequestNewFriendCount(){
        UserProtos.PBUserFriendList list = getUserFriendList();
        if (list == null){
            return 0;
        }

        return list.getRequestNewCount();
    }

    public boolean isFriend(String userId){
        if (StringUtil.isEmpty(userId)){
            return false;
        }

        if (mUserManager.isMe(userId)){
            return true;
        }

        List<UserProtos.PBUser> friendList = getFriendList();
        for (UserProtos.PBUser user : friendList){
            if (userId.equalsIgnoreCase(user.getUserId())){
                return true;
            }
        }

        return false;
    }

    public UserProtos.PBUser getUserByFriendId(String friendId){
        if (StringUtil.isEmpty(friendId)){
            return null;
        }

        List<UserProtos.PBUser> friendList = getFriendList();
        //第一个参数是类型，第二个参数是名称，第三个参数是需要遍历的集合
        for (UserProtos.PBUser user : friendList){
            if (friendId.equalsIgnoreCase(user.getUserId())){
                return user;
            }
        }

        return null;
    }



}
