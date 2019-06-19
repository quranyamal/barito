package org.tangaya.barito.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ArticleAdapter;
import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.repository.HeadlineRepository;
import org.tangaya.barito.data.source.NewsApi;
import org.tangaya.barito.data.source.RetrofitService;
import org.tangaya.barito.view.listener.NewsRecyclerTouchListener;
import org.tangaya.barito.view.ui.NewsPageActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

//    private void loadHeadlineData() {
//        NewsApi service = RetrofitService.getInstance().create(NewsApi.class);
//
//        //Call<APIResponse> articlesCall = service.getHeadlineNews();
//
//        Call<APIResponse> articlesCall = service.getHeadlineNews();
//        articlesCall.enqueue(new Callback<APIResponse>() {
//
//            @Override
//            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
//                Log.d("enqueue", "onResponse. response: " + response);
//
//                dataLoading.postValue(false);
//
////                progressDialog.dismiss();
//                generateDataList(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<APIResponse> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), "Ups, terdapat kesalahan :(", Toast.LENGTH_SHORT).show();
//
//                Log.d("onFailure call", call.toString());
//                Log.d("onFailure throwable", t.toString());
//            }
//        });
//
//        private void generateDataList(final APIResponse apiResponse) {
//
//            Log.d("SearchResultActivity", "articleList size: " + apiResponse.getArticles().size());
//            recyclerView = getActivity().findViewById(R.id.headline_recycler);
//
//            adapter = new ArticleAdapter(getActivity(), apiResponse.getArticles());
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setAdapter(adapter);
//            recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getActivity().getApplicationContext(),
//                    recyclerView, new NewsRecyclerTouchListener.ClickListener() {
//
//                @Override
//                public void onClick(View view, int position) {
//                    Intent intent = new Intent(getActivity().getApplicationContext(), NewsPageActivity.class);
//                    intent.putExtra("url", apiResponse.getArticles().get(position).getUrl());
//
//                    startActivity(intent);
//
//                }
//            }));
//        }
//    }
}
