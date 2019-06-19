package org.tangaya.barito.view.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProvider;
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
import android.view.View;
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

        Log.d("MainActivity", "onCreate 1");

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Log.d("MainActivity", "onCreate 2");
        TabLayout tabLayout = findViewById(R.id.tab_layout);

//        ViewPager viewPager = findViewById(R.id.view_pager);
        final ViewPager viewPager =(ViewPager)findViewById(R.id.view_pager);
        Log.d("MainActivity", "onCreate 3");
        viewPager.setAdapter(vpAdapter);

        Log.d("MainActivity", "onCreate 4");
//        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

//        HeadlineFragment headlineFragment = obtainHeadlineFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.

//        handleIntent(getIntent());
    }

//    @Override
//    public void onCreateView(View view, Bundle savedInstanceState) {
//        vpAdapter = new ViewPagerAdapter(getChildFragmentManager());
//        viewPager = view.findViewById(R.id.view_pager);
//        viewPager.setAdapter(vpAdapter);
//    }

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
