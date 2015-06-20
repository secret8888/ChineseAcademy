package com.stroke.academy.model;

/**
 * @author yuym
 */
public class HandleInfo {

    // 0 正常
    public static final String OK = "200";

    /**
     * 状态编码 除了code=200以外，其他均为业务或系统状态吗
     */
    private String retCode;

    /**
     * 服务器返回的业务数据
     */
    private String data;

    /**
     * 错误信息
     */
    private String retDesc;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRetDesc() {
        return retDesc;
    }

    public void setRetDesc(String retDesc) {
        this.retDesc = retDesc;
    }
}
