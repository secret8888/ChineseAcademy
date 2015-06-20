package com.stroke.academy.activity.video;

import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.activity.base.BaseFragmentActivity;
import com.stroke.academy.adapter.MeetingDayAdapter;
import com.stroke.academy.adapter.MeetingFragmentAdapter;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.Consts;
import com.stroke.academy.common.constant.PreferenceConsts;
import com.stroke.academy.common.http.AcademyHandler;
import com.stroke.academy.common.http.HttpManager;
import com.stroke.academy.common.util.AES256;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.common.util.Logcat;
import com.stroke.academy.common.util.PreferenceUtils;
import com.stroke.academy.common.util.Utils;
import com.stroke.academy.model.HandleInfo;
import com.stroke.academy.model.MeetingDayData;
import com.stroke.academy.view.refresh.RefreshListView;
import com.youdao.yjson.YJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilyu on 6/11/15.
 */
public class MeetingDayActivity extends BaseFragmentActivity implements
        View.OnClickListener {

    @ViewId(R.id.tv_title)
    private TextView titleView;

    @ViewId(R.id.tv_back)
    private TextView backView;

    @ViewId(R.id.pager_content)
    private ViewPager contentPager;

    @ViewId(R.id.im_back_first)
    private ImageView backFirstView;

    @ViewId(R.id.im_last)
    private ImageView lastView;

    @ViewId(R.id.im_back_last)
    private ImageView backLastView;

    @ViewId(R.id.im_next)
    private ImageView nextView;

    @ViewId(R.id.lv_index)
    private LinearLayout indexLayout;

    private MeetingFragmentAdapter mAdapter;

    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_video;
    }

    @Override
    protected void readIntent() {
    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        titleView.setText(R.string.meeting_video);
        onShowLoadingDialog();
        getMeetingDays(null);
    }

    @Override
    protected void setListeners() {
        backView.setOnClickListener(this);
        backFirstView.setOnClickListener(this);
        lastView.setOnClickListener(this);
        backLastView.setOnClickListener(this);
        nextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.im_back_first:
                contentPager.setCurrentItem(0);
                setIndexSelected(0);
                break;
            case R.id.im_last:
                int currentIndex = contentPager.getCurrentItem();
                currentIndex --;
                if(currentIndex < 0) {
                    currentIndex = 0;
                }
                contentPager.setCurrentItem(currentIndex);
                setIndexSelected(currentIndex);
                break;
            case R.id.im_back_last:
                contentPager.setCurrentItem(contentPager.getChildCount() - 1);
                setIndexSelected(contentPager.getChildCount() - 1);
                break;
            case R.id.im_next:
                int index = contentPager.getCurrentItem();
                index ++;
                if(index > contentPager.getChildCount() - 1) {
                    index = contentPager.getChildCount() - 1;
                }
                contentPager.setCurrentItem(index);
                setIndexSelected(index);
                break;
        }
    }

    private void initIndexLayout(int pageNum) {
        for(int i = 0; i < pageNum; i ++) {
            TextView indexView = new TextView(this);
            indexView.setText(String.valueOf(i + 1));
            indexView.setTextSize(11);
            indexView.setTextColor(getResources().getColor(R.color.text_dark_gray));
            indexView.setPadding(Utils.dip2px(this, 10), 0, Utils.dip2px(this, 10), 0);
            indexLayout.addView(indexView);
            indexView.setTag(i);
            indexView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (int)v.getTag();
                    setIndexSelected(index);
                    contentPager.setCurrentItem(index);
                }
            });
        }
    }

    private void setIndexSelected(int index) {
        if(index < indexLayout.getChildCount()) {
            for(int i = 0; i < indexLayout.getChildCount(); i ++) {
                TextView childView = (TextView)indexLayout.getChildAt(i);
                childView.setTextColor(getResources().getColor(R.color.text_dark_gray));
            }
            TextView childView = (TextView)indexLayout.getChildAt(index);
            childView.setTextColor(getResources().getColor(R.color.text_green));
//            childView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
//            childView.getPaint().setAntiAlias(true);
        }
    }

    public void getMeetingDays(final OnMeetingDayLoadListener listener) {
        currentPage = contentPager.getCurrentItem() + 1;
        HttpManager.getMeetingDayList(new AcademyHandler(this) {
            @Override
            protected void handleSuccessMessage(Object object) {
                onDismissLoadingDialog();
                HandleInfo handleInfo = (HandleInfo) object;
                MeetingDayData mData = YJson.getObj(handleInfo.getData(), MeetingDayData.class);
                if (currentPage == 1) {
                    mAdapter = new MeetingFragmentAdapter(
                            getSupportFragmentManager(),
                            mData);
                    contentPager.setAdapter(mAdapter);
                    initIndexLayout(Integer.parseInt(mData.getTotalPage()));
                    setIndexSelected(0);
                } else if (listener != null) {
                    listener.onMeetingDayLoaded(mData);
                }
            }

            @Override
            protected void handleError(int errorCode, String errorMsg) {
                onDismissLoadingDialog();
            }
        }, currentPage, Consts.DEFAULT_PAGE_SIZE);
    }

    public interface OnMeetingDayLoadListener {
        void onMeetingDayLoaded(MeetingDayData meetingDayData);
    }
}
