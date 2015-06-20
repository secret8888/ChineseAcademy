/**
 * Copyright 2013 Ubox Inc. Filename : StationAsyncHttpResponseHandler.java
 * Author : Jiang Bian Creation time : 2013-7-10 Description :
 */
package com.stroke.academy.common.http;

import android.os.Message;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.stroke.academy.common.util.Logcat;
import com.stroke.academy.common.util.Utils;
import com.stroke.academy.model.HandleInfo;

import org.apache.http.Header;

/**
 * 
 * @author emilyu
 *
 */
public class AcademyHttpResponseHandler extends AsyncHttpResponseHandler {

    private AcademyHandler handler = null;
    
    public AcademyHttpResponseHandler(AcademyHandler handler){
        this.handler = handler;
    }
    
    protected boolean isMessageAvailabe(HandleInfo handleInfo){
        if (Utils.isMessageAvaliabe(handleInfo, handler)) {
            return true;
        }
        return false;
    }
    
    protected void sendSuccessMessage(int what, Object object){
        Message message = new Message();
        message.what = what;
        message.obj = object;
        handler.sendMessage(message);
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        String response = new String(bytes);
        Logcat.d("TAG", "success result : " + response);
        HandleInfo handleInfo = AcademyHttpClient
                .getHttpMessage(response);
        if (isMessageAvailabe(handleInfo)) {
            sendSuccessMessage(AcademyHandler.RESULT_CODE_SUCCESS,
                    handleInfo);
        }
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        Logcat.d("TAG", "fail result : " + new String(bytes));
        handler.sendEmptyMessage(AcademyHandler.RESULT_CODE_ERROR);
    }
}
