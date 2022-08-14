package com.nejer.freyja

import android.content.Context
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import com.nejer.freyja.ext.setFullScreen
import java.io.ByteArrayInputStream

class MyWebView(context: Context, val videoLayout: FrameLayout) : WebView(context) {
    init {
        loadUrl("https://google.com")
        Log.d("tag", "first activate")


        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

//            override fun shouldInterceptRequest(
//                view: WebView?,
//                request: WebResourceRequest?
//            ): WebResourceResponse? {
//                Log.d("tag", request?.url.toString())
//
//                val emptyWebResourceRequest = WebResourceResponse("text/plain", "utf8", ByteArrayInputStream("".encodeToByteArray()))
//
//                return super.shouldInterceptRequest(view, request) //emptyWebResourceRequest
//            }

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