package com.orange.barrage.android.user.model;

import com.orange.barrage.android.util.model.CommonManager;
import com.orange.barrage.android.util.persistent.barrage.DefaultDBDAO;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.inject.ContextSingleton;

/**
 * Created by pipi on 15/1/5.
 */
@Singleton
public class UserManager extends CommonManager {

    public static final String KEY_USER_DATA = "KEY_USER_DATA";

    @Inject
    DefaultDBDAO defaultDBDAO;

    public String getUserId(){
        UserProtos.PBUser user = getUser();
        if (user == null){
            return null;
        }

        return user.getUserId();
    }

    public UserProtos.PBUser getUser(){

        UserProtos.PBUser pbUser = defaultDBDAO.getPB(KEY_USER_DATA, UserProtos.PBUser.class);
        if (pbUser != null){
            return pbUser;
        }
        else{
            return null;
        }

// hard code user data here
//        UserProtos.PBUser.Builder builder = UserProtos.PBUser.newBuilder();
//        builder.setUserId("5490ea5374f8383d999eb28b");
//        builder.setNick("Mike Jodan");
//        builder.setAvatar("https://wt-avatars.oss.aliyuncs.com/40/4744c7ab-81b7-44a0-9044-a78f2d126292.jpg");
//
//        pbUser = builder.build();
//        return pbUser;
    }

    public void storeUser(UserProtos.PBUser pbUser) {
        if (pbUser == null){
            return;
        }

        defaultDBDAO.put(KEY_USER_DATA, pbUser);
    }

    public boolean hasUser() {
        UserProtos.PBUser user = getUser();
        if (user == null) {
            return false;
        }
        else{
            return true;
        }
    }
}
