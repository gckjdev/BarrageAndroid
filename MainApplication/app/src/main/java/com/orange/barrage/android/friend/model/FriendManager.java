package com.orange.barrage.android.friend.model;

import com.orange.barrage.android.util.model.CommonManager;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Singleton;

/**
 * Created by pipi on 15/3/3.
 */

@Singleton
public class FriendManager extends CommonManager {
    public void storeFriendList(UserProtos.PBUserFriendList friendList) {

    }

    public void storeOnlyFriendList(List<UserProtos.PBUser> friendsList) {

    }

    public void storeRequestFriendList(List<UserProtos.PBUser> requestFriendsList, int requestFriendsCount) {

    }

    public List<UserProtos.PBUser> getFriendList() {
        return null;
    }

    public UserProtos.PBUserFriendList getAllList() {
        return null;
    }
}
