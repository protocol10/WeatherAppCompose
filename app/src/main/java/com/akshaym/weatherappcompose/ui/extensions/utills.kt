package com.akshaym.weatherappcompose.ui.extensions

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.akshaym.weatherappcompose.feature.home.domain.model.MetricType

// UI Layer code
@Composable
fun MetricType.getIcon(): ImageVector {
    return when (this) {
        MetricType.WIND -> Icons.Filled.Air
        MetricType.HUMIDITY -> Icons.Filled.HeatPump
        MetricType.VISIBILITY -> Icons.Filled.Visibility
        MetricType.PRESSURE -> Icons.Filled.Speed
        MetricType.UV -> Icons.Filled.WbSunny
        MetricType.FEELS_LIKE -> Icons.Filled.DeviceThermostat
    }
}