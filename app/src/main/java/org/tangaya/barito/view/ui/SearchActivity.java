package org.tangaya.barito.view.ui;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ArticleAdapter;
import org.tangaya.barito.view.listener.NewsRecyclerTouchListener;
import org.tangaya.barito.viewmodel.SearchViewModel;

import timber.log.Timber;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private SearchViewModel viewModel;
    private ArticleAdapter adapter;
    // todo: change adapter

    private SearchView searchView;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.init();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

            Timber.d("Search query: " + query);

            setTitle(query);
//            doMySearch(query);
            viewModel.searchNewsByKeyword(query);
        }

        adapter = new ArticleAdapter(this);

        viewModel.getSearchResult().observe(this, articles -> {
            Timber.d("Articles log: " + articles);
            if (articles != null) {
                adapter.setData(articles);
                adapter.notifyDataSetChanged();
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Timber.d("Timber log. setupRecyclerView");
        recyclerView = findViewById(R.id.news_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getApplicationContext(),
                recyclerView, (view, position) -> {
            Intent intent = new Intent(getApplicationContext(), NewsPageActivity.class);
            intent.putExtra("url", viewModel.getSearchResult().getValue().get(position).getUrl());

            startActivity(intent);
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(this);

        searchView.setQuery(query, false);
        searchView.clearFocus();

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit", "query: " + query);

        viewModel.searchNewsByKeyword(query);
        searchView.clearFocus();

        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("onQueryTextChange", "query: " + newText);
        return false;
    }
}
