package com.nupt.dzs.wordsreader.http.request;

import android.util.Log;

import com.nupt.dzs.wordsreader.http.RetrofitUtils;
import com.nupt.dzs.wordsreader.http.ShanBayApi;
import com.nupt.dzs.wordsreader.http.response.TokenResponse;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * code =ZCmCRIIRLuRsbGTMp3PDkYByzF3r6W;*/

/**
 * client_id         必须,创建应用时分配的App key
 client_secret     必须,创建应用时分配的App secret
 grant_type        必须，值为authorization_code
 code              必须，上一步获取的CODE
 redirect_uri      必须,需要和创建应用时填写的回调地址相同
 * @return
 */
public class TokenRequest{
    Subscription subscription;
    private String client_id = "636271677e1223dfebbd";
    private String client_secret = "6cd52bbde501032d27c8047ee86adf4ca710eb6f";
    private String grant_type = "authorization_code";
    private String code = "ZCmCRIIRLuRsbGTMp3PDkYByzF3r6W";
    private String redirect_uri = "https://api.shanbay.com/oauth2/auth/success/";
    IRequest<TokenResponse> listener;
    public TokenRequest(IRequest<TokenResponse> listener){
        this.listener = listener;
    }
    public void start() {
        Log.e("haha","start");
        RetrofitUtils.getBuilder(IRequest.baseUrl).build().create(ShanBayApi.class)
                .getToken(client_id,client_secret,grant_type,code,redirect_uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TokenResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.e("haha","complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure("error");
                        Log.e("haha","error"+e.getMessage());

                    }

                    @Override
                    public void onNext(TokenResponse tokenResponse) {
                        Log.e("haha",tokenResponse.toString());

                        if(tokenResponse.getAccess_token()!=null&&!tokenResponse.getAccess_token().equals("")){
                            listener.onResponse(tokenResponse);
                        }else {
                            listener.onFailure("认证失败");
                        }
                    }
                });
    }

    public void cancle() {
        if(!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
