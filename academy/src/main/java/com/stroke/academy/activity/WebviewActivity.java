package com.stroke.academy.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.IntentConsts;

public class WebviewActivity extends BaseActivity implements View.OnClickListener{

	@ViewId(R.id.tv_title)
	private TextView titleView;

	@ViewId(R.id.tv_back)
	private TextView backView;

	@ViewId(R.id.webview)
	private WebView webView;

	private String title;

	private String url;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_webview;
	}

	@Override
	protected void readIntent() {
		title = getIntent().getStringExtra(IntentConsts.TITLE_KEY);
		url = getIntent().getStringExtra(IntentConsts.URL_KEY);
	}

	@Override
	protected void initControls(Bundle savedInstanceState) {
		titleView.setText(title);
		webView.loadUrl(url);
		setDefultWebSettings(webView.getSettings());
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				//返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				onShowLoadingDialog();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				onDismissLoadingDialog();
			}
		});
	}

	@Override
	protected void setListeners() {
		backView.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		if(webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}

	public static void setDefultWebSettings(WebSettings settings) {
		settings.setBuiltInZoomControls(false);
		settings.setSaveFormData(false);
		settings.setSavePassword(false);
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(false);

		// 设置强制加载进来的页面自适应屏幕大小
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_back:
				finish();
				break;
			case R.id.tv_detail:
				break;
		}
	}
}
