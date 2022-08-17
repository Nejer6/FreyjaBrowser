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
        Log.d("tag", "first activate")
        settings.apply {
            useWideViewPort = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            allowContentAccess = true
            allowFileAccess = true
            databaseEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            javaScriptCanOpenWindowsAutomatically = false
        }



        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request!!.url.toString()


                return false
            }



            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                val emptyWebResourceRequest = WebResourceResponse("text/plain", "utf8", ByteArrayInputStream("".encodeToByteArray()))
                listOf(
                    "zyf03k.xyz",
                    "http://mvd-tl.online",
                    "i.bimbolive.com",
                    "ht-cdn.trafficjunky.net",
                    "hw-cdn2.adtng.com",
                    "rf.bongacams25.com",
                    "appzery.com"
                ).forEach {
                    if (request!!.url.toString().contains(it)) {
                        return emptyWebResourceRequest
                    }
                }

                Log.d("tag", request?.url.toString())

                return super.shouldInterceptRequest(view, request) //emptyWebResourceRequest
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                val http = "http://"
                val https = "https://"

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