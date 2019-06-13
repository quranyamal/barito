package org.tangaya.barito.view.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.tangaya.barito.R;
import org.tangaya.barito.databinding.ActivitySearchBinding;
import org.tangaya.barito.view.navigator.SearchNavigator;
import org.tangaya.barito.viewmodel.SearchViewModelOld;

public class SearchActivity extends AppCompatActivity implements SearchNavigator {

    private SearchViewModelOld viewModel;
    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new SearchViewModelOld();
        viewModel.onActivityCreated(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        // binding.setViewmodel(viewModel);
    }

    @Override
    public void onClickSearch(String keyword) {

        Log.d("SearchActivity", "searching news by keyword: " + keyword);

        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("keyword", keyword);
        startActivity(intent);
    }
}
