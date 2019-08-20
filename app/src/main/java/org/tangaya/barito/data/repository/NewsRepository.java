package org.tangaya.barito.data.repository;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonElement;

import org.tangaya.barito.data.model.APIResponseOld;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.model.Source;
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
    private MutableLiveData<ArrayList<Source>> sources;
    private MutableLiveData<Integer> resultCount;

    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    public NewsRepository() {
        service = NewsAPIService.createService(NewsApi.class);
        headlines = new MutableLiveData<>();
        sources = new MutableLiveData<>();
        searchResult = new MutableLiveData<>();
        resultCount = new MutableLiveData<>();
    }

    public Observable<JsonElement> fetchSeources(String category, String language, String country) {
        Log.d("NewsRepo", "Fetching news sources...");
        return service.getSources(category, language, country);
    }

    public Observable<JsonElement> fetchHeadline(String country) {
        Log.d("NewsRepo", "Fetching headline.. country="+country);
        if (country.equals("All")) {
            return service.getHeadlines(null,null, null, null, 12, null);
        } else {
            return service.getHeadlines(country, null, null, null, 12, null);
        }
    }

    public Observable<JsonElement> fetchHeadlineNew(@Nullable String country, @Nullable String category,
                                                    @Nullable String source, @Nullable String keywords,
                                                    @Nullable Integer pageSize, @Nullable Integer page) {
        return service.getHeadlines(country, category, source, keywords, pageSize, page);
    }

    public void searchNewsByKeyword(String keyword) {
        Timber.d("Search news fired by keyword: " + keyword);

        Call<APIResponseOld> articlesCall = service.getEverything(keyword, null, null,
                null, null, null, null, null,
                null, null);

        articlesCall.enqueue(new Callback<APIResponseOld>() {
            @Override
            public void onResponse(Call<APIResponseOld> call, Response<APIResponseOld> response) {
                Timber.d("inside onResponse");
                Log.d("onResponse", response.toString());
                if (response.isSuccessful()) {
                    Timber.d(keyword + " submitted");
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

    public MutableLiveData<ArrayList<Source>> getSources() {
        return sources;
    }

    public MutableLiveData<ArrayList<Article>> getSearchResult() {
        return searchResult;
    }

    public MutableLiveData<Integer> getResultCount() {
        return resultCount;
    }
}
