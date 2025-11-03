package com.example.assignment5q3


sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Categories : Routes("categories")
    object List : Routes("list/{category}") {
        fun createRoute(category: String) = "list/$category"
        const val routeWithArg = "list/{category}"
    }
    object Detail : Routes("detail/{category}/{id}") {
        fun createRoute(category: String, id: Int) = "detail/$category/$id"
        const val routeWithArgs = "detail/{category}/{id}"
    }
}