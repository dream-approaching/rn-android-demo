package com.lieying.socialappstore.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.constant.Constants;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseActivity;
import com.lieying.socialappstore.widget.TitleView;
import com.lieying.socialappstore.widget.web.CommonWebView;
import com.lieying.socialappstore.widget.web.OnWebViewCallBack;

public class WebActivity extends BaseActivity implements OnWebViewCallBack {
    private TitleView mTitleV;
    private CommonWebView mWebView;

    public static void startWeb(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Constants.KEY_WEB_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(false).keyboardEnable(true).init();
        setContentView(R.layout.activity_web);
    }

    @Override
    public void findView() {
        mTitleV = findViewById(R.id.view_title);
        mWebView = findViewById(R.id.web_view);
    }

    @Override
    public void initView() {
        mWebView.setOnWebViewCallBack(this);
        mWebView.setSupportZoom(true);
    }

    @Override
    public void initData() {
        String url = getIntent().getStringExtra(Constants.KEY_WEB_URL);
        mWebView.loadUrl(url);
    }

    @Override
    public void initListener() {
        mTitleV.setClickBackFinish(this);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        mTitleV.setTitle(title);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(WebView view, String url) {
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
