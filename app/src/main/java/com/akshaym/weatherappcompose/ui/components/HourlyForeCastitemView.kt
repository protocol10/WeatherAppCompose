package com.akshaym.weatherappcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudCircle
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.Shower
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HourlyForeCastItemView(
    icon: Int,
    time: String,
    temperature: String,
    unit: String,
    modifier: Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = time,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Icon(
            imageVector = weatherIconRes(icon),
            contentDescription = null,
            tint = Color.Yellow,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = temperature,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

fun weatherIconRes(icon: Int) = when (icon) {
    1, 2, 3 -> Icons.Filled.WbSunny
    4, 5, 6 -> Icons.Filled.WbCloudy
    7, 8 -> Icons.Filled.CloudCircle
    12, 13, 14 -> Icons.Default.Shower
    else -> Icons.Default.Light
}