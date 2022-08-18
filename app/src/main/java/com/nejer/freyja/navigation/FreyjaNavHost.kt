package com.nejer.freyja.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nejer.freyja.ui.screens.archive.Archive
import com.nejer.freyja.ui.screens.main.Browser

sealed class NavRoute(val route: String) {
    object Main: NavRoute("main_screen")
    object Archive: NavRoute("archive_screen")
}

@Composable
fun FreyjaNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Main.route) {
        composable(NavRoute.Main.route) { Browser(navController = navController) }
        composable(NavRoute.Archive.route) { Archive(navController = navController) }
    }
}