package com.recyclerx.utils;

import android.util.Log;

public class LogUtil {
    // Do logs need to be visible ?
    private static final boolean DEBUG = false;

    public static void log(String tag, Object message) {
        if (DEBUG) {
            Log.i("Debug", "**/|| " + tag + " ||** ------------------------------------" + message + "\n");
        }
    }

    public static void checkpoint(Object message) {
        if (DEBUG) {
            Log.i("Debug", "**/|| " + "Checkpoint" + " ||** ------------------------------------" + message + "\n");
        }
    }

    public static void sysOut(Object message) {
        System.out.print(message.toString());
    }
}
