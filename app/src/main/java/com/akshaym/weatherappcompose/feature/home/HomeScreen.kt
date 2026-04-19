package com.akshaym.weatherappcompose.feature.home

import android.Manifest
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.akshaym.weatherappcompose.R
import com.akshaym.weatherappcompose.domain.model.WeatherSection
import com.akshaym.weatherappcompose.ui.components.CurrentLocationView
import com.akshaym.weatherappcompose.ui.components.HourlyForeCastItemView
import com.akshaym.weatherappcompose.ui.components.MetricCard
import com.akshaym.weatherappcompose.ui.permissions.PermissionHandler
import com.akshaym.weatherappcompose.ui.extensions.getIcon
import com.akshaym.weatherappcompose.ui.theme.WeatherCardBg
import com.akshaym.weatherappcompose.ui.theme.WeatherGradientBottom
import com.akshaym.weatherappcompose.ui.theme.WeatherGradientTop
import timber.log.Timber

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isError by viewModel.error.collectAsStateWithLifecycle()
    val uiList by viewModel.uiList.collectAsStateWithLifecycle()

    PermissionHandler(
        context = navController.context,
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        rationalMessage = "We need location to show your local weather data accurately.",
        onAllGranted = { viewModel.getCurrentLocation() }) { hasPermission, requestPermission ->

        LaunchedEffect(hasPermission) {
            if (hasPermission) {
                viewModel.getCurrentLocation()
            }
        }
        val backgroundBrush = Brush.verticalGradient(
            colors = listOf(
                WeatherGradientTop, WeatherGradientBottom
            )
        )
        if (hasPermission) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = backgroundBrush)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Text(
                                "Loading....",
                                modifier = Modifier.padding(top = 16.dp),
                                color = Color.White
                            )
                        }
                    }
                } else if (isError != null) {
                    Text(text = isError ?: "Unknown error", color = Color.White)
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = uiList) { section ->
                            when (section) {
                                is WeatherSection.Header -> {
                                    Column() {
                                        CurrentLocationView(section.cityName, section.countryName)
                                        Image(
                                            modifier = Modifier
                                                .padding(top = 24.dp, start = 16.dp)
                                                .width(120.dp)
                                                .height(120.dp),
                                            painter = painterResource(R.drawable.ic_rainy),
                                            contentDescription = "",
                                            colorFilter = ColorFilter.tint(Color.Yellow)
                                        )
                                        section.temperature?.let {
                                            Timber.i("value are ${it.value} ${it.unit}")
                                            Text(
                                                text = "${it.value} ${it.unit}",
                                                modifier = Modifier
                                                    .padding(16.dp)
                                                    .align(Alignment.CenterHorizontally),
                                                color = Color.White
                                            )
                                        }
                                    }

                                }

                                is WeatherSection.WeatherMetrics -> {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        section.items.chunked(2).forEach { rowItems ->
                                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                                rowItems.forEach { item ->
                                                    MetricCard(
                                                        icon = item.metricType.getIcon(),
                                                        title = item.title,
                                                        value = item.value,
                                                        unit = item.unit,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                                if (rowItems.size == 1) {
                                                    Spacer(Modifier.weight(1f))
                                                }
                                            }
                                        }
                                    }
                                }

                                is WeatherSection.HourlyForeCast -> {
                                    Card(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .fillMaxWidth(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = WeatherCardBg
                                        )
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(16.dp)
                                        ) {
                                            Text(
                                                text = "Hourly Forecast",
                                                color = Color.White,
                                                style = MaterialTheme.typography.titleMedium
                                            )

                                            Spacer(modifier = Modifier.height(16.dp))

                                            LazyRow(
                                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                                            ) {
                                                items(section.weatherForeCast) { forecast ->
                                                    HourlyForeCastItemView(
                                                        icon = forecast.weatherIcon,
                                                        time = forecast.dateTime,
                                                        temperature = forecast.temperature.value.toString(),
                                                        unit = forecast.temperature.unit,
                                                        modifier = Modifier,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } else {
            PermissionRequestUI(onRequestClick = requestPermission)
        }
    }

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
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location Icon",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Enable Location",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "To show you the most accurate weather for your current city, we need to know where you are.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onRequestClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Allow Access",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "We only use your location to fetch local weather data. Your privacy is protected.",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}