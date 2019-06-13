package com.lieying.petcat.widget.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lieying.petcat.utils.FilePathUtils;


/**
 * Created by Administrator on 2016/5/26.
 * 自定义加载网页webview
 */
public class CommonWebView extends WebView {
    public static final String APP_CACHE_NAME = "webCache";
    private Context mContext;
    private OnWebViewCallBack mWebViewCall;
    private WebDownloadListener mDownloadListener;

    private WebSettings mSettings;
    private boolean isLoadSuccess = true;
    private boolean isLoadFinish = false;


    public CommonWebView(Context context) {
        super(context);
        init(context);
    }

    public CommonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setWebConfig();
    }

    public boolean isLoadSuccess() {
        return isLoadSuccess && isLoadFinish;
    }

    private void setWebConfig() {
        setDownloadListener(new DownloadClient());
        setWebChromeClient(new ChromeClient());
        setWebViewClient(new WebClient());
        mSettings = getSettings(); //webView的设置
        mSettings.setAppCachePath(FilePathUtils.getCanDeleteCacheExternalFilesDirChildPath(mContext, APP_CACHE_NAME));
        mSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        mSettings.setDatabaseEnabled(true);
        mSettings.setAppCacheEnabled(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mSettings.setGeolocationEnabled(true);
        mSettings.setSaveFormData(true);
        mSettings.setUseWideViewPort(true);
        mSettings.setSupportZoom(false); // 设置支持缩放
        mSettings.setBuiltInZoomControls(false); // 设置缩放
//        mSettings.setSupportMultipleWindows(true);//这个造成部分页面item无法点击
        mSettings.setAllowFileAccess(true);// 允许访问文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mSettings.setAllowContentAccess(true);
        }
        mSettings.setLoadWithOverviewMode(true);
        //19以下 先不加载图片，因为在网速差的时候，加载图片会造成宽带紧张，css和js加载异常，导致网页解析出问题
        mSettings.setLoadsImagesAutomatically(Build.VERSION.SDK_INT > 18);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {// 适应内容大小
            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        }
    }

    public void setOnWebViewCallBack(OnWebViewCallBack callBack) {
        this.mWebViewCall = callBack;
    }

    public void setDownloadListener(WebDownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;
    }

    public void setSupportZoom(boolean isZoom) {
        mSettings.setSupportZoom(isZoom); // 支持缩放
        mSettings.setBuiltInZoomControls(isZoom); // 设置缩放
    }

    public WebSettings getWebSettings() {
        return mSettings;
    }


    public void onDestroy() {
        stopLoading();
        destroy();
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (mWebViewCall != null) {
                mWebViewCall.onProgressChanged(view, newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mWebViewCall != null) {
                mWebViewCall.onReceivedTitle(view, title);
            }
        }
    }

    private class WebClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return mWebViewCall != null && mWebViewCall.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            isLoadSuccess = true;
            isLoadFinish = false;
            if (mWebViewCall != null) {
                mWebViewCall.onPageStarted(view, url, favicon);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            isLoadFinish = true;
            if (mWebViewCall != null) {
                mWebViewCall.onPageFinished(view, url);
            }
            if (!mSettings.getLoadsImagesAutomatically()) {
                mSettings.setLoadsImagesAutomatically(true);
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            isLoadSuccess = false;
            if (mWebViewCall != null) {
                mWebViewCall.onReceivedError(view, request, error);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
            handler.proceed();//忽略ssl错误
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            isLoadSuccess = false;
        }
    }

    private class DownloadClient implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            if (mDownloadListener != null) {
                mDownloadListener.onDownloadStart(url, userAgent, contentDisposition, mimetype, contentLength);
            }
        }
    }
}
