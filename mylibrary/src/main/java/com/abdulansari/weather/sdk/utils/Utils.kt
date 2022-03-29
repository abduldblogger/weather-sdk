package com.abdulansari.weather.sdk.utils

import com.abdulansari.weather.sdk.WeatherSDK

object Utils {
    fun getConvertedUnit(tempUnit: WeatherSDK.TempUnit): String {
        return when (tempUnit.name) {
            WeatherSDK.TempUnit.CELSIUS.name -> {
                "metric"
            }
            WeatherSDK.TempUnit.FAHRENHEIT.name -> {
                "imperial"
            }
            else -> {
                "standard"
            }
        }
    }

}