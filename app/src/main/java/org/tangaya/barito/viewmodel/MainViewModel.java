package org.tangaya.barito.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.repository.HeadlineRepository;

public class MainViewModel extends ViewModel {

    private MutableLiveData<APIResponse> headlineLiveData;
    private HeadlineRepository headlineRepository;

    public final MutableLiveData<Boolean> dataLoading = new MutableLiveData<>();

    public void init() {

        if (headlineRepository != null) {
            return;
        }
        headlineRepository = HeadlineRepository.getInstance();
        headlineLiveData = headlineRepository.getHeadline();
    }

    public LiveData<APIResponse> getHeadlineRepository() {
        return headlineLiveData;
    }

}
