package com.stroke.academy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.IntentConsts;

public class WaitingActivity extends BaseActivity implements View.OnClickListener{

	@ViewId(R.id.tv_title)
	private TextView titleView;

	@ViewId(R.id.tv_back)
	private TextView backView;

	private String title;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_waiting;
	}

	@Override
	protected void readIntent() {
		title = getIntent().getStringExtra(IntentConsts.TITLE_KEY);
	}

	@Override
	protected void initControls(Bundle savedInstanceState) {
		titleView.setText(title);
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
}
