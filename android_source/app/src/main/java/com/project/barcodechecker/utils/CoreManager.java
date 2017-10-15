package com.project.barcodechecker.utils;

import com.project.barcodechecker.models.User;

/**
 * Created by lucky on 15-Oct-17.
 */

public class CoreManager {
    private static User mUser = null;

    public static User getUser() {
        return mUser;
    }

    public static void setUser(User user) {
        mUser = user;
    }
}
