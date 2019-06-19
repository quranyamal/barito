package org.tangaya.barito.data.repository;

import android.arch.lifecycle.MutableLiveData;

import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.source.NewsApi;
import org.tangaya.barito.data.source.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeadlineRepository {

    private static HeadlineRepository headlineRepository;

    public static HeadlineRepository getInstance() {
        if (headlineRepository == null) {
            headlineRepository = new HeadlineRepository();
        }
        return headlineRepository;
    }

    private NewsApi newsApi;

    public HeadlineRepository() {
        newsApi = RetrofitService.createService(NewsApi.class);
    }

    public MutableLiveData<APIResponse> getHeadline() {
        final MutableLiveData<APIResponse> headlineData = new MutableLiveData<>();
        newsApi.getHeadlineNews().enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    headlineData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                headlineData.setValue(null);
            }
        });

        return headlineData;
    }
}
