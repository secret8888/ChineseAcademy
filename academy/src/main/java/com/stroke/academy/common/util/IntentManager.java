package com.stroke.academy.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.stroke.academy.activity.LoginActivity;
import com.stroke.academy.activity.MainActivity;
import com.stroke.academy.activity.WaitingActivity;
import com.stroke.academy.activity.message.MessageDetailActivity;
import com.stroke.academy.activity.setting.HelpActivity;
import com.stroke.academy.activity.setting.QrCodeActivity;
import com.stroke.academy.activity.setting.SettingActivity;
import com.stroke.academy.activity.article.ArticleInfoActivity;
import com.stroke.academy.activity.article.ArticleListActivity;
import com.stroke.academy.activity.WebviewActivity;
import com.stroke.academy.activity.video.MeetingDayActivity;
import com.stroke.academy.activity.video.MeetingListActivity;
import com.stroke.academy.activity.video.VideoActivity;
import com.stroke.academy.activity.video.VideoListActivity;
import com.stroke.academy.common.constant.IntentConsts;
import com.stroke.academy.model.MessageItem;
import com.stroke.academy.model.VideoItem;

/**
 * intent manager for the whole application
 * Created by yuym on 4/15/15.
 */
public class IntentManager {


    /**
     * start guide activity
     *
     * @param context
     */
    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * start login activity
     *
     * @param context
     */
    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * start setting activity
     * @param context
     */
    public static void startSettingActivity(Activity context, int requestCode) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * start help activity
     * @param context
     */
    public static void startHelpActivity(Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
        context.startActivity(intent);
    }

    /**
     * start qr code activity
     * @param context
     */
    public static void startQrCodeActivity(Context context) {
        Intent intent = new Intent(context, QrCodeActivity.class);
        context.startActivity(intent);
    }

    /**
     * start webveiw activity
     * @param context
     * @param title
     * @param url
     */
    public static void startWebviewActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(IntentConsts.TITLE_KEY, title);
        intent.putExtra(IntentConsts.URL_KEY, url);
        context.startActivity(intent);
    }

    /**
     * start waiting activity
     * @param context
     * @param title
     */
    public static void startWaitingActivity(Context context, String title) {
        Intent intent = new Intent(context, WaitingActivity.class);
        intent.putExtra(IntentConsts.TITLE_KEY, title);
        context.startActivity(intent);
    }

    /**
     * start video activity
     * @param context
     */
    public static void startVideoActivity(Context context, VideoItem videoItem) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(IntentConsts.VIDEO_ITEM_KEY, videoItem);
        context.startActivity(intent);
    }

    /**
     * start meeting day list activity
     * @param context
     */
    public static void startMeetingDayActivity(Context context) {
        Intent intent = new Intent(context, MeetingDayActivity.class);
        context.startActivity(intent);
    }

    /**
     * start meeting list activity
     *
     * @param context
     */
    public static void startMeetingListActivity(Context context, String meetingId, String meetingStr) {
        Intent intent = new Intent(context, MeetingListActivity.class);
        intent.putExtra(IntentConsts.MEETING_ID_KEY, meetingId);
        intent.putExtra(IntentConsts.MEETING_STR_KEY, meetingStr);
        context.startActivity(intent);
    }

    /**
     * start video list activity
     *
     * @param context
     * @param meetingId
     * @param dayId
     */
    public static void startVideoListActivity(Context context, String meetingId, String dayId) {
        Intent intent = new Intent(context, VideoListActivity.class);
        intent.putExtra(IntentConsts.MEETING_ID_KEY, meetingId);
        intent.putExtra(IntentConsts.DAY_ID_KEY, dayId);
        context.startActivity(intent);
    }

    /**
     * start article activity
     *
     * @param context
     */
    public static void startArticleListActivity(Context context) {
        Intent intent = new Intent(context, ArticleListActivity.class);
        context.startActivity(intent);
    }

    /**
     * start article info activity
     * @param context
     * @param id
     */
    public static void startArticleInfoActivity(Context context, String id) {
        Intent intent = new Intent(context, ArticleInfoActivity.class);
        intent.putExtra(IntentConsts.ID_KEY, id);
        context.startActivity(intent);
    }

    public static void startMessageDetailActivity(Context context, MessageItem item) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra(IntentConsts.MESSAGE_ITEM_KEY, item);
        context.startActivity(intent);
    }
}
