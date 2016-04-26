package com.nupt.dzs.wordsreader.http.request;

/**
 * Created by Administrator on 2016/4/26.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public interface IRequest<RESPONSE> {
    String baseUrl = "https://api.shanbay.com";
    void onResponse(RESPONSE response);
    void onFailure(String msg);
}
