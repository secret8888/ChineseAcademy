package com.stroke.academy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.annotation.Injector;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.util.IntentManager;

/**
 * Created by emilyu on 6/17/15.
 */
public class MainNewsView extends RelativeLayout {

    @ViewId(R.id.im_index)
    private ImageView indexView;

    @ViewId(R.id.tv_title)
    private TextView titleView;

    @ViewId(R.id.tv_content)
    private TextView contentView;

    private String mTitle;

    private String mUrl;

    public MainNewsView(Context context) {
        this(context, null);
    }

    public MainNewsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainNewsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_news_item, this);
        Injector.inject(this, this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.startWebviewActivity(getContext(), mTitle, mUrl);
            }
        });
    }

    public void setData(int resId, String title, String content, String url) {
        mUrl = url;
        mTitle = title;
        indexView.setImageResource(resId);
        titleView.setText(title);
        contentView.setText(content);
    }
}
