package com.akshaym.weatherappcompose.feature.home

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.akshaym.weatherappcompose.R

@Composable
fun HomeScreen(navController: NavHostController) {
    Column {
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

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(rememberNavController())
}