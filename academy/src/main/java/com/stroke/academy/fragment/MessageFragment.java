package com.stroke.academy.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import com.stroke.academy.R;
import com.stroke.academy.adapter.ArticleListAdapter;
import com.stroke.academy.adapter.MessageAdapter;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.fragment.base.BaseFragment;
import com.stroke.academy.model.MessageItem;
import com.stroke.academy.view.refresh.RefreshListView;

import java.util.ArrayList;

public class MessageFragment extends BaseFragment implements OnClickListener {

	@ViewId(R.id.list_content)
	private RefreshListView contentView;

	private MessageAdapter mAdapter;

	private ArrayList<MessageItem> items;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_message;
	}

	@Override
	protected void readIntent() {

	}

	@Override
	protected void initControls(Bundle savedInstanceState) {
		contentView.setPullLoadEnable(false);
		contentView.setPullRefreshEnable(false);
		items = new ArrayList<>();
		items.add(new MessageItem("系统消息", "\"中国卒中\"APP试用通知！！！",
				"“中国卒中”APP 将从 2015.06.26 日起为广大用户提供试用版本,试用期限暂定3个月至 2015.10.01。后续进展敬请关注..."));
		items.add(new MessageItem("系统消息", "\"中国卒中\"APP升级计划！！！",
				"中国卒中”APP 计划从试用期开始(2015.06.26)对APP进行阶段性升级,每次升级将会提供更多功能模块," +
						"升级期间的原有下载方式可正常试用,并将提供应用市场下载功能。"));
		mAdapter = new MessageAdapter(getActivity(), items);
		contentView.setAdapter(mAdapter);
	}

	@Override
	protected void setListeners() {
		contentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				IntentManager.startMessageDetailActivity(getActivity(), items.get(position-1));
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}
}
