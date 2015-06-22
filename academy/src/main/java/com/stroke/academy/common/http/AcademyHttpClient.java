package com.stroke.academy.common.http;

import android.text.TextUtils;

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
        try {
            JSONObject valueObject = new JSONObject(result);
            Logcat.d("TAG", "value : " + valueObject.toString());
            String data = valueObject.optString("data");
            String retCode = valueObject.optString("retCode");
            msg.setRetCode(retCode);
            msg.setData(data);
            if(!TextUtils.isEmpty(data) && Integer.parseInt(retCode) != AcademyHandler.RESULT_CODE_SUCCESS) {
                JSONObject dataObject = new JSONObject(data);
                msg.setRetDesc(dataObject.optString("errMsg"));
            }
        } catch (Exception e) {
            Logcat.e(TAG, "AcademyHttpClient getHttpMessage exception",
                    e);
        }
        return msg;
    }

}
