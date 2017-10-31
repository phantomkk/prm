package com.project.barcodechecker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.project.barcodechecker.R;
import com.project.barcodechecker.models.User;

/**
 * Created by lucky on 14-Sep-17.
 */

public class Utils {

    private static final String PREF_NAME = "ACCOUNT";
    private static final String USER_KEY = "USER_KEY";

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86");
    }

    public static void saveUserInSharePref(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(USER_KEY, gson.toJson(user));
        editor.apply();
    }

    public static User getUserInSharePref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonUser = sharedPreferences.getString(USER_KEY, null);
        Gson gson = new Gson();
        User u = gson.fromJson(jsonUser, User.class);
        return u;
    }

    public static void checkPassword(Context context, EditText editText, ProgressBar progressBar) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0)
            progressBar.setProgress(0);
        else if (s.length() < 3) {
            progressBar.setProgress(25);
            progressBar.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.color_red_500)));
        } else if (s.length() < 8) {
            progressBar.setProgress(50);
            progressBar.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.color_orange_500)));
        } else if (s.length() < 10) {
            progressBar.setProgress(75);
            progressBar.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.color_yellow_500)));
        } else {
            progressBar.setProgress(100);
            progressBar.setProgressTintList(ColorStateList.valueOf(context.getColor(R.color.color_green_500)));
        }
    }

    public static boolean checkEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean checkPhone(String phone) {
        if (phone == null) {
            return false;
        } else {
            return Patterns.PHONE.matcher(phone).matches();
        }
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }
    }

    public static boolean setGridViewHeightBasedOnItems(GridView gridView) {

        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, gridView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = gridView.getHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            gridView.setLayoutParams(params);
            gridView.requestLayout();

            return true;

        } else {
            return false;
        }
    }
}
