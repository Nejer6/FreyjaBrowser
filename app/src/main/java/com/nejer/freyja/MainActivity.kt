package com.nejer.freyja

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
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
        webView = MyWebView(this, videoLayout).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Browser(screen: MutableState<Int>) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar {
            var url by remember {
                mutableStateOf("pornhub.com")
            }
            val focusManager = LocalFocusManager.current

            IconButton(onClick = { screen.value = 1 }) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_view_list_24),
                    contentDescription = "archive"
                )
            }

            OutlinedTextField(
                value = url,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    url = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    APP.webView.loadUrl(url)
                }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )
            BackHandler(enabled = true) {
                focusManager.clearFocus()
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "save")
            }
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


