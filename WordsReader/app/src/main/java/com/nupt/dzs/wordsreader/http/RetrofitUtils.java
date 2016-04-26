package com.nupt.dzs.wordsreader.http;


import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dading on 2016/1/25.
 * 在使用的过程中 可以过滤显示OKhttp的log信息 查看调用信息
 * 接口定义时  占位符 用@PATH 参数使用@QUERY
 */
public class RetrofitUtils {
    private static OkHttpClient client;

    private static OkHttpClient getClient() {
        if (client == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder =  new OkHttpClient.Builder();
            builder.readTimeout(8,TimeUnit.SECONDS);
            builder.writeTimeout(8,TimeUnit.SECONDS);
            builder.connectTimeout(5,TimeUnit.SECONDS);
            builder.addInterceptor(loggingInterceptor);
            client = builder.build();
        }
        return client;
    }


    private static Retrofit.Builder builder;

    public static Retrofit.Builder getBuilder(String url) {
        if (builder == null) {
            builder = new Retrofit.Builder()
                    .client(getClient()) // setup okHttp client
                    .addConverterFactory(GsonConverterFactory.create(new Gson())).addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        }
        builder.baseUrl(url);
        return builder;
    }
}
