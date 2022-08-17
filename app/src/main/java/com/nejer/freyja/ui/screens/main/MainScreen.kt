package com.nejer.freyja.ui.screens.main

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.nejer.freyja.APP
import com.nejer.freyja.R
import com.nejer.freyja.TopBar
import com.nejer.freyja.ui.theme.DarkBlue
import com.nejer.freyja.ui.theme.Orange

@Composable
fun Browser(screen: MutableState<Int>) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar {
            val focusManager = LocalFocusManager.current

            IconButton(onClick = { screen.value = 1 }) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = "archive",
                    tint = DarkBlue
                )
            }

            CompositionLocalProvider(
                LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = DarkBlue,
                    backgroundColor = DarkBlue.copy(alpha = 0.3f)
                )
            ) {
                BasicTextField(
                    modifier = Modifier
                        .background(
                            Orange,
                            RoundedCornerShape(20.dp)
                        )
                        .weight(1f)
                        .height(42.dp)
                        .padding(start = 15.dp),
                    value = APP.url.value,
                    onValueChange = {
                        APP.url.value = it
                    },
                    cursorBrush = SolidColor(DarkBlue),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 15.sp,
                        color = DarkBlue
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        APP.webView.loadUrl(APP.url.value)
                    }),
                    decorationBox = { innerTextField ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            innerTextField()
                        }
                    }
                )

                if (keyboardAsState().value == Keyboard.Closed) {
                    focusManager.clearFocus()
                }
            }

            BackHandler(enabled = true) {
                focusManager.clearFocus()
            }

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "save",
                    tint = DarkBlue
                )
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


enum class Keyboard {
    Opened, Closed
}

@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}