package com.project.barcodechecker.utils;

import android.os.Build;

/**
 * Created by lucky on 14-Sep-17.
 */

public class Utils {
    public static boolean isEmulator(){
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86");
    }
}
