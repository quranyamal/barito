package org.tangaya.barito.data.source;

import com.google.gson.JsonElement;

import org.tangaya.barito.BuildConfig;
import org.tangaya.barito.data.model.APIResponseOld;
import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsApi {

    String authHeader = "X-Api-Key: " + BuildConfig.API_KEY;

    @Headers(authHeader)
    @GET("/v2/everything")
    Call<APIResponseOld> getNewsByKeyword(@Query("q") String keyword);

    @Headers(authHeader)
    @GET("/v2/top-headlines")
    Observable<JsonElement> getHeadlineNews(@Query("country") String country);

    @Headers(authHeader)
    @GET("/v2/top-headlines")
    Observable<JsonElement> getHeadlines(@Query("country") String country,
                                         @Query("category") String category,
                                         @Query("sources") String sources,
                                         @Query("q") String keywords,
                                         @Query("pageSize") Integer pageSize,
                                         @Query("page") Integer page);

    @Headers(authHeader)
    @GET("/v2/sources")
    Observable<JsonElement> getSources(@Query("category") String category,
                                       @Query("language") String language,
                                       @Query("country") String country);

}
