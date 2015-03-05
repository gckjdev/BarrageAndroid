package com.orange.barrage.android.friend.mission;

import com.orange.barrage.android.friend.mission.callback.FriendMissionDefaultCallback;
import com.orange.barrage.android.friend.mission.callback.GetFriendListCallback;
import com.orange.barrage.android.util.model.CommonMission;
import com.orange.protocol.message.MessageProtos;
import com.orange.protocol.message.UserProtos;

/**
 * Created by pipi on 15/3/5.
 */
public class FriendMission extends CommonMission {

    // 获取好友列表
    public void getFriendList(final int type, final GetFriendListCallback callback){
    }

    // 同意好友
    public void addFriend(final MessageProtos.PBAddUserFriendRequest addRequest, final FriendMissionDefaultCallback callback){
    }

    // 删除好友
    public void deleteFriend(final UserProtos.PBUser pbFriend, final FriendMissionDefaultCallback callback){
    }

    // 处理好友请求（同意、拒绝等）
    public void processUserFriendRequest(final MessageProtos.PBProcessUserFriendRequest request, final FriendMissionDefaultCallback callback){
    }

    // 同步用户好友列表
    public void syncFriend(final GetFriendListCallback callback){
    }

    // 获得用户的好友请求列表
    public void getFriendRequestList(final GetFriendListCallback callback){
    }

}
