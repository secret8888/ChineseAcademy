package com.stroke.academy.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.Consts;
import com.stroke.academy.common.constant.IntentConsts;
import com.stroke.academy.common.constant.PreferenceConsts;
import com.stroke.academy.common.http.AcademyHandler;
import com.stroke.academy.common.http.HttpManager;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.common.util.PreferenceUtils;
import com.stroke.academy.common.util.Toaster;
import com.stroke.academy.common.util.Utils;
import com.stroke.academy.model.HandleInfo;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

	@ViewId(R.id.tv_title)
	private TextView titleView;

	@ViewId(R.id.tv_back)
	private TextView backView;

	private int settingTag = -1;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_setting;
	}

	@Override
	protected void readIntent() {

	}

	@Override
	protected void initControls(Bundle savedInstanceState) {
		titleView.setText(R.string.setting);
	}

	@Override
	protected void setListeners() {
		backView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_back:
				onBackPressed();
				break;
			case R.id.tv_detail:
				break;
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra(IntentConsts.SETTING_TAG_KEY, settingTag);
		setResult(RESULT_OK, intent);
		super.onBackPressed();
	}

	public void onHelpClick(View view) {
		IntentManager.startHelpActivity(this);
	}

	public void onSwitchAccountClick(View view) {
		PreferenceUtils.clear(PreferenceConsts.KEY_LOGIN_INFO);
		settingTag = Consts.SETTING_SWITCH_ACCOUNT_TAG;
		onBackPressed();
	}

	public void onLogoutClick(View view) {
		PreferenceUtils.clear(PreferenceConsts.KEY_LOGIN_INFO);
		settingTag = Consts.SETTING_LOGOUT_TAG;
		onBackPressed();
	}
}
