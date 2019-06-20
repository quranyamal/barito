package org.tangaya.barito.data.repository;

import android.arch.lifecycle.MutableLiveData;
import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.source.NewsApi;
import org.tangaya.barito.data.source.RetrofitService;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private static NewsRepository newsRepository;
    private NewsApi service;

    private MutableLiveData<ArrayList<Article>> headlines, searchResult;

    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    public NewsRepository() {
        service = RetrofitService.createService(NewsApi.class);
        headlines = new MutableLiveData<>();
        searchResult = new MutableLiveData<>();
    }

    private void fetchHeadlines() {
        service.getHeadlineNews().enqueue(new Callback<APIResponse>() {
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
        Call<APIResponse> articlesCall = service.getNewsByKeyword(keyword, NewsApi.API_KEY);
        articlesCall.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    searchResult.postValue(response.body().getArticles());
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
}
