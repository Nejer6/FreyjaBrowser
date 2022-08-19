package com.nejer.freyja.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nejer.freyja.ui.screens.archive.ArchiveScreen
import com.nejer.freyja.ui.screens.main.BrowserScreen

sealed class NavRoute(val route: String) {
    object Main: NavRoute("main_screen")
    object Archive: NavRoute("archive_screen")
}

@Composable
fun FreyjaNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Main.route) {
        composable(NavRoute.Main.route) { BrowserScreen(navController = navController) }
        composable(NavRoute.Archive.route) { ArchiveScreen(navController = navController) }
    }
}