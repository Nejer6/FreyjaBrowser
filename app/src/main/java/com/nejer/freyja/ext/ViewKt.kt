package com.nejer.freyja.ext

import android.view.View
import android.view.WindowManager
import com.nejer.freyja.MainActivity

fun View.setFullScreen(enable: Boolean) {
    if (enable) {
        @Suppress("DEPRECATION")
        (context as? MainActivity)?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
    else {
        @Suppress("DEPRECATION")
        (context as? MainActivity)?.window?.clearFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}