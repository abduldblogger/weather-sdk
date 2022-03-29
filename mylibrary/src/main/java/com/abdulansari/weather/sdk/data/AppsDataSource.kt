package com.abdulansari.weather.sdk.data

import com.abdulansari.weather.sdk.data.model.weather.WeatherResponse
import com.abdulansari.weather.sdk.data.network.ApiService
import com.abdulansari.weather.sdk.data.network.ApiServiceProvider
import io.reactivex.Single

internal class AppsDataSource : DataSource {
    private val apiService: ApiService =
        ApiServiceProvider().provideApticaApiservice()

    override fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: String,
        apiKey: String
    ): Single<WeatherResponse> {
        return apiService.getCurrentWeather(latitude, longitude, tempUnit, apiKey)
    }
}