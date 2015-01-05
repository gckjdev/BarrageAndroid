package com.orange.barrage.android.user.mission;

import com.orange.protocol.message.MessageProtos;
import com.orange.protocol.message.UserProtos;

/**
 * Created by pipi on 15/1/5.
 */
public interface UserMissionCallback {

    void handleMessage(int errorCode, UserProtos.PBUser pbUser);
}
