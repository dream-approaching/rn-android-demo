package com.lieying.petcat.widget.web;

public interface WebDownloadListener {
        void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength);
    }