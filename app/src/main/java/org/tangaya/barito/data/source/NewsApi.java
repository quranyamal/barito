package org.tangaya.barito.data.source;

import org.tangaya.barito.BuildConfig;
import org.tangaya.barito.data.model.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("/v2/top-headlines")
    Call<APIResponse> getHeadlineNews(@Query("country") String country,
                                      @Query("apiKey") String apiKey);

    @GET("/v2/everything")
    Call<APIResponse> getNewsByKeyword(@Query("q") String keyword, @Query("apiKey") String key);

}
