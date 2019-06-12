package org.tangaya.newsappretrofit.viewmodel;

import android.databinding.ObservableField;
import android.util.Log;

import org.tangaya.newsappretrofit.view.navigator.SearchNavigator;

public class SearchViewModel {

    private SearchNavigator navigator;

    public final ObservableField<String> keyword = new ObservableField<>();

    public SearchViewModel() {}

    public void onActivityCreated(SearchNavigator navigator) {
        this.navigator = navigator;
    }

    public void searchNews() {
        Log.d("SearchViewModel", "should go to main activity");
        navigator.onClickSearch(keyword.get());
    }
}
