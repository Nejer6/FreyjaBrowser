package com.nejer.freyja

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.nejer.freyja.models.Branch
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
            Navigation()
        }
    }
}


@Composable
fun TopBar(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Orange),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
fun Browser(screen: MutableState<Int>) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar {
            var url by remember {
                mutableStateOf("google.com")
            }

            IconButton(onClick = { screen.value = 1 }) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_view_list_24),
                    contentDescription = "archive"
                )
            }

            TextField(value = url, modifier = Modifier.weight(1f), onValueChange = {
                if (it.lastOrNull() == '\n') {
                    APP.webView.loadUrl(url)
                } else {
                    url = it
                }

            })

            //Box(contentAlignment = Alignment.CenterEnd) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "save")
                }
            //}

        }
        Web()

    }
    AndroidView(factory = { APP.videoLayout })
}

@Composable
fun Web() {
    AndroidView(factory = { APP.webView }, modifier = Modifier.fillMaxSize())

    BackHandler(enabled = true) {
        if (APP.webView.canGoBack()) {
            APP.webView.goBack()
        } else {
            APP.finish()
        }
    }
}


@Composable
fun Folder(screen: MutableState<Int>) {
    var listBranches = remember {
            mutableStateListOf(
                Branch(
                    "root", mutableListOf(
                        Branch(
                            "first child", mutableListOf(
                                Branch("thee branch")
                            )
                        ),
                        Branch("second child")
                    )
                )
            )

    }

    LazyColumn() {
        items(
            listBranches.last().children
        ) { branch ->
            Text(text = branch.value, modifier = Modifier.clickable {
                listBranches.add(branch)
            })
        }
    }

    BackHandler(enabled = true) {
        if (listBranches.size > 1) {
            listBranches.removeLast()
        } else {
            screen.value = 0
        }
    }
}

@Composable
fun Archive(screen: MutableState<Int>) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar {
            IconButton(onClick = { APP.onBackPressed() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "back to web"
                )
            }
        }

        Folder(screen)
    }

}

@Composable
fun Navigation() {
    val screen = remember {
        mutableStateOf(0)
    }

    when (screen.value) {
        0 -> Browser(screen)
        else -> Archive(screen)
    }
}


