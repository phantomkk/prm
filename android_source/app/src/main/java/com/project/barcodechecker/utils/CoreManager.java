package com.project.barcodechecker.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.project.barcodechecker.models.User;

/**
 * Created by lucky on 15-Oct-17.
 */

public class CoreManager {
    private static User mUser = null;

    public static User getUser(Context context) {
        mUser = Utils.getUserInSharePref(context);
        return mUser;
    }

    public static void setUser(Context context, User user) {
        Utils.saveUserInSharePref(context, user);
        mUser = user;
    }

}
