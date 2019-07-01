package org.tangaya.barito.data.source;

import com.google.gson.JsonElement;
import org.tangaya.barito.data.model.APIResponseOld;
import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("/v2/top-headlines")
    Call<APIResponseOld> getHeadlineNewsOld(@Query("country") String country,
                                            @Query("apiKey") String apiKey);

    @GET("/v2/everything")
    Call<APIResponseOld> getNewsByKeyword(@Query("q") String keyword, @Query("apiKey") String key);

    @GET("/v2/top-headlines")
    Observable<JsonElement> getHeadlineNews(@Query("country") String country,
                                         @Query("apiKey") String apiKey);

}
