package com.orange.barrage.android.friend.model;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orange.barrage.android.util.misc.RandomUtil;
import com.orange.barrage.android.util.misc.StringUtil;
import com.orange.barrage.android.util.model.CommonManager;
import com.orange.barrage.android.util.persistent.ShareUserDBDAO;
import com.orange.protocol.message.UserProtos;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.util.Ln;

/**
 * Created by pipi on 15/3/5.
 */

@Singleton
public class TagManager extends CommonManager {

    // store KEY in DB
    private static final String KEY_USER_TAG_LIST = "KEY_USER_TAG_LIST";

    UserProtos.PBUserTagList mTagList;

    @Inject
    ShareUserDBDAO mUserDB;

    public boolean isTagNameExist(String name) {
        return false;
    }

    public void storeTags(UserProtos.PBUserTagList newTagList) {

        if (newTagList == null){
            return;
        }

        byte[] data = newTagList.toByteArray();
        if (data == null){
            return;
        }

        mUserDB.getDB().put(KEY_USER_TAG_LIST, data);
        mTagList = newTagList;
    }

    public UserProtos.PBUserTagList allTags(){
        if (mTagList == null){
            // load from storage
            loadTagListFromStroage();
        }

        return mTagList;
    }

    private UserProtos.PBUserTagList loadTagListFromStroage() {
        byte[] data = mUserDB.getDB().getBytes(KEY_USER_TAG_LIST);
        if (data == null){
            return null;
        }

        try {
            mTagList = UserProtos.PBUserTagList.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            Ln.e(e, "<loadTagListFromStroage> but catch exception = "+e.toString());
            return null;
        }

        return mTagList;
    }

    public void printTags(){
        UserProtos.PBUserTagList list = allTags();
        if (list == null){
            Ln.d("print tag but list is null");
            return;
        }

        Ln.d("<print tags> total %d tags", list.getTagsCount());
        int i=0;
        for (UserProtos.PBUserTag tag : list.getTagsList()){
            Ln.d("[%d] tag=%s", i, tag.toString());
            i++;
        }
    }

    public UserProtos.PBUserTag buildNewTag(String name){
        UserProtos.PBUserTag.Builder builder = UserProtos.PBUserTag.newBuilder();
        builder.setTid(StringUtil.createUUID());
        builder.setName(name);
        return builder.build();
    }

    public List<UserProtos.PBUser> userListByTag(UserProtos.PBUserTag tag){
        return null;
    }

    public UserProtos.PBUserTag getTagById(String tagId){
        return null;
    }
}
