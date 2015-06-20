package com.stroke.academy.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.PreferenceConsts;
import com.stroke.academy.common.http.AcademyHandler;
import com.stroke.academy.common.http.HttpManager;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.common.util.PreferenceUtils;
import com.stroke.academy.common.util.Toaster;
import com.stroke.academy.common.util.Utils;
import com.stroke.academy.model.HandleInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity {

	@ViewId(R.id.edit_member)
	private EditText memberEdit;

	@ViewId(R.id.edit_psd)
	private EditText psdEdit;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_login;
	}

	@Override
	protected void readIntent() {

	}

	@Override
	protected void initControls(Bundle savedInstanceState) {

	}

	@Override
	protected void setListeners() {

	}

	public void onLoginClick(View view) {
		if (Utils.checkMetworkConnected(this)) {
			String memberId = memberEdit.getText().toString();
			String psd = psdEdit.getText().toString();

			if (TextUtils.isEmpty(memberId)) {
				Toaster.show(this, R.string.member_not_null);
				return;
			}

			if (TextUtils.isEmpty(psd)) {
				Toaster.show(this, R.string.psd_not_null);
				return;
			}

			if(!isMobile(psd)) {
				Toaster.show(this, R.string.psd_not_phone);
				return;
			}
			onShowLoadingDialog();
			HttpManager.login(new AcademyHandler(this) {

				@Override
				protected void handleSuccessMessage(Object object) {
					handleLoginResult((HandleInfo) object);
				}

				@Override
				protected void handleError(int errorCode, String errorMsg) {
					onDismissLoadingDialog();
				}
			}, memberId, psd);
		}
	}

	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	private void handleLoginResult(HandleInfo handleInfo) {
		onDismissLoadingDialog();
		PreferenceUtils.putString(PreferenceConsts.KEY_LOGIN_INFO, handleInfo.getData());
		IntentManager.startMainActivity(this);
		finish();
	}
}
