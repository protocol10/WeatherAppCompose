package com.akshaym.weatherappcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.akshaym.weatherappcompose.ui.theme.WeatherCardBg

@Composable
fun MetricCard(icon: ImageVector, title: String, value: String, unit: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = WeatherCardBg
        ),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(start = 8.dp, top = 12.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = icon,
                    contentDescription = "",
                    tint = Color.White
                )
                Text(text = title, color = Color.White)
            }
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 16.dp, bottom = 12.dp),
                text = "$value $unit",
                color = Color.White,
            )
        }
    }
}