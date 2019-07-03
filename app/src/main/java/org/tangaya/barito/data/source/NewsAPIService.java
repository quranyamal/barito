package org.tangaya.barito.data.source;

import org.tangaya.barito.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsAPIService {

    private static final String BASE_URL = BuildConfig.API_BASE_URL;

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static <s> s createService(Class<s> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static Retrofit getInstance() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

//        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
//        okhttpBuilder.addInterceptor(loggingInterceptor);
//        okhttpBuilder.addInterceptor(chain -> {
//            Request request = chain.request();
//            Request.Builder newRequest = request.newBuilder().header("X-Api-Key",BuildConfig.API_KEY);
//            return chain.proceed(newRequest.build());
//        });

        if (retrofit==null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
