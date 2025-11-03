package com.example.assignment5q3

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            HomeScreen(navController)
        }
        composable(Routes.Categories.route) {
            CategoriesScreen(navController)
        }
        composable(
            route = Routes.List.routeWithArg,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            ListScreen(navController, category)
        }
        composable(
            route = Routes.Detail.routeWithArgs,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetailScreen(navController, category, id)
        }
    }
}
