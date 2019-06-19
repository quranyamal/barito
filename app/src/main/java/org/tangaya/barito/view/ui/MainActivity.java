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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import org.tangaya.barito.R;
import org.tangaya.barito.adapter.ViewPagerAdapter;
import org.tangaya.barito.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements HeadlineFragment.OnFragmentInteractionListener {

    String searchKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(vpAdapter);

        tabLayout.setupWithViewPager(viewPager);
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("onQueryTextSubmit", "string: " + s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("onQueryTextChange", "string: " + s);
                return false;
            }
        });

        return true;
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

    @Override
    public void onFragmentInteraction(Uri uri) {
        // todo
    }
}
