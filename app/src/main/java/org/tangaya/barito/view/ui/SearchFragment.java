package org.tangaya.barito.view.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ArticleAdapter;
import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.source.NewsApi;
import org.tangaya.barito.data.source.RetrofitService;
import org.tangaya.barito.view.listener.NewsRecyclerTouchListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String searchKeyword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        //setTitle("Cari Berita: " + searchKeyword);

        handleIntent(getActivity().getIntent());

        return rootView;
    }

//    @Override
//    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
//        inflater.inflate(R.menu.search, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView sv = new SearchView(((YourActivity) getActivity()).getSupportActionBar().getThemedContext());
//        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
//        MenuItemCompat.setActionView(item, sv);
//        sv.setOnQueryTextListener(new OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                System.out.println("search query submit");
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                System.out.println("tap");
//                return false;
//            }
//        });
//    }

    private void handleIntent(Intent intent) {
        Log.d("SearchFragment", "handleIntent()");

        try {
         searchKeyword = getActivity().getIntent().getExtras().getString("keyword");
        } catch (NullPointerException e) {
            Log.e("SearchFragment", "NullPointerException");
            searchKeyword = "solok";
        }
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Toast.makeText(getActivity().getApplicationContext(), "search for " + query, Toast.LENGTH_SHORT).show();
            if (searchKeyword == null) {
                searchKeyword = query;
            }
        }

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("mengambil data artikel...");
        progressDialog.show();

        NewsApi service = RetrofitService.createService(NewsApi.class);

        Call<APIResponse> articlesCall = service.getNewsByKeyword(searchKeyword, NewsApi.API_KEY);
        articlesCall.enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                Log.d("enqueue", "onResponse. response: " + response);

                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Ups, terdapat kesalahan :(", Toast.LENGTH_SHORT).show();

                Log.d("onFailure call", call.toString());
                Log.d("onFailure throwable", t.toString());
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getActivity(), query + " submitted", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(getActivity(), newText, Toast.LENGTH_SHORT).show();
        return false;
    }

    private void generateDataList(final APIResponse apiResponse) {

        Log.d("SearchResultActivity", "articleList size: " + apiResponse.getArticles().size());
        recyclerView = getActivity().findViewById(R.id.news_recycler);

        adapter = new ArticleAdapter(getActivity(), apiResponse.getArticles());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getActivity().getApplicationContext(),
                recyclerView, new NewsRecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), NewsPageActivity.class);
                intent.putExtra("url", apiResponse.getArticles().get(position).getUrl());

                startActivity(intent);

            }
        }));
    }

}
