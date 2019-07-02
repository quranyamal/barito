package org.tangaya.barito.utlls;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, final String url) {
//        return super.shouldOverrideUrlLoading(view, url);
        System.out.println("hello!");

        return true;
    }
}
