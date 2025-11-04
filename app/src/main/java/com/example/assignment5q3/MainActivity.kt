package com.example.assignment5q3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.assignment5q3.ui.theme.Assignment5Q3Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment5Q3Theme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    topBar = { AppTopBar(navController, currentRoute) }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        NavGraph(navController)
                    }
                }
            }
        }
    }
}

val sampleData = mapOf(
    "Museums" to listOf("Museum of Fine Arts", "MIT Museum", "Harvard Museum"),
    "Parks" to listOf("Boston Common", "Public Garden", "Hello"),
    "Restaurants" to listOf("Sea Foods", "Oyster", "Bakery")
)

@Composable
fun HomeScreen(navController: NavHostController) {
    BackHandler(enabled = true) {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            navController.navigate(Routes.Categories.route)
        }) {
            Text("Start Tour")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navController: NavHostController, currentRoute: String?) {
    TopAppBar(
        title = { Text("Explore Boston") },
        navigationIcon = {
            if (currentRoute != Routes.Home.route) {
                IconButton(onClick = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Home.route) { inclusive = true }
                    }
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Home")
                }
            }
        }
    )
}


@Composable
fun CategoriesScreen(navController: NavHostController) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(sampleData.keys.toList()) { category ->
            Text(
                text = category,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(Routes.List.createRoute(category))
                    }
            )
        }
    }
}

@Composable
fun ListScreen(navController: NavHostController, category: String) {
    val items = sampleData[category] ?: emptyList()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Text("All $category", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            itemsIndexed(items) { index, item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.Detail.createRoute(category, index))
                        }
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun DetailScreen(navController: NavHostController, category: String, id: Int) {
    val item = sampleData[category]?.getOrNull(id) ?: "Unknown"
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Text("Detail: $item", style = MaterialTheme.typography.titleLarge)
    }
}