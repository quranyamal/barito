package org.tangaya.barito.data.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import org.tangaya.barito.BuildConfig;
import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.source.NewsApi;
import org.tangaya.barito.data.source.NewsAPIService;

import java.util.ArrayList;
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

    private void fetchHeadlines() {
        service.getHeadlineNews("id", apiKey).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    headlines.setValue(response.body().getArticles());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                headlines.setValue(null);
            }
        });
    }

    public MutableLiveData<ArrayList<Article>> getHeadlines() {
        fetchHeadlines();
        // async! todo!
        return headlines;
    }

    public void searchNewsByKeyword(String keyword) {
        Log.d("searchNewsByKeyword", "manual logging. keyword: "+keyword);
        Timber.d("inside searchNewsByKeyword");

        Call<APIResponse> articlesCall = service.getNewsByKeyword(keyword, apiKey);
        articlesCall.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                Timber.d("inside onResponse");
                if (response.isSuccessful()) {
                    Timber.d(keyword + " submitted");
                    Log.d("onResponse", response.toString());
                    searchResult.postValue(response.body().getArticles());
                    resultCount.postValue(response.body().getTotalResults());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
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
