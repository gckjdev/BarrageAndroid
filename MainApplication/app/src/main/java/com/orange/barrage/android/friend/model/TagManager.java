package com.orange.barrage.android.friend.model;

import com.orange.barrage.android.util.model.CommonManager;
import com.orange.protocol.message.UserProtos;

import javax.inject.Singleton;

/**
 * Created by pipi on 15/3/5.
 */

@Singleton
public class TagManager extends CommonManager {

    public boolean isTagNameExist(String name) {
        return false;
    }

    public void storeTags(UserProtos.PBUserTagList tagList) {
        
    }
}
