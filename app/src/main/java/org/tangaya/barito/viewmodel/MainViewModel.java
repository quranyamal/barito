package org.tangaya.barito.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.repository.NewsRepository;
import org.tangaya.barito.utlls.ApiResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainViewModel extends ViewModel {

    private NewsRepository newsRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> headlineApiResponse = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Article>> headlineArticles = new MutableLiveData<>();

    public final MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();
    public MutableLiveData<Integer> resultCount = new MutableLiveData<>();


    public void init() {

        if (newsRepository != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
    }

    public void hitHeadlineApi(String country, String apiKey) {

        disposables.add(newsRepository.executeFetchHeadline(country, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> headlineApiResponse.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> headlineApiResponse.setValue(ApiResponse.success(result)),
                        throwable -> headlineApiResponse.setValue(ApiResponse.error(throwable))
                ));

    }

//    public MutableLiveData<ArrayList<Article>> getHeadlinesOld() {
//        return newsRepository.getHeadlinesOld();
//    }

    public MutableLiveData<ApiResponse> getHeadlineApiResponse() {
        return headlineApiResponse;
    }

    public MutableLiveData<ArrayList<Article>> getHeadlineArticles() {
        return headlineArticles;
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

    @Override
    protected void onCleared() {
        disposables.clear();
    }

}
