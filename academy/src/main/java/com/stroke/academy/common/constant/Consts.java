package com.stroke.academy.common.constant;

import android.os.Environment;

import com.stroke.academy.BuildConfig;

/**
 * Created by yuym on 4/2/15.
 */
public class Consts {

    /**
     * 是否是调试模式
     */
    public static boolean ON_DEBUG = BuildConfig.DEBUG;

    /**
     * 是否是测试模式
     */
    public static boolean ON_TEST_MODEl = false;

    /**
     * File Path *
     */
    public static final String ASSET_PATH_PREFIX = "file:///android_asset/";
    public static final String SDCARD_PATH_PREFIX = "file:///mnt/sdcard/";
    /*
     * 本地文件路径
     */
    public static final String MAIN_FILE_PATH = Environment.getExternalStorageDirectory() + "/ChineseAcademy/academy/";
    public static final String TEMP_FILE_PATH = MAIN_FILE_PATH + "temp/";
    public static final String AVATAR_CACHE_FILE = TEMP_FILE_PATH + "avatar.png";

    /*
     * 网络连接超时时间
     */
    public final static int TIMEOUT = 30 * 1000;

    /*
     * 退出间隔
     */
    public static final int INTERVAL = 2000;

    public static final int DEFAULT_PAGE_SIZE = 20;
    /*
     * 临时文件
     */
    public final static String TEMP_NAME = "temp.jpg";
    public static final String SHARE_CACHE_NAME = "share_tmp.png";

    /**
     * 切换帐号标记
     */
    public static final int SETTING_SWITCH_ACCOUNT_TAG = 0;

    /**
     * 退出登录标记
     */
    public static final int SETTING_LOGOUT_TAG = 1;
}
