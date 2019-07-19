package org.tangaya.barito.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.model.Source;
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

    private final MutableLiveData<ApiResponse> sourceApiResponse = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse> headlineApiResponse = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<Article>> headlineArticles = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Source>> sources = new MutableLiveData<>();

    public final MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();
    public MutableLiveData<Integer> resultCount = new MutableLiveData<>();


    public void init() {

        if (newsRepository != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
    }

    public MutableLiveData<ArrayList<Source>> getSources() {
        return newsRepository.getSources();
    }

    public void hitSourceApi(String category, String language, String country) {
        Timber.d("hitSourceApi called");
        disposables.add(newsRepository.fetchSeources(category, language, country)
                .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe((d) -> sourceApiResponse.postValue(ApiResponse.loading()))
            .subscribe(
                    result -> {
                        sourceApiResponse.postValue(ApiResponse.success(result));
                        Timber.d(result.toString());
                    },
                    throwable -> {
                        sourceApiResponse.postValue(ApiResponse.error(throwable));
                        Timber.d(throwable.toString());
                    }
            ));
    }

    public void hitHeadlineApi(String country) {

        disposables.add(newsRepository.fetchHeadline(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> headlineApiResponse.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> headlineApiResponse.setValue(ApiResponse.success(result)),
                        throwable -> headlineApiResponse.setValue(ApiResponse.error(throwable))
                ));
    }
//
//    public void hitHeadlineApiNew(@Nullable String country, @Nullable String category,
//                                  @Nullable String source, @Nullable String keywords,
//                                  @Nullable Integer pageSize, @Nullable Integer page) {
//        disposables.add(newsRepository.fetchHeadlineNew(country, category, source, keywords, pageSize, page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe((d) -> headlineApiResponse.setValue(ApiResponse.loading()))
//                .subscribe(
//                        result -> headlineApiResponse.setValue(ApiResponse.success(result)),
//                        throwable -> headlineApiResponse.setValue(ApiResponse.error(throwable))
//                ));
//    }

    public MutableLiveData<ApiResponse> getSourceApiResponse() {
        return sourceApiResponse;
    }

    public MutableLiveData<ApiResponse> getHeadlineApiResponse() {
        return headlineApiResponse;
    }

    public MutableLiveData<ArrayList<Article>> getHeadlineArticles() {
        return headlineArticles;
    }

//    public void searchNewsByKeyword(String keyword) {
//        Timber.d(keyword + " submitted. log by timber");
//        Log.d("searchNewsByKeyword", keyword + " submitted. manual logging");
//        newsRepository.searchNewsByKeyword(keyword);
//    }

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
