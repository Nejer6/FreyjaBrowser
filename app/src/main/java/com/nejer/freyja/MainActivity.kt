package com.nejer.freyja

import android.app.Application
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nejer.freyja.navigation.FreyjaNavHost

class MainActivity : ComponentActivity() {

    val url = mutableStateOf(Constants.Urls.YANDEX)
    lateinit var videoLayout: FrameLayout
    lateinit var webView: MyWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        APP = this

        videoLayout = FrameLayout(this)
        webView = MyWebView(this, videoLayout).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(
                color = Constants.Colors.Yellow
            )
            val context = LocalContext.current
            val mViewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(context.applicationContext as Application)
            )

            mViewModel.initDatabase {}

            GetUrls(mViewModel)

            FreyjaNavHost(mViewModel)
        }
    }

    @Composable
    private fun GetUrls(mViewModel: MainViewModel) {
        mViewModel.changeFolder(mViewModel.getAllUrls().observeAsState(listOf()).value)
    }
}


