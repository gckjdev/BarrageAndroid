package com.orange.barrage.android.friend.mission.callback;

import com.orange.protocol.message.UserProtos;

import java.util.List;

/**
 * Created by pipi on 15/3/5.
 */
public interface AddTagCallback {
    void handleMessage(int errorCode, UserProtos.PBUserTag userTag);
}
