package com.akshaym.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akshaym.weatherappcompose.ui.navigation.WeatherNavGraph
import com.akshaym.weatherappcompose.ui.theme.WeatherAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WeatherAppComposeTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { WeatherBottomBar(navController) }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        WeatherNavGraph(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherBottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Locations, BottomNavItem.Forecast
    )
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White.copy(alpha = 0.15f),
        tonalElevation = 8.dp
    ) {

        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = Color.White,
            windowInsets = WindowInsets(0.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.title,
                    onClick = {
                        navController.navigate(item.title)
                    },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                    enabled = true,
                    label = { Text(text = item.title, fontSize = 10.sp) },
                )
            }
        }
    }
}

sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Default.AddHome)
    object Forecast : BottomNavItem("Forecast", Icons.Default.Cloud)
    object Locations : BottomNavItem("Locations", Icons.Default.LocationOn)
    object Search : BottomNavItem("Search", Icons.Default.Search)
}