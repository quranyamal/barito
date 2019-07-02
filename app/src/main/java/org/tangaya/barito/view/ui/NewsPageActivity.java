package org.tangaya.barito.view.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import org.tangaya.barito.R;
import org.tangaya.barito.utlls.MyWebViewClient;

public class NewsPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        WebView webView = findViewById(R.id.news_webview);
        String url = getIntent().getStringExtra("url");

        webView.getSettings().setJavaScriptEnabled(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }
}
