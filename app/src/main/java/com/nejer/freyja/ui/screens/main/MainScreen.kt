package com.nejer.freyja.ui.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.viewinterop.AndroidView
import com.nejer.freyja.APP
import com.nejer.freyja.R
import com.nejer.freyja.TopBar

@Composable
fun Browser(screen: MutableState<Int>) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar {
            var url by remember {
                mutableStateOf("google.com")
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