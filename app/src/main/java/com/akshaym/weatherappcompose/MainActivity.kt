package com.akshaym.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akshaym.weatherappcompose.ui.navigation.WeatherNavGraph
import com.akshaym.weatherappcompose.ui.theme.BottomNavActiveIndicator
import com.akshaym.weatherappcompose.ui.theme.BottomNavBackground
import com.akshaym.weatherappcompose.ui.theme.BottomNavContentSelected
import com.akshaym.weatherappcompose.ui.theme.BottomNavContentUnselected
import com.akshaym.weatherappcompose.ui.theme.WeatherAppComposeTheme
import com.akshaym.weatherappcompose.ui.theme.WeatherGradientBottom
import com.akshaym.weatherappcompose.ui.theme.WeatherGradientTop
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            WeatherAppComposeTheme {
                val brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary, // #4A90E2
                        MaterialTheme.colorScheme.tertiary // #4332E6
                    )
                )
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush
                        ),
                    containerColor = WeatherGradientTop,
                    bottomBar = { WeatherBottomBar(navController) }) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding),
                    ) {
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
            .fillMaxWidth(),
        tonalElevation = 0.dp,
        color = Color.Transparent,
        shadowElevation = 0.dp
    ) {
        val b = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF4A90E2), // Top of pill
                Color(0xFF4332E6)
            )
        )
        Box(modifier = Modifier.background(brush = b)) {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                contentColor = BottomNavContentSelected,
                containerColor = Color.Transparent,
                windowInsets = WindowInsets(0.dp),
                tonalElevation = 0.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    val isSelected = currentRoute == item.title
                    NavigationBarItem(
                        selected = currentRoute == item.title,
                        onClick = {
                            navController.navigate(item.title)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (isSelected) BottomNavContentSelected else BottomNavContentUnselected
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BottomNavContentSelected,
                            unselectedIconColor = BottomNavContentUnselected,
                            indicatorColor = BottomNavActiveIndicator,
                            selectedTextColor = BottomNavContentSelected,
                            unselectedTextColor = BottomNavContentUnselected
                        ),
                        enabled = true,
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 10.sp,
                                color = if (isSelected) BottomNavContentSelected else BottomNavContentUnselected
                            )
                        },
                    )
                }
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