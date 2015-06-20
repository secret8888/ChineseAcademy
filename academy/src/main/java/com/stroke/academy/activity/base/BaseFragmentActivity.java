package com.stroke.academy.activity.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.stroke.academy.R;
import com.stroke.academy.annotation.Injector;
import com.stroke.academy.common.util.ViewUtils;
import com.stroke.academy.listener.OnLoadingViewListener;

public abstract class BaseFragmentActivity extends FragmentActivity implements OnLoadingViewListener {

    /* 统计用户浏览该activity的时间 */
    private static boolean isViewTimeOn = false;

    /* 用户浏览该activity的开始时间 */
    private long startTime = 0;

    /* 加载等待dialog*/
    private Dialog loadingDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        if (layoutId != -1) {
            setContentView(layoutId);
        }
        Injector.inject(this, this);
//        DATracker.enableTracker(this, Consts.HZ_ANALYZER_KEY, BuildConfig.VERSION_NAME, "index");
        readIntent();
        initControls(savedInstanceState);
        setListeners();

    }

    @Override
    protected void onResume() {
        if (isViewTimeOn) {
            startTime = System.currentTimeMillis(); // 获取开始时间
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (isViewTimeOn) {
            uploadViewTime();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        onDismissLoadingDialog();
        super.onDestroy();
    }

    @Override
    public void onShowLoadingDialog() {
        loadingDialog = ViewUtils.createLoadingDialog(this, getString(R.string.wating_hint));
        loadingDialog.show();
    }

    @Override
    public void onShowLoadingDialog(String loadingText) {
        loadingDialog = ViewUtils.createLoadingDialog(this, loadingText);
        loadingDialog.show();
    }

    @Override
    public void onDismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    private void uploadViewTime() {
        long endTime = System.currentTimeMillis(); // 获取结束时间
        long interval = endTime - startTime;
        if (interval > 0) {
            //上传浏览时间
        }
    }

    /**
     * get layout id for fragment
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * read intent from last object
     *
     * @return
     */
    protected abstract void readIntent();

    /**
     * init base values
     *
     * @return
     */
    protected abstract void initControls(Bundle savedInstanceState);

    /**
     * set listener for fragment views
     */
    protected abstract void setListeners();
}
