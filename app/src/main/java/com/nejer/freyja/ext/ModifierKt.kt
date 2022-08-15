package com.nejer.freyja.ext

import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsets.Type.ime
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import com.nejer.freyja.APP

//@ExperimentalLayoutApi
//fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
//    var isFocused by remember { mutableStateOf(false) }
//    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
//    if (isFocused) {
//
//        val density = LocalDensity.current
//
//
//
//        val imeIsVisible = WindowInsets.Type
//        val focusManager = LocalFocusManager.current
//        LaunchedEffect(imeIsVisible) {
//            if (imeIsVisible) {
//                keyboardAppearedSinceLastFocused = true
//            } else if (keyboardAppearedSinceLastFocused) {
//                focusManager.clearFocus()
//            }
//        }
//    }
//    onFocusEvent {
//        if (isFocused != it.isFocused) {
//            isFocused = it.isFocused
//            if (isFocused) {
//                keyboardAppearedSinceLastFocused = false
//            }
//        }
//    }
//}