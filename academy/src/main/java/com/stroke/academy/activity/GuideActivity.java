package com.stroke.academy.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.PreferenceConsts;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.common.util.PreferenceUtils;


public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @ViewId(R.id.pager_guide)
    private ViewPager viewPager = null;

    private int scrollState;

    private boolean isStartActivity = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        String loginInfo = PreferenceUtils.getString(PreferenceConsts.KEY_LOGIN_INFO);
        if(!TextUtils.isEmpty(loginInfo)) {
            IntentManager.startMainActivity(this);
            finish();
        } else {
            boolean isFirstLogin = PreferenceUtils.getBoolean(PreferenceConsts.KEY_FIRST_LOGIN, true);
            if(!isFirstLogin) {
                IntentManager.startLoginActivity(this);
                finish();
            }
        }

        viewPager.setOffscreenPageLimit(viewPager.getChildCount());
        viewPager.setAdapter(new GuidePagerAdapter());
    }

    @Override
    protected void setListeners() {
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (!isStartActivity && scrollState == 1 && arg0 == 3) {
            isStartActivity = true;
            IntentManager.startMainActivity(this);
            finish();
        }
    }

    @Override
    public void onPageSelected(int arg0) {
    }

    public void onRejectClick(View view) {
        onBackPressed();
    }

    public void onAcceptClick(View view) {
        PreferenceUtils.putBoolean(PreferenceConsts.KEY_FIRST_LOGIN, false);
        IntentManager.startLoginActivity(this);
        finish();
    }

    class GuidePagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(View collection, int position) {
            return viewPager.getChildAt(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public int getCount() {
            return viewPager.getChildCount();
        }
    }

}
