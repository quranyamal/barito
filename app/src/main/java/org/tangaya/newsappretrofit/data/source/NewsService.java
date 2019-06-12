package org.tangaya.newsappretrofit.data.source;

import org.tangaya.newsappretrofit.data.model.APIResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface NewsService {

    String API_KEY = "411b30da03f24aed986d77e276aec72e";

    @GET("/v2/top-headlines?country=id&apiKey=411b30da03f24aed986d77e276aec72e")
    Call<APIResponse> getHeadlineNews();

    @GET("/v2/everything")
    Call<APIResponse> getNewsByKeyword(@Query("q") String keyword, @Query("apiKey") String key);

}
