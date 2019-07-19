package org.tangaya.barito.view.ui;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

    private SharedPreferences sharedPref;

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
        mViewModel.init();

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(vpAdapter);

        tabLayout.setupWithViewPager(viewPager);

        Timber.d("end of onCreate");

        Context context = getApplication();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Timber.d("starting onCreateOptionsMenu");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        Timber.d("end of onCreateOptionsMenu");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menu_bookmark:
                intent = new Intent(getApplicationContext(), BookmarkActivity.class);
                startActivity(intent);
            case R.id.menu_search:
                searchView.requestFocusFromTouch();
                return true;
            case R.id.menu_setting:
                intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_about:
                intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    protected void onResume() {
        super.onResume();

        int countryId = sharedPref.getInt(getString(R.string.saved_country_key),0);
        String country = getResources().getStringArray(R.array.countries_array)[countryId];
        mViewModel.hitHeadlineApi(country);
        mViewModel.hitSourceApi(null, null, null);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // todo
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit", "query: " + query);

//        mViewModel.searchNewsByKeyword(query);
//        viewPager.setCurrentItem(1);
        searchView.clearFocus();

        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        startActivity(intent);

        searchView.setQuery("", false);
        searchView.setIconified(true);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("onQueryTextChange", "query: " + newText);
        return false;
    }
}
