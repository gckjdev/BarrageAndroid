package com.orange.barrage.android.user.model;

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
}
