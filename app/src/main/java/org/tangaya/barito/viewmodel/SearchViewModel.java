package org.tangaya.barito.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.repository.NewsRepository;

import java.util.ArrayList;

import timber.log.Timber;

public class SearchViewModel extends ViewModel {

    private NewsRepository newsRepository;

    private MutableLiveData<ArrayList<Article>> articles;

    public void init() {

        if (newsRepository != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
    }

    public LiveData<ArrayList<Article>> getArticles() {
        if (articles == null) {
            articles = new MutableLiveData<ArrayList<Article>>();
            loadArticles();
        }
        return articles;
    }

    private void loadArticles() {
    }

    public void searchNewsByKeyword(String keyword) {
        Timber.d(keyword + " submitted. log by timber");
        Log.d("searchNewsByKeyword", keyword + " submitted. manual logging");
        newsRepository.searchNewsByKeyword(keyword);
    }

    public MutableLiveData<ArrayList<Article>> getSearchResult() {
        return newsRepository.getSearchResult();
    }

}
