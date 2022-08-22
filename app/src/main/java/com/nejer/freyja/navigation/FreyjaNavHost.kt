package com.nejer.freyja.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nejer.freyja.Constants
import com.nejer.freyja.MainViewModel
import com.nejer.freyja.ui.screens.archive.NewArchive
import com.nejer.freyja.ui.screens.main.BrowserScreen

sealed class NavRoute(val route: String) {
    object Main: NavRoute(Constants.Screens.MAIN_SCREEN)
    object Archive: NavRoute(Constants.Screens.ARCHIVE_SCREEN)
    //object Folder: NavRoute("folder")
}

@Composable
fun FreyjaNavHost(mViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Main.route) {
        composable(NavRoute.Main.route) { BrowserScreen(navController = navController, viewModel = mViewModel) }
        composable(NavRoute.Archive.route) { NewArchive(navController = navController, viewModel = mViewModel) }
    }
}