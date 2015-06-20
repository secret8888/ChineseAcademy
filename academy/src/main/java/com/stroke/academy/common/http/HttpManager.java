package com.stroke.academy.common.http;

import com.loopj.android.http.RequestParams;
import com.stroke.academy.common.util.AES256;
import com.stroke.academy.common.util.Logcat;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpManager {

	private final static String TAG = HttpManager.class.getSimpleName();

	private static void asyncGet(AcademyHandler handler, final String url, JSONObject valueObject) {
		Logcat.d("TAG", "params : " + valueObject.toString());
		RequestParams requestParams = new RequestParams();
		requestParams.put("jsonData", AES256.encrypt(valueObject.toString()));
		AcademyHttpClient.get(url,
				requestParams, new AcademyHttpResponseHandler(handler));
	}

	/**
	 * 登录接口
	 * @param handler
	 * @param userName
	 * @param password
	 */
	public static void login(final AcademyHandler handler, String userName,
			String password) {
		try {
			JSONObject valueObject = new JSONObject();
			valueObject.put("username", userName);
			valueObject.put("password", password);
			asyncGet(handler, "/ChinaStroke/user/login?", valueObject);
		} catch (JSONException e) {
			handler.handleError(AcademyHandler.RESULT_CODE_ERROR, null);
		}
	}

	/**
	 * get meeting day list
	 *
	 * @param handler
	 * @param currentPage
	 */
	public static void getMeetingDayList(final AcademyHandler handler, int currentPage, int pageSize) {
		try {
			JSONObject valueObject = new JSONObject();
			valueObject.put("currentPage", String.valueOf(currentPage));
			valueObject.put("pageSize", String.valueOf(pageSize));
			asyncGet(handler, "/ChinaStroke/video/meetingDay?", valueObject);
		} catch (JSONException e) {
			handler.handleError(AcademyHandler.RESULT_CODE_ERROR, null);
		}
	}

	public static void getMeetingList(final AcademyHandler handler, String meetingStr, int currentPage, int pageSize) {
		try {
			JSONObject valueObject = new JSONObject();
			valueObject.put("meetingStr", meetingStr);
			valueObject.put("currentPage", String.valueOf(currentPage));
			valueObject.put("pageSize", String.valueOf(pageSize));
			asyncGet(handler, "/ChinaStroke/video/meeting?", valueObject);
		} catch (JSONException e) {
			handler.handleError(AcademyHandler.RESULT_CODE_ERROR, null);
		}
	}

	/**
	 * get video list
	 * @param handler
	 * @param meetingId
	 * @param dayId
	 * @param currentPage
	 * @param pageSize
	 */
	public static void getVideoList(final AcademyHandler handler, String meetingId, String dayId, int currentPage, int pageSize) {
		try {
			JSONObject valueObject = new JSONObject();
			valueObject.put("meetingId", meetingId);
			valueObject.put("dayId", dayId);
			valueObject.put("currentPage", String.valueOf(currentPage));
			valueObject.put("pageSize", String.valueOf(pageSize));
			asyncGet(handler, "/ChinaStroke/video/recordVideos?", valueObject);
		} catch (JSONException e) {
			handler.handleError(AcademyHandler.RESULT_CODE_ERROR, null);
		}
	}

	/**
	 * get article list
	 * @param handler
	 * @param currentPage
	 * @param pageSize
	 */
	public static void getArticleList(final AcademyHandler handler, int currentPage, int pageSize) {
		try {
			JSONObject valueObject = new JSONObject();
			valueObject.put("currentPage", String.valueOf(currentPage));
			valueObject.put("pageSize", String.valueOf(pageSize));
			asyncGet(handler, "/ChinaStroke/article/articleList?", valueObject);
		} catch (JSONException e) {
			handler.handleError(AcademyHandler.RESULT_CODE_ERROR, null);
		}
	}

	/**
	 * get article info
	 *
	 * @param handler
	 * @param id
	 */
	public static void getArticleInfo(final AcademyHandler handler, String id) {
		try {
			JSONObject valueObject = new JSONObject();
			valueObject.put("id", id);
			asyncGet(handler, "/ChinaStroke/article/articleInfo?", valueObject);
		} catch (JSONException e) {
			handler.handleError(AcademyHandler.RESULT_CODE_ERROR, null);
		}
	}

//	/**
//	 * 退出登录
//	 *
//	 * @param handler
//	 * @param uid
//	 */
//	public static void logout(final AcademyHandler handler, String uid) {
//		RequestParams requestParams = new RequestParams();
//		requestParams.put("uid", uid);
//		AcademyHttpClient.get("/Clientuser/loginOut/?" + getRequestParams(),
//				requestParams, new AcademyHttpResponseHandler(handler));
//	}
//
//	/**
//	 * 检查更新
//	 *
//	 * @param handler
//	 * @param versionName
//	 */
//	public static void checkUpdate(final AcademyHandler handler,
//			String versionName) {
//		RequestParams requestParams = new RequestParams();
//		requestParams.put("versonCode", versionName);
//		requestParams.put("type", "android");
//		AcademyHttpClient.get("/Clientuser/ckvsn/?" + getRequestParams(),
//				requestParams, new AcademyHttpResponseHandler(handler));
//	}
}
