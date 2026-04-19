package com.akshaym.weatherappcompose.domain.model


data class SaveLocationModel(
    val locationKey: String,
    val cityName: String,
    val countryName: String,
    val weatherFeel: String,
    val lastUpdatedTime: String,
    val iconType: String,
    val temperature: String
)