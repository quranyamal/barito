package org.tangaya.barito.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.repository.NewsRepository;
import java.util.ArrayList;

import javax.security.auth.callback.CallbackHandler;

import timber.log.Timber;

public class MainViewModel extends ViewModel {

    private NewsRepository newsRepository;

    public final MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();
    public MutableLiveData<Integer> resultCount = new MutableLiveData<>();

    public void init() {

        if (newsRepository != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
    }

    public MutableLiveData<ArrayList<Article>> getHeadlines() {
        return newsRepository.getHeadlines();
    }

    public void searchNewsByKeyword(String keyword) {
        Timber.d(keyword + " submitted. log by timber");
        Log.d("searchNewsByKeyword", keyword + " submitted. manual logging");
        newsRepository.searchNewsByKeyword(keyword);
    }

    public MutableLiveData<ArrayList<Article>> getSearchResult() {
        return newsRepository.getSearchResult();
    }

    public MutableLiveData<Integer> getResultCount() {
        return newsRepository.getResultCount();
    }

}
