package org.tangaya.barito.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ArticleAdapter;
import org.tangaya.barito.data.model.APIResponseOld;
import org.tangaya.barito.view.listener.NewsRecyclerTouchListener;
import org.tangaya.barito.viewmodel.MainViewModel;

import timber.log.Timber;


public class SearchFragment extends Fragment {

    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    private TextView resultCount;

    private MainViewModel mViewModel;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("Timber log. onCreate");

        adapter = new ArticleAdapter(getActivity());

        mViewModel = ((MainActivity) getActivity()).getMainViewModel();
        mViewModel.init();
        mViewModel.getSearchResult().observe(getActivity(), articles -> {
            if (articles != null) {
                adapter.setData(articles);
                adapter.notifyDataSetChanged();
            }
        });

        Log.d("onCreate SF", "mViewModel initialized");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Timber.d("Timber log. onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

//        handleIntent(getActivity().getIntent());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Timber.d("Timber log. onViewCreated");

        resultCount = getActivity().findViewById(R.id.result_count);
        mViewModel.getResultCount().observe(getActivity(), count ->
                resultCount.setText("showing "+mViewModel.getSearchResult().getValue().size() +
                        "of " + count.toString()+" result(s)"));

        setupRecyclerView(view);
    }

    private void setupRecyclerView(View parent) {
        Timber.d("Timber log. setupRecyclerView");
        recyclerView = getActivity().findViewById(R.id.news_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getActivity().getApplicationContext(),
                recyclerView, (view, position) -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), NewsPageActivity.class);
            intent.putExtra("url", mViewModel.getSearchResult().getValue().get(position).getUrl());

            startActivity(intent);
        }));
    }

//    private void handleIntent(Intent intent) {
//        Log.d("SearchFragment", "handleIntent()");
//
//        try {
//         searchKeyword = getActivity().getIntent().getExtras().getString("keyword");
//        } catch (NullPointerException e) {
//            Log.e("SearchFragment", "NullPointerException");
//            searchKeyword = "solok";
//        }
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//
//            Toast.makeText(getActivity().getApplicationContext(), "search for " + query, Toast.LENGTH_SHORT).show();
//            if (searchKeyword == null) {
//                searchKeyword = query;
//            }
//
////            progressDialog = new ProgressDialog(getActivity());
////            progressDialog.setMessage("loading news...");
////            progressDialog.show();
//
////            NewsApi service = NewsAPIService.createService(NewsApi.class);
////            Call<APIResponseOld> articlesCall = service.getSearchResult(searchKeyword, NewsApi.API_KEY);
////            articlesCall.enqueue(new Callback<APIResponseOld>() {
////
////                @Override
////                public void onResponse(Call<APIResponseOld> call, Response<APIResponseOld> response) {
////                    Log.d("enqueue", "onResponse. response: " + response);
////
////                    progressDialog.dismiss();
////                    generateDataList(response.body());
////                }
////
////                @Override
////                public void onFailure(Call<APIResponseOld> call, Throwable t) {
////                    progressDialog.dismiss();
////                    Toast.makeText(getActivity(), "Ups, terdapat kesalahan :(", Toast.LENGTH_SHORT).show();
////
////                    Log.d("onFailure call", call.toString());
////                    Log.d("onFailure throwable", t.toString());
////                }
////            });
//
////            generateDataList();
//
//        }
//    }

    private void generateDataList(final APIResponseOld apiResponseOld) {

        Log.d("SearchResultActivity", "articleList size: " + apiResponseOld.getArticles().size());
//        recyclerView = getActivity().findViewById(R.id.news_recycler);
//
//        adapter = new ArticleAdapter(getActivity());
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//        recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getActivity().getApplicationContext(),
//                recyclerView, (view, position) -> {
//                    Intent intent = new Intent(getActivity().getApplicationContext(), NewsPageActivity.class);
//                    intent.putExtra("url", apiResponseOld.getArticles().get(position).getUrl());
//
//                    startActivity(intent);
//
//                }));
    }

}
