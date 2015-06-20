package com.stroke.academy.common.http;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.stroke.academy.R;
import com.stroke.academy.common.util.Toaster;

public abstract class AcademyHandler extends Handler {

	public static final int NETWORK_TIMEOUT = 10000;// result is null

	public static final int RESULT_CODE_ERROR = -1;// result-status error

	public static final int RESULT_CODE_SUCCESS = 200;// result-status error

	public static final int RESULT_CODE_FAIL = 400;// result-status error

	private WeakReference<Context> mContext = null;

	public AcademyHandler(Context context) {
		mContext = new WeakReference<Context>(context);
	}

	@Override
	public void handleMessage(Message msg) {
		Context context = null;
		if (mContext != null) {
			context = mContext.get();
		}

		switch (msg.what) {
		case NETWORK_TIMEOUT:
			Toaster.show(context, R.string.network_connect_timeout);
			handleError(NETWORK_TIMEOUT, null);
			break;
		case RESULT_CODE_ERROR:
			handleError(RESULT_CODE_ERROR, null);
			break;
		case RESULT_CODE_FAIL:
			String errorMsg = msg.getData().getString("message");
			if (!TextUtils.isEmpty(errorMsg)) {
				Toaster.showMsg(context, errorMsg);
			}
			handleError(RESULT_CODE_FAIL, errorMsg);
			break;
		default:
			handleSuccessMessage(msg.obj);
		}

	}

	protected abstract void handleSuccessMessage(Object object);

	protected abstract void handleError(int errorCode, String errorMsg);
}
