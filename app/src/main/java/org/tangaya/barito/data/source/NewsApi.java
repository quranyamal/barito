package org.tangaya.barito.data.source;

import org.tangaya.barito.data.model.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    String API_KEY = "411b30da03f24aed986d77e276aec72e";

    @GET("/v2/top-headlines?country=id&apiKey=411b30da03f24aed986d77e276aec72e")
    Call<APIResponse> getHeadlineNews();

    @GET("/v2/everything")
    Call<APIResponse> getNewsByKeyword(@Query("q") String keyword, @Query("apiKey") String key);

}
