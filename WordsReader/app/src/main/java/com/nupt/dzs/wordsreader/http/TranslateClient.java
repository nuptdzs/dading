package com.nupt.dzs.wordsreader.http;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dading on 2016/4/26.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public interface TranslateClient {

    /**
     * q 	TEXT 	Y 	请求翻译query 	UTF-8编码
     from 	TEXT 	Y 	翻译源语言 	语言列表(可设置为auto)
     to 	TEXT 	Y 	译文语言 	语言列表(不可设置为auto)
     appid 	INT 	Y 	APP ID 	可在管理控制台查看
     salt 	INT 	Y 	随机数
     sign 	TEXT 	Y 	签名 	appid+q+salt+密钥 的MD5值
     * @return
     */
    @GET("/api/trans/vip/translate")
    Observable transLateWord(@Query("q") String q,
                             @Query("from") String from,
                             @Query("to") String to,
                             @Query("appid") int appid,
                             @Query("salt") int salt,
                             @Query("sign") int sign);
}
