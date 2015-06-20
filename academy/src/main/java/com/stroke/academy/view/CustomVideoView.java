/**
 * @(#)CustomVideoView.java, May 20, 2014. Copyright 2014 Yodao, Inc. All rights
 *                           reserved. YODAO PROPRIETARY/CONFIDENTIAL. Use is
 *                           subject to license terms.
 */
package com.stroke.academy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @author emilyu
 */
public class CustomVideoView extends VideoView {
    private OnPlayerListener mListener;

    public CustomVideoView(Context context) {
        super(context);

    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setOnPlayerListener(OnPlayerListener listener) {
        mListener = listener;
    }

    @Override
    public void pause() {
        super.pause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void start() {
        super.start();
        if (mListener != null) {
            mListener.onPlay();
        }

    }

    public interface OnPlayerListener {
        void onPlay();
        void onPause();
    }
}
