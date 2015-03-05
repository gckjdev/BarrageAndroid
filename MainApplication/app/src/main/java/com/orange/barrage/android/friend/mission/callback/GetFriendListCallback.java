package com.orange.barrage.android.friend.mission.callback;

import com.orange.protocol.message.UserProtos;

import java.util.List;

/**
 * Created by pipi on 15/3/3.
 */
public interface GetFriendListCallback {

    void handleMessage(int errorCode, UserProtos.PBUserFriendList friendList);

}
