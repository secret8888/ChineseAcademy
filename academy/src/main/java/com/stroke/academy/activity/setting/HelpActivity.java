package com.stroke.academy.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.Consts;
import com.stroke.academy.common.constant.IntentConsts;
import com.stroke.academy.common.constant.PreferenceConsts;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.common.util.PreferenceUtils;

public class HelpActivity extends BaseActivity implements View.OnClickListener{

	@ViewId(R.id.tv_title)
	private TextView titleView;

	@ViewId(R.id.tv_back)
	private TextView backView;

	private int settingTag = -1;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_help;
	}

	@Override
	protected void readIntent() {

	}

	@Override
	protected void initControls(Bundle savedInstanceState) {
		titleView.setText(R.string.help);
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
		}
	}

	public void onIntroClick(View view) {
		IntentManager.startWebviewActivity(this, getString(R.string.software_intro), "http://122.114.52.243:9900/help01.htm");
	}

	public void onUseHelpClick(View view) {
		IntentManager.startWebviewActivity(this, getString(R.string.software_intro), "http://122.114.52.243:9900/help02.htm");
	}

	public void onQrCodeClick(View view) {
		IntentManager.startQrCodeActivity(this);
	}
}
