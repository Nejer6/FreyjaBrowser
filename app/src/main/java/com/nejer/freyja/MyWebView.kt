package com.nejer.freyja

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import com.nejer.freyja.ext.setFullScreen
import java.io.ByteArrayInputStream

class MyWebView(context: Context, val videoLayout: FrameLayout) : WebView(context) {
    init {
        loadUrl(APP.url.value)

        settings.apply {
            useWideViewPort = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            allowContentAccess = true
            allowFileAccess = true
            databaseEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            javaScriptCanOpenWindowsAutomatically = false
            userAgentString = Constants.WebViewSettings.USER_AGENT
        }

        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                Log.d(Constants.Tags.URLS, request?.url.toString())

                val emptyWebResourceRequest = WebResourceResponse(
                    Constants.WebViewSettings.MIME_TYPE,
                    Constants.WebViewSettings.ENCODING,
                    ByteArrayInputStream("".encodeToByteArray())
                )

                Constants.WebViewSettings.BLOCK_LIST.forEach {
                    if (request!!.url.toString().contains(it)) {
                        return emptyWebResourceRequest
                    }
                }

                return super.shouldInterceptRequest(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                val http = Constants.WebViewSettings.HTTP
                val https = Constants.WebViewSettings.HTTPS

                if (url?.startsWith(http) == true) {
                    APP.url.value = url.removePrefix(http)
                }

                if (url?.startsWith(https) == true) APP.url.value = url.removePrefix(https)

                super.onPageStarted(view, url, favicon)
            }

        }

        webChromeClient = object : WebChromeClient() {


            override fun onShowCustomView(view: View, callback: CustomViewCallback) {
                videoLayout.apply {
                    addView(view)
                    visibility = View.VISIBLE
                    setFullScreen(true)
                }
            }

            override fun onHideCustomView() {
                videoLayout.apply {
                    removeAllViews()
                    visibility = View.GONE
                    setFullScreen(false)
                }
            }
        }

        true.also { settings.javaScriptEnabled = it }
    }
}