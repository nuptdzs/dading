package com.nupt.dzs.wordsreader.http.response;

/**
 * Created by Administrator on 2016/4/26.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public class Response<T> {
    private int status_code;
    private String msg;
    private T data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
