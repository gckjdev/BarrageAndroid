package com.orange.barrage.android.friend.mission;

import com.orange.barrage.android.friend.mission.callback.AddTagCallback;
import com.orange.barrage.android.friend.mission.callback.GetTagListCallback;
import com.orange.barrage.android.util.model.CommonMission;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Singleton;

import roboguice.inject.ContextSingleton;

/**
 * Created by pipi on 15/3/3.
 */

@Singleton
public class TagMission extends CommonMission {

    public void addTag(final UserProtos.PBUserTag tag, final AddTagCallback callback){

    }

    // 添加用户到某个tag
    public void addUsersToTag(final UserProtos.PBUserTag origTag, final List<String> addUserIds, final AddTagCallback callback){

    }

    // 从标签删除某个用户
    public void removeUserFromTag(final UserProtos.PBUserTag origTag, final List<String> removeUserIds, final AddTagCallback callback){

    }

    // 在已有标签情况下，把新的userList更新到tag
    public void updateUserTag(final UserProtos.PBUserTag origTag, final List<String> currentUserIds, final AddTagCallback callback){

    }

    // 删除标签
    public void deleteTag(final UserProtos.PBUserTag tag, final AddTagCallback callback){
    }

    // 获取用户所有标签
    public void getAllTags(final GetTagListCallback callback){

    }





}
