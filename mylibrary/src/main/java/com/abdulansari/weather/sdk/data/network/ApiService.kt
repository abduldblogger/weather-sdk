package com.abdulansari.weather.sdk.data.network

import com.abdulansari.weather.sdk.data.model.weather.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") tempUnit: String,
        @Query("appid") apiKey: String
    ): Single<WeatherResponse>
}