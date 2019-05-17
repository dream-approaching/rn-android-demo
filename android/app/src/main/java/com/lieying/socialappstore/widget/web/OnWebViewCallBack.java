package com.lieying.socialappstore.widget.web;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

public interface OnWebViewCallBack {
        void onProgressChanged(WebView view, int newProgress);

        void onReceivedTitle(WebView view, String title);

        boolean shouldOverrideUrlLoading(WebView view, String url);

        void onPageStarted(WebView view, String url, Bitmap favicon);

        void onPageFinished(WebView view, String url);

        void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);
}