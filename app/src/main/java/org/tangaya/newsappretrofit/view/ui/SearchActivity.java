package org.tangaya.newsappretrofit.view.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.tangaya.newsappretrofit.R;
import org.tangaya.newsappretrofit.databinding.ActivitySearchBinding;
import org.tangaya.newsappretrofit.view.navigator.SearchNavigator;
import org.tangaya.newsappretrofit.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity implements SearchNavigator {

    private SearchViewModel viewModel;
    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new SearchViewModel();
        viewModel.onActivityCreated(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setViewmodel(viewModel);
    }

    @Override
    public void onClickSearch(String keyword) {

        Log.d("SearchActivity", "searching news by keyword: " + keyword);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("keyword", keyword);
        startActivity(intent);
    }
}
