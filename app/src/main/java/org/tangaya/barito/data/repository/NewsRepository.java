package org.tangaya.barito.data.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.JsonElement;

import org.tangaya.barito.BuildConfig;
import org.tangaya.barito.data.model.APIResponseOld;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.source.NewsApi;
import org.tangaya.barito.data.source.NewsAPIService;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NewsRepository {

    private static NewsRepository newsRepository;
    private NewsApi service;

    private MutableLiveData<ArrayList<Article>> headlines, searchResult;
    private MutableLiveData<Integer> resultCount;

    private String apiKey = BuildConfig.API_KEY;

    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    public NewsRepository() {
        service = NewsAPIService.createService(NewsApi.class);
        headlines = new MutableLiveData<>();
        searchResult = new MutableLiveData<>();
        resultCount = new MutableLiveData<>();
    }

//    private void fetchHeadlinesOld() {
//        Timber.d("old fetch headline fired!!");
//        service.getHeadlineNewsOld("id", apiKey).enqueue(new Callback<APIResponseOld>() {
//            @Override
//            public void onResponse(Call<APIResponseOld> call, Response<APIResponseOld> response) {
//                if (response.isSuccessful()) {
//                    headlines.setValue(response.body().getArticles());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<APIResponseOld> call, Throwable t) {
//                headlines.setValue(null);
//            }
//        });
//    }

    public Observable<JsonElement> executeFetchHeadline(String country, String apiKey) {
        Timber.d("new fetch headline fired!!");
        return service.getHeadlineNews(country, apiKey);
    }

//    public MutableLiveData<ArrayList<Article>> getHeadlinesOld() {
//        fetchHeadlinesOld();
//        // async! todo!
//        return headlines;
//    }

    public void searchNewsByKeyword(String keyword) {
        Log.d("searchNewsByKeyword", "manual logging. keyword: "+keyword);
        Timber.d("inside searchNewsByKeyword");

        Call<APIResponseOld> articlesCall = service.getNewsByKeyword(keyword, apiKey);
        articlesCall.enqueue(new Callback<APIResponseOld>() {
            @Override
            public void onResponse(Call<APIResponseOld> call, Response<APIResponseOld> response) {
                Timber.d("inside onResponse");
                if (response.isSuccessful()) {
                    Timber.d(keyword + " submitted");
                    Log.d("onResponse", response.toString());
                    searchResult.postValue(response.body().getArticles());
                    resultCount.postValue(response.body().getTotalResults());
                }
            }

            @Override
            public void onFailure(Call<APIResponseOld> call, Throwable t) {
                searchResult.postValue(null);
            }
        });
    }

    public MutableLiveData<ArrayList<Article>> getSearchResult() {
        return searchResult;
    }

    public MutableLiveData<Integer> getResultCount() {
        return resultCount;
    }
}
