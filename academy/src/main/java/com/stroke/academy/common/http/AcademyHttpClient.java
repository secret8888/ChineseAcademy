package com.stroke.academy.common.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.stroke.academy.common.constant.Consts;
import com.stroke.academy.common.constant.HttpConsts;
import com.stroke.academy.common.util.AES256;
import com.stroke.academy.common.util.Logcat;
import com.stroke.academy.model.HandleInfo;

import org.json.JSONObject;


public class AcademyHttpClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    private final static String TAG = AcademyHttpClient.class.getSimpleName();

    /**
     * http post request
     * 
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(String url, RequestParams params,
            AsyncHttpResponseHandler responseHandler) {
        setHttpClientParams();
        if (params != null) {
            Logcat.d(TAG, "get URL: " + url + ", params: " + params.toString());
        }
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * http post request
     * 
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void post(String url, RequestParams params,
            AsyncHttpResponseHandler responseHandler) {
        setHttpClientParams();
        if (params != null) {
            Logcat.d(TAG, "Post URL: " + getAbsoluteUrl(url) + ", params: " + params.toString());
        }
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Logcat.d(TAG, HttpConsts.BASE_URL + relativeUrl);
        return HttpConsts.BASE_URL + relativeUrl;
    }

    /**
     * set httpclient params
     */
    private static void setHttpClientParams() {
        client.setTimeout(Consts.TIMEOUT);
        // client.setUserAgent(Consts.USERAGANT);
        // Logcat.d(TAG, "useragent: " + Consts.USERAGANT);
    }

    /**
     * 获取服务器返回的信息
     * 
     * @param result
     * @return
     */
    public static synchronized HandleInfo getHttpMessage(String result) {
        HandleInfo msg = new HandleInfo();
        JSONObject jObject;
        try {
            jObject = new JSONObject(result);
            if (jObject.has("json")) {
                JSONObject valueObject = new JSONObject(AES256.decrypt(jObject.getString("json")));
                Logcat.d("TAG", "value : " + valueObject.toString());
                msg.setRetCode(valueObject.optString("retCode"));
                msg.setRetDesc(valueObject.optString("retDesc"));
                msg.setData(valueObject.optString("Data"));
            }
        } catch (Exception e) {
            Logcat.e(TAG, "AcademyHttpClient getHttpMessage exception",
                    e);
        }
        return msg;
    }

}
