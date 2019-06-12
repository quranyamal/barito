package org.tangaya.newsappretrofit.view.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import org.tangaya.newsappretrofit.R;

public class NewsPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        WebView myWebView = (WebView) findViewById(R.id.news_webview);
        String url = getIntent().getStringExtra("url");
        myWebView.loadUrl(url);
    }
}
