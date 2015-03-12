package com.orange.barrage.android.user.mission;

import com.orange.protocol.message.UserProtos;

import java.util.List;

/**
 * Created by pipi on 15/3/12.
 */
public interface SearchUserCallback {

    void handleMessage(int errorCode, List<UserProtos.PBUser> pbUserList);
}
