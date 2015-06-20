package com.stroke.academy.common.util;

import android.content.Context;
import android.preference.PreferenceManager;

import com.stroke.academy.AcademyApplication;

public class PreferenceUtils {

    private static final Context CONTEXT = AcademyApplication.getInstance()
            .getApplicationContext();

    public static void putString(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit()
                .putString(key, value).commit();
    }

    public static void putBoolean(String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit()
                .putBoolean(key, value).commit();
    }
    
    public static String getString(String key) {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT)
                .getString(key, null);
    }

    public static void putInt(String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit()
                .putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT).getInt(
                key, -1);
    }
    
    public static int getInt(String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT).getInt(
                key, defaultValue);
    }
    
    public static boolean getBoolean(String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean(
                key, defaultValue);
    }
    
    public static boolean clear(String key) {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit().remove(key).commit();
    }
}
