package com.nejer.freyja

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("tag", "activity activate")

        val videoLayout = FrameLayout(this)
        val webView = MyWebView(this, videoLayout)

        setContent {
            AndroidView(factory = { webView }, modifier = Modifier.fillMaxSize())
            AndroidView(factory = { videoLayout })
        }
    }
}

