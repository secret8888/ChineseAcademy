package com.stroke.academy.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.stroke.academy.R;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.PreferenceConsts;
import com.stroke.academy.common.util.PreferenceUtils;
import com.stroke.academy.fragment.base.BaseFragment;
import com.stroke.academy.model.UserInfo;
import com.youdao.yjson.YJson;

import org.w3c.dom.Text;

public class MineFragment extends BaseFragment implements OnClickListener {

	@ViewId(R.id.im_avatar)
	private ImageView avatarView;

	@ViewId(R.id.tv_name)
	private TextView nameView;

	@ViewId(R.id.tv_member_id)
	private TextView memberIdView;

	@ViewId(R.id.tv_hospital)
	private TextView hospitalView;

	@ViewId(R.id.tv_job)
	private TextView jobView;

	private DisplayImageOptions options;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_mine;
	}

	@Override
	protected void readIntent() {
	}

	@Override
	protected void initControls(Bundle savedInstanceState) {
		String loginInfo = PreferenceUtils.getString(PreferenceConsts.KEY_LOGIN_INFO);
		UserInfo userInfo = YJson.getObj(loginInfo, UserInfo.class);
		nameView.setText(userInfo.getName());
		memberIdView.setText(userInfo.getIdentity());
		hospitalView.setText(userInfo.getHospital());
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory(true)
				.displayer(new RoundedBitmapDisplayer(30))
				.cacheOnDisk(true).build();

		ImageLoader.getInstance().displayImage(userInfo.getImgurl(), avatarView, options);
	}

	@Override
	protected void setListeners() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}
}
