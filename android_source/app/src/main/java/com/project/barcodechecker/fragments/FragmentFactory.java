package com.project.barcodechecker.fragments;

import android.app.Fragment;

/**
 * Created by lucky on 08-Oct-17.
 */

public class FragmentFactory {
    private static HistoryFragment historyFragment = new HistoryFragment();
    private static CategoryFragment categoryFragment = new CategoryFragment();
    private static ScanFragment scanFragment = new ScanFragment();
    private static SearchFragment searchFragment = new SearchFragment();
    private static SettingFragment settingFragment = new SettingFragment();
//
//    public static Fragment getFragment(Class c) {
//        if (c == HistoryFragment.class) {
//         //   return historyFragment;
//        } else if (c == CategoryFragment.class) {
//           // return categoryFragment;
//        } else if (c == ScanFragment.class) {
//          //  return scanFragment;
//        } else if (c == SearchFragment.class) {
//         //   return searchFragment;
//        } else if (c == SettingFragment.class) {
//            return settingFragment;
//        } else {
//            return null;
//        }
//    }
}
