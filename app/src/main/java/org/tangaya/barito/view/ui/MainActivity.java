package org.tangaya.barito.view.ui;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ViewPagerAdapter;
import org.tangaya.barito.viewmodel.MainViewModel;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements HeadlineFragment.OnFragmentInteractionListener, SearchView.OnQueryTextListener {

    String searchKeyword;
    private ViewPager viewPager;
    private MainViewModel mViewModel;
    private SearchView searchView;

    public MainViewModel getMainViewModel() {
        return mViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        Timber.d("starting onCreate");

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(vpAdapter);

        tabLayout.setupWithViewPager(viewPager);

        Timber.d("end of onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Timber.d("starting onCreateOptionsMenu");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(this);

        Timber.d("end of onCreateOptionsMenu");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
//                searchView.requestFocusFromTouch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public SearchView getSearchView() {
        return searchView;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("MainActivity", "handleIntent");

        if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Toast.makeText(getApplicationContext(), "search for " + query, Toast.LENGTH_SHORT).show();
            if (searchKeyword == null) {
                searchKeyword = query;
            }
        }
    }

    public static MainViewModel obtainViewModel(MainActivity activity) {

        return ViewModelProviders.of(activity).get(MainViewModel.class);
    }

    @NonNull
    private HeadlineFragment obtainHeadlineFragment() {

        HeadlineFragment headlineFragment = (HeadlineFragment)getSupportFragmentManager()
                .findFragmentById(R.id.headline_frame);

        if(headlineFragment == null ) {
            headlineFragment = HeadlineFragment.newInstance();
        }

        return headlineFragment;
    }

    private SearchFragment obtainSearchFragment() {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager()
                .findFragmentById(R.id.search_frame);

        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
        }
        return searchFragment;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        // todo
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit", "query: " + query);

        mViewModel.searchNewsByKeyword(query);
        viewPager.setCurrentItem(1);
        searchView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("onQueryTextChange", "query: " + newText);
        return false;
    }
}
