package com.stroke.academy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.activity.base.BaseFragmentActivity;
import com.stroke.academy.common.constant.Consts;
import com.stroke.academy.common.constant.IntentConsts;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.common.util.Toaster;
import com.stroke.academy.fragment.MainFragment;
import com.stroke.academy.fragment.MessageFragment;
import com.stroke.academy.fragment.MineFragment;

public class MainActivity extends BaseFragmentActivity {

    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    private RadioGroup mTabRg;

    private final Class<?>[] fragments = {MainFragment.class, MessageFragment.class, MineFragment.class};

    // exit time
    private long mExitTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void setListeners() {

    }

    private void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragments[i], null);
        }

        mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
        mTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_main:
                        mTabHost.setCurrentTab(0);
                        break;
                    case R.id.tab_message:
                        mTabHost.setCurrentTab(1);
                        break;
                    case R.id.tab_mine:
                        mTabHost.setCurrentTab(2);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return exitApplication();
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == Activity.RESULT_OK) {
            int settingTag = data.getIntExtra(IntentConsts.SETTING_TAG_KEY, -1);
            if(settingTag == Consts.SETTING_SWITCH_ACCOUNT_TAG) {
                IntentManager.startLoginActivity(this);
                finish();
            } else if (settingTag == Consts.SETTING_LOGOUT_TAG) {
                finish();
            }
        }
    }

    /**
     * 判断两次返回时间间隔,小于两秒则退出程序
     */
    private boolean exitApplication() {
        if (System.currentTimeMillis() - mExitTime > Consts.INTERVAL) {
            Toaster.showMsg(this, getResources().getString(R.string.exit_tip));
            mExitTime = System.currentTimeMillis();
            return true;
        } else {
            finish();
            return false;
        }
    }
}
