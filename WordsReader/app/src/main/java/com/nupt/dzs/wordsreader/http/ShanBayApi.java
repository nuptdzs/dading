package com.nupt.dzs.wordsreader.http;

import com.nupt.dzs.wordsreader.http.response.Response;
import com.nupt.dzs.wordsreader.http.response.TokenResponse;
import com.nupt.dzs.wordsreader.http.response.WordResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/26.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public interface ShanBayApi {
    /**
     * {client_id} 必须,创建应用时分配的App key

     {response_type} 必须，必须为code或者token其中一个

     {state} 可选，如果传递在服务器会把相同的state值传回给客户端，用于保存状态
     * @return
    @GET("/oauth2/authorize/")
    Observable<> authorize();

    code =ZCmCRIIRLuRsbGTMp3PDkYByzF3r6W;*/

    /**
     * client_id         必须,创建应用时分配的App key
     client_secret     必须,创建应用时分配的App secret
     grant_type        必须，值为authorization_code
     code              必须，上一步获取的CODE
     redirect_uri      必须,需要和创建应用时填写的回调地址相同
     * @return
     */
    @FormUrlEncoded
    @POST("/oauth2/token/")
    Observable<TokenResponse> getToken(@Field("client_id")String appkey,
                                       @Field("client_secret")String appSecret,
                                       @Field("grant_type")String authorization_code,
                                       @Field("code")String code,
                                       @Field("redirect_uri")String callbackuri);

    @GET("/bdc/search/")
    Observable<Response<WordResponse>> searchWord(@Query("word")String word,
                                                  @Query("access_token")String access_token);
}
