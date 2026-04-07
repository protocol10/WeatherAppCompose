package com.akshaym.weatherappcompose.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurrentLocationView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, top = 16.dp)
    ) {
        Icon(
            modifier = Modifier.padding(top = 8.dp),
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "",
            tint = Color.Red
        )

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = "San Franscio")
            Text(text = "United States", modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComoseView() {
    CurrentLocationView()
}