package org.tangaya.barito.view.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ArticleAdapter;
import org.tangaya.barito.data.model.APIResponse;
import org.tangaya.barito.data.source.NewsService;
import org.tangaya.barito.data.source.RESTClient;
import org.tangaya.barito.view.listener.NewsRecyclerTouchListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String searchKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchKeyword = getIntent().getExtras().getString("keyword");
        setTitle("Cari Berita: " + searchKeyword);

        handleIntent(getIntent());

        progressDialog = new ProgressDialog(SearchResultActivity.this);
        progressDialog.setMessage("mengambil data artikel...");
        progressDialog.show();

        NewsService service = RESTClient.getInstance().create(NewsService.class);

        //Call<APIResponse> articlesCall = service.getHeadlineNews();

        Call<APIResponse> articlesCall = service.getNewsByKeyword(searchKeyword, NewsService.API_KEY);
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
                Toast.makeText(SearchResultActivity.this, "Ups, terdapat kesalahan :(", Toast.LENGTH_SHORT).show();

                Log.d("onFailure call", call.toString());
                Log.d("onFailure throwable", t.toString());
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Toast.makeText(getApplicationContext(), "search for " + query, Toast.LENGTH_SHORT).show();
            if (searchKeyword == null) {
                searchKeyword = query;
            }
        }
    }

    private void generateDataList(final APIResponse apiResponse) {

        Log.d("SearchResultActivity", "articleList size: " + apiResponse.getArticles().size());
        recyclerView = findViewById(R.id.news_recycler);

        adapter = new ArticleAdapter(this, apiResponse.getArticles());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchResultActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getApplicationContext(),
                recyclerView, new NewsRecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), NewsPageActivity.class);
                intent.putExtra("url", apiResponse.getArticles().get(position).getUrl());

                startActivity(intent);

            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


}
