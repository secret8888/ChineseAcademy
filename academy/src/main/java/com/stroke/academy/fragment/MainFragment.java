package com.stroke.academy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.fragment.base.BaseFragment;
import com.stroke.academy.view.MainNewsView;

public class MainFragment extends BaseFragment implements OnClickListener,
        ViewPager.OnPageChangeListener {

    @ViewId(R.id.im_setting)
    private ImageView settingView;

    @ViewId(R.id.tv_title)
    private TextView titleView;

    @ViewId(R.id.pager_banner)
    private ViewPager bannerPager;

    @ViewId(R.id.lv_news_1)
    private MainNewsView newsOneView;

    @ViewId(R.id.lv_news_2)
    private MainNewsView newsTwoView;

    @ViewId(R.id.lv_news_3)
    private MainNewsView newsThreeView;

    @ViewId(R.id.lv_meeting_video)
    private LinearLayout meetingVideoLayout;

    @ViewId(R.id.lv_article_browse)
    private LinearLayout articleBrowseLayout;

    @ViewId(R.id.lv_mobile_check)
    private LinearLayout mobileCheckLayout;

    @ViewId(R.id.lv_network_metting)
    private LinearLayout networkMettingLayout;

    @ViewId(R.id.lv_base_train)
    private LinearLayout baseTrainLayout;

    @ViewId(R.id.lv_international_online)
    private LinearLayout internationalOnlineLayout;

    /** banner **/
    private String[] mainTitleArray;

    private String[] mainUrlArray;

    /** news **/
    private String[] mainNewsTitleArray;

    private String[] mainNewsContentArray;

    private String[] mainNewsUrlArray;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        //banner
        mainTitleArray = getResources().getStringArray(R.array.main_title_array);
        mainUrlArray = getResources().getStringArray(R.array.main_url_array);
        //news
        mainNewsTitleArray = getResources().getStringArray(R.array.main_news_title_array);
        mainNewsContentArray = getResources().getStringArray(R.array.main_news_content_array);
        mainNewsUrlArray = getResources().getStringArray(R.array.main_news_url_array);
        titleView.setText(mainTitleArray[0]);
        bannerPager.setOffscreenPageLimit(5);
        bannerPager.setAdapter(new BannerPagerAdapter());
        newsOneView.setData(R.drawable.ic_news_1, mainNewsTitleArray[0], mainNewsContentArray[0], mainNewsUrlArray[0]);
        newsTwoView.setData(R.drawable.ic_news_2, mainNewsTitleArray[1], mainNewsContentArray[1], mainNewsUrlArray[1]);
        newsThreeView.setData(R.drawable.ic_news_3, mainNewsTitleArray[2], mainNewsContentArray[2], mainNewsUrlArray[2]);
    }

    @Override
    protected void setListeners() {
        settingView.setOnClickListener(this);
        bannerPager.setOnPageChangeListener(this);
        meetingVideoLayout.setOnClickListener(this);
        articleBrowseLayout.setOnClickListener(this);
        mobileCheckLayout.setOnClickListener(this);
        networkMettingLayout.setOnClickListener(this);
        baseTrainLayout.setOnClickListener(this);
        internationalOnlineLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_setting:
                IntentManager.startSettingActivity(getActivity(), 0);
                break;
            case R.id.lv_meeting_video:
                IntentManager.startMeetingDayActivity(getActivity());
                break;
            case R.id.lv_article_browse:
                IntentManager.startArticleListActivity(getActivity());
                break;
            case R.id.lv_mobile_check:
                IntentManager.startWaitingActivity(getActivity(), getString(R.string.mobile_check));
                break;
            case R.id.lv_network_metting:
                IntentManager.startWaitingActivity(getActivity(), getString(R.string.network_meeting));
                break;
            case R.id.lv_base_train:
                IntentManager.startWaitingActivity(getActivity(), getString(R.string.base_train));
                break;
            case R.id.lv_international_online:
                IntentManager.startWaitingActivity(getActivity(), getString(R.string.international_online));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    @Override
    public void onPageSelected(int i) {
        titleView.setText(mainTitleArray[i]);
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(bannerPager.getChildAt(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View bannerView = bannerPager.getChildAt(position);
            bannerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentManager.startWebviewActivity(getActivity(), mainTitleArray[position], mainUrlArray[position]);
                }
            });
            return bannerView;
        }
    }
}
