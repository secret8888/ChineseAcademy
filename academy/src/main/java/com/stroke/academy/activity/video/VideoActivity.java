package com.stroke.academy.activity.video;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.IntentConsts;
import com.stroke.academy.common.constant.PreferenceConsts;
import com.stroke.academy.common.util.AES256;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.common.util.PreferenceUtils;
import com.stroke.academy.model.VideoItem;
import com.stroke.academy.view.VideoControllerView;

import org.w3c.dom.Text;

/**
 * http://video.weibo.com/show?fid=1034:8dfaaddc758ba9ab595da6269fecaf5d&type=mp4&reason=&retcode=&from=timeline&isappinstalled=0
 */
public class VideoActivity extends BaseActivity implements View.OnClickListener{

    @ViewId(R.id.tv_title)
    private TextView titleView;

    @ViewId(R.id.tv_back)
    private TextView backView;

    @ViewId(R.id.video_controller)
	private VideoControllerView videoControllerView;

    @ViewId(R.id.tv_speaker)
    private TextView speakerView;

    @ViewId(R.id.tv_content)
    private TextView contentView;

    private VideoItem videoItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void readIntent() {
        videoItem = (VideoItem) getIntent().getSerializableExtra(IntentConsts.VIDEO_ITEM_KEY);
    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        titleView.setText(videoItem.getName());
        speakerView.setText(String.format(getString(R.string.speaker), videoItem.getSpeaker()));
        contentView.setText(videoItem.getDescription());
        contentView.setMovementMethod(ScrollingMovementMethod.getInstance());

        if (!TextUtils.isEmpty(videoItem.getUrlios())) {
            videoControllerView.initPlayParams(videoItem.getUrlios(), videoItem.getImgURL());
        }
    }

    @Override
    protected void setListeners() {
        backView.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        videoControllerView.doPlayerStart();
        super.onResume();
    }

    @Override
    protected void onPause() {
        videoControllerView.doPlayerStop();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoControllerView.switchFullScreen();
            return;
        }
        videoControllerView.removeMessage();
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoControllerView.setVideoHeight(true);
        } else {
            videoControllerView.setVideoHeight(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                onBackPressed();
                break;
        }
    }
}
