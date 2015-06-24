package com.stroke.academy.common.http;

import com.loopj.android.http.RequestParams;

public class HttpManager {

	private final static String TAG = HttpManager.class.getSimpleName();

//	private static void asyncGet(AcademyHandler handler, final String url, JSONObject valueObject) {
//		Logcat.d("TAG", "params : " + valueObject.toString());
//		RequestParams requestParams = new RequestParams();
//		requestParams.put("jsonData", AES256.encrypt(valueObject.toString()));
//		AcademyHttpClient.get(url,
//				requestParams, new AcademyHttpResponseHandler(handler));
//	}

	/**
	 * 登录接口
	 * @param handler
	 * @param userName
	 * @param password
	 */
	public static void login(final AcademyHandler handler, String userName,
			String password) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("username", userName);
		requestParams.put("password", password);
		AcademyHttpClient.get("/ChinaStroke/user/Login?",
				requestParams, new AcademyHttpResponseHandler(handler));
	}

	/**
	 * get meeting day list
	 *
	 * @param handler
	 * @param currentPage
	 */
	public static void getMeetingDayList(final AcademyHandler handler, int currentPage, int pageSize) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("currentPage", String.valueOf(currentPage));
		requestParams.put("pageSize", String.valueOf(pageSize));
		AcademyHttpClient.get("/ChinaStroke/video/meetingDay?",
				requestParams, new AcademyHttpResponseHandler(handler));
	}

	public static void getMeetingList(final AcademyHandler handler, String meetingStr, int currentPage, int pageSize) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("meetingStr", meetingStr);
		requestParams.put("currentPage", String.valueOf(currentPage));
		requestParams.put("pageSize", String.valueOf(pageSize));
		AcademyHttpClient.get("/ChinaStroke/video/meeting?",
				requestParams, new AcademyHttpResponseHandler(handler));
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
		RequestParams requestParams = new RequestParams();
		requestParams.put("meetingId", meetingId);
		requestParams.put("dayId", dayId);
		requestParams.put("currentPage", String.valueOf(currentPage));
		requestParams.put("pageSize", String.valueOf(pageSize));
		AcademyHttpClient.get("/ChinaStroke/video/recordVideos?",
				requestParams, new AcademyHttpResponseHandler(handler));
	}

	/**
	 * get article list
	 * @param handler
	 * @param currentPage
	 * @param pageSize
	 */
	public static void getArticleList(final AcademyHandler handler, int currentPage, int pageSize) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("currentPage", String.valueOf(currentPage));
		requestParams.put("pageSize", String.valueOf(pageSize));
		AcademyHttpClient.get("/ChinaStroke/article/articleList?",
				requestParams, new AcademyHttpResponseHandler(handler));
	}

	/**
	 * get article info
	 *
	 * @param handler
	 * @param id
	 */
	public static void getArticleInfo(final AcademyHandler handler, String id) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("id", id);
		AcademyHttpClient.get("/ChinaStroke/article/articleInfo?",
				requestParams, new AcademyHttpResponseHandler(handler));
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
