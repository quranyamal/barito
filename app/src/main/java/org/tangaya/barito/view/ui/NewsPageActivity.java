package org.tangaya.barito.view.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import org.tangaya.barito.R;

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
