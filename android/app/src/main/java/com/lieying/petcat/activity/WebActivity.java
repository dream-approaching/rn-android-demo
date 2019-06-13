package com.lieying.petcat.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.gyf.immersionbar.ImmersionBar;
import com.lieying.comlib.constant.Constants;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseActivity;
import com.lieying.petcat.widget.TitleView;
import com.lieying.petcat.widget.web.CommonWebView;
import com.lieying.petcat.widget.web.OnWebViewCallBack;

public class WebActivity extends BaseActivity implements OnWebViewCallBack {
    private TitleView mTitleV;
    private CommonWebView mWebView;
    private boolean showTitle = false;
    public static void startWeb(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Constants.KEY_WEB_URL, url);
        context.startActivity(intent);
    }

    public static void startWeb(Context context, String url , boolean showTitle) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Constants.KEY_WEB_URL, url);
        intent.putExtra(Constants.KEY_WEB_SHOW_TITLE, showTitle);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true).init();
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
        mWebView.setSupportZoom(false);
    }

    @Override
    public void initData() {
        String url = getIntent().getStringExtra(Constants.KEY_WEB_URL);
        showTitle = getIntent().getBooleanExtra(Constants.KEY_WEB_SHOW_TITLE , false);
        mWebView.loadUrl(url);
        mTitleV.setVisibility(showTitle? View.VISIBLE : View.GONE);
        mTitleV.setTitle("树莓用户协议及隐私政策");
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
//        mTitleV.setTitle(title);
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
