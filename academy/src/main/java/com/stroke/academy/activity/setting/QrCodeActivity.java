package com.stroke.academy.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.annotation.ViewId;

public class QrCodeActivity extends BaseActivity implements View.OnClickListener{

	@ViewId(R.id.tv_title)
	private TextView titleView;

	@ViewId(R.id.tv_back)
	private TextView backView;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_qrcode;
	}

	@Override
	protected void readIntent() {

	}

	@Override
	protected void initControls(Bundle savedInstanceState) {
		titleView.setText(R.string.qrcode);
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
}
