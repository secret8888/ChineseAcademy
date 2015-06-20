package com.stroke.academy.activity.message;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.IntentConsts;
import com.stroke.academy.model.MessageItem;

public class MessageDetailActivity extends BaseActivity implements View.OnClickListener{

	@ViewId(R.id.tv_title)
	private TextView titleView;

	@ViewId(R.id.tv_back)
	private TextView backView;

	@ViewId(R.id.tv_message_title)
	private TextView messageTitleView;

	@ViewId(R.id.tv_content)
	private TextView contentView;

	private MessageItem item;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_message_detail;
	}

	@Override
	protected void readIntent() {
		item = (MessageItem)getIntent().getSerializableExtra(IntentConsts.MESSAGE_ITEM_KEY);
	}

	@Override
	protected void initControls(Bundle savedInstanceState) {
		titleView.setText(R.string.message_detail);
		messageTitleView.setText(item.getTitle());
		contentView.setText("    " + item.getContent());
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
