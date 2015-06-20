package com.stroke.academy.view;

import java.util.Formatter;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.stroke.academy.R;
import com.stroke.academy.annotation.Injector;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.util.Logcat;
import com.stroke.academy.common.util.Utils;
import com.stroke.academy.common.util.ViewUtils;

/**
 * 播放插件
 * 
 * @author yuym
 */
public class VideoControllerView extends RelativeLayout implements
        CustomVideoView.OnPlayerListener, MediaPlayer.OnPreparedListener,
        OnClickListener, OnTouchListener, OnSeekBarChangeListener, AnimationListener {

    @ViewId(R.id.lv_video_view)
    private FrameLayout videoViewLayout = null;
    
    @ViewId(R.id.video_view)
    private CustomVideoView videoView = null;

    @ViewId(R.id.lv_controller_bar)
    private RelativeLayout controlBarLayout = null;

    @ViewId(R.id.im_play)
    private ImageView playView = null;

    @ViewId(R.id.seek_bar)
    private SeekBar seekBar = null;

    @ViewId(R.id.tv_time)
    private TextView timeView;
    
    @ViewId(R.id.im_full_screen)
    private ImageView fullScreenView;
    
    @ViewId(R.id.im_pack_up)
    private ImageView packUpView;

    @ViewId(R.id.pbar_loading)
    private ProgressBar loadingBar;

    private boolean isDragging;
    
    private float paremsHeight;
    
    private int positionWhenPaused = -1;
    
    private boolean isPackUp = false;
    
    private boolean isLandscape = false;

    private boolean isPrepared = false;

    private StringBuilder formatBuilder = new StringBuilder();
    
    private Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());
    
    private static final int UPDATE_PROGRESS = 0;
    
    public VideoControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoControllerView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_video_controller, this, true);
        Injector.inject(this, this);
        setListeners();
    }

    public void setVideoHeight(boolean isLandscape){
    	this.isLandscape = isLandscape;
        if(isLandscape){
            int statusBarHeight = ViewUtils.getStatusBarHeight((Activity) getContext());
            videoViewLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
                    Utils.getScreenWidth(getContext()) - statusBarHeight));
        }else{
            LayoutParams layoutParams = (LayoutParams)videoViewLayout.getLayoutParams();
            layoutParams.height = (int)paremsHeight + 1;
            videoViewLayout.setLayoutParams(layoutParams);
        }
    }
    
    @SuppressWarnings("deprecation")
    private void setVideoThumbnail(String url){
        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                Bitmap bitmap = null;
//                bitmap = ThumbnailUtils.createVideoThumbnail(path,
//                        Thumbnails.FULL_SCREEN_KIND);
                videoView.setBackgroundDrawable(new BitmapDrawable(bitmap));
                int videoWidth = bitmap.getWidth();
                int videoHeight = bitmap.getHeight();
                Logcat.d("TAG", "videoWidth : " + videoWidth);
                Logcat.d("TAG", "videoHeight : " + videoHeight);
                paremsHeight = videoHeight * (float) Utils.getScreenWidth(getContext()) / videoWidth;
                setVideoHeight(false);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }
    
    private void setListeners(){
        videoView.setOnPlayerListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnTouchListener(this);
        playView.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        fullScreenView.setOnClickListener(this);
        packUpView.setOnClickListener(this);
    }
    
    public void initPlayParams(String path, String imageUrl){
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        setVideoThumbnail(imageUrl);
        
        videoView.seekTo(0);
        long duration = videoView.getDuration();
        timeView.setText("00:00/" + stringForTime((int) duration));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlay() {
//        videoView.setBackgroundDrawable(null);
    }

    @Override
    public void onPause() {
        
    }

    public void removeMessage(){
        handler.removeMessages(UPDATE_PROGRESS);
    }
    
    public void switchFullScreen(){
        fullScreenView.performClick();
    }
    
    private int setProgress() {
        if (isDragging) {
            return 0;
        }
        int position = videoView.getCurrentPosition();
        int duration = videoView.getDuration();
        if (seekBar != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                seekBar.setProgress((int) pos);
            }
        }

        if (timeView != null) {
            timeView.setText(stringForTime(position) + "/" + stringForTime(duration));
        }
        
        if (position >= duration) {
            stop();
        }
        return position;
    }
    
    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        formatBuilder.setLength(0);
        if (hours > 0) {
            return formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return formatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
    
    public void play(){
        videoView.start();
        playView.setImageResource(R.drawable.dict_video_pause);
        setProgress();
        handler.sendEmptyMessage(UPDATE_PROGRESS);
    }
    
    public void stop(){
        videoView.pause();
        playView.setImageResource(R.drawable.dict_video_play);
        handler.removeMessages(UPDATE_PROGRESS);
    }
    
    public void doPlayerStop(){
        positionWhenPaused = videoView.getCurrentPosition();
        stop();
    }
    
    public void doPlayerStart(){
        if(positionWhenPaused >= 0) {
            videoView.seekTo(positionWhenPaused);
            positionWhenPaused = -1;
        }
    }
    
    public void doPausePlay(){
        if(videoView.isPlaying()){
            stop();
        }else{
            if(!isPrepared) {
                loadingBar.setVisibility(View.VISIBLE);
            } else {
                videoView.setBackgroundDrawable(null);
                play();
            }
        }
    }
    
    public void videoPackUp(){
    	if(isPackUp){
    		return;
    	}
    	isPackUp = true;
		packUpView.setImageResource(R.drawable.ic_pack_down);
		LayoutParams layoutParams = (LayoutParams)videoViewLayout.getLayoutParams();
        layoutParams.height = Utils.dip2px(getContext(), 35);
        videoViewLayout.setLayoutParams(layoutParams);
        controlBarLayout.setVisibility(View.VISIBLE);
    }
    
    public void videoPackDown(){
    	if(!isPackUp){
    		return;
    	}
    	isPackUp = false;
		packUpView.setImageResource(R.drawable.ic_pack_up);
		setVideoHeight(isLandscape);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_full_screen:
                if(((Activity)getContext()).getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
                    ((Activity)getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else{
                    ((Activity)getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
            case R.id.im_play:
                doPausePlay();
                break;
            case R.id.im_pack_up:
            	if(!isPackUp){
            		videoPackUp();
            	}else{
            		videoPackDown();
            	}            	
            	break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(controlBarLayout.getVisibility() == View.VISIBLE){
            Animation downAnimation = AnimationUtils.loadAnimation(
                    getContext(), R.anim.push_bottom_out);
            controlBarLayout.startAnimation(downAnimation);
            downAnimation.setAnimationListener(this);
            controlBarLayout.setVisibility(View.GONE);
        }else{
            controlBarLayout.setVisibility(View.VISIBLE);
            Animation upAnimation = AnimationUtils.loadAnimation(
                    getContext(), R.anim.push_bottom_in);
            controlBarLayout.startAnimation(upAnimation);
        }
        return false;
    }
    
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        if (!fromUser) {
            return;
        }

        long duration = videoView.getDuration();
        long newposition = (duration * progress) / 1000L;
        videoView.seekTo((int) newposition);
        timeView.setText(stringForTime((int) newposition) + "/" + stringForTime((int) duration));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isDragging = true;
        handler.removeMessages(UPDATE_PROGRESS);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isDragging = false;        
        play();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        isPrepared = true;
        if(loadingBar.getVisibility() == View.VISIBLE) {
            videoView.setBackgroundDrawable(null);
            loadingBar.setVisibility(View.GONE);
            play();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    int pos = setProgress();
                    if (!isDragging && videoView.isPlaying()) {
                        msg = obtainMessage(UPDATE_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                    }
                    break;
            }
        }
    };

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {
        controlBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
