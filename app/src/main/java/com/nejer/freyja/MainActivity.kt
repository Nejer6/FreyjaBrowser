package com.nejer.freyja

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.nejer.freyja.ui.theme.Orange

class MainActivity : ComponentActivity() {
    lateinit var videoLayout: FrameLayout
    lateinit var webView: MyWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tag", "activity activate")

        videoLayout = FrameLayout(this)
        webView = MyWebView(this, videoLayout)

        APP = this

        setContent {
            Browser()
        }
    }
}


@Composable
fun TopBar(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Orange)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun Browser() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_view_list_24),
                    contentDescription = "archive"
                )
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "save")
                }
            }

        }
        Web()
    }
}

@Composable
fun Web() {
    AndroidView(factory = { APP.webView }, modifier = Modifier.fillMaxSize())
    AndroidView(factory = { APP.videoLayout })
}

