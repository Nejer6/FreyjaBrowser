package com.nejer.freyja

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nejer.freyja.navigation.FreyjaNavHost
import com.nejer.freyja.ui.theme.Yellow

class MainActivity : ComponentActivity() {
    val url = mutableStateOf("yandex.ru")
    lateinit var videoLayout: FrameLayout
    lateinit var webView: MyWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tag", "activity activate")

        APP = this

        videoLayout = FrameLayout(this)
        webView = MyWebView(this, videoLayout).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        setContent {
            FreyjaNavHost()
        }
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 1040, device = "ZTE Blade Smart 20 API 28")
@Composable
fun TopBar(content: @Composable () -> Unit = {}) {
    Row(
        modifier = Modifier
            .background(Yellow)
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}


