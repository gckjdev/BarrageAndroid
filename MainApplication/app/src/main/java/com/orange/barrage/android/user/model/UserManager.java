package com.orange.barrage.android.user.model;

import com.orange.protocol.message.UserProtos;

/**
 * Created by pipi on 15/1/5.
 */
public class UserManager {
    private static UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager() {
    }

    public String getUserId(){
        return getUser().getUserId();
    }

    public UserProtos.PBUser getUser(){
        // hard code user data here
        UserProtos.PBUser.Builder builder = UserProtos.PBUser.newBuilder();
        builder.setUserId("5490ea5374f8383d999eb28b");
        builder.setNick("Mike Jodan");
        builder.setAvatar("https://wt-avatars.oss.aliyuncs.com/40/4744c7ab-81b7-44a0-9044-a78f2d126292.jpg");

        UserProtos.PBUser pbUser = builder.build();
        return pbUser;
    }
}
