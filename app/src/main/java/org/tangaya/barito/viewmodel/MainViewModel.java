package org.tangaya.barito.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.model.Article;
import org.tangaya.barito.data.repository.NewsRepository;
import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private NewsRepository newsRepository;

    public final MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();

    public void init() {

        if (newsRepository != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
    }

    public MutableLiveData<ArrayList<Article>> getHeadlines() {
        return newsRepository.getHeadlines();
    }

    public LiveData<APIResponse> getNewsByKeyword(String keyword) {
        return null;
    }

}
