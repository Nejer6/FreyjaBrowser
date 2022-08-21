package com.nejer.freyja

import android.app.Application
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nejer.freyja.navigation.FreyjaNavHost
import com.nejer.freyja.ui.theme.Yellow

class MainActivity : ComponentActivity() {
    val url = mutableStateOf("yandex.ru/")
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
            val context = LocalContext.current
            val mViewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(context.applicationContext as Application)
            )
            /*todo*/
            mViewModel.initDatabase { Log.d("tag", "database initialized") }

            GetUrls(mViewModel)

            FreyjaNavHost(mViewModel)
        }
    }

    @Composable
    private fun GetUrls(mViewModel: MainViewModel) {
        mViewModel.changeFolder(mViewModel.getAllUrls().observeAsState(listOf()).value)
    }
}

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


