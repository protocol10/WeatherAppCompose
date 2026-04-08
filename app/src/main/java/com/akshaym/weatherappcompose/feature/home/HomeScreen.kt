package com.akshaym.weatherappcompose.feature.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.akshaym.weatherappcompose.R
import com.akshaym.weatherappcompose.ui.permissions.PermissionHandler
import timber.log.Timber

@SuppressLint("TimberArgCount")
@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.error.collectAsState()
    val location by viewModel.currentLocation.collectAsState()
    PermissionHandler(
        context = navController.context,
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        rationalMessage = "We need location to show your local weather data accurately.",
        onAllGranted = { viewModel.getCurrentLocation() }) { hasPermission, requestPermission ->

        if (hasPermission) {
            Timber.e("Has permission $hasPermission, $isError, $location, $isLoading")
            viewModel.getCurrentLocation()
            Column(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Text("Loading....", modifier = Modifier.padding(top = 16.dp))
                        }
                    }
                } else if (isError != null) {
                    Text(text = isError ?: "Unknown error", color = Color.Red)
                } else {
                    if (location != null) {
                        Timber.i("location " + location)
                        Text("Latitude: ${location?.latitude}")
                        Text("Longitude: ${location?.longitude}")
                        Text("Accuracy: ${location?.accuracy} meters")
                    }
                    CurrentLocationView()
                    Image(
                        modifier = Modifier
                            .padding(top = 24.dp, start = 16.dp)
                            .width(120.dp)
                            .height(120.dp),
                        painter = painterResource(R.drawable.ic_rainy),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.Yellow)
                    )
                    Text(
                        "72", modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

            }
        } else {
            // THE "PRE-REQUEST" UI (The one with the big button)
            PermissionRequestUI(onRequestClick = requestPermission)
        }
    }

}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(rememberNavController())
}

@Composable
fun PermissionRequestUI(
    onRequestClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1. Visual Anchor (Big Icon)
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location Icon",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 2. Headline
        Text(
            text = "Enable Location",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. The "Why" (Explanation)
        Text(
            text = "To show you the most accurate weather for your current city, we need to know where you are.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // 4. The Main Action Button
        Button(
            onClick = onRequestClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Allow Access", style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Privacy Assurance
        Text(
            text = "We only use your location to fetch local weather data. Your privacy is protected.",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )
    }
}