package com.abdulansari.weather.sdk

import android.util.Log
import androidx.annotation.NonNull
import com.abdulansari.weather.sdk.data.AppsDataSource
import com.abdulansari.weather.sdk.data.model.weather.WeatherResponse
import com.abdulansari.weather.sdk.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


//  default constructor private to avoid initialization
class WeatherSDK private constructor(private val apiKey: String) {
    private var listener: WeatherDataListener? = null
    private var appDateSource: AppsDataSource = AppsDataSource()

    fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: TempUnit,
        @NonNull listener: WeatherDataListener
    ) {
        this.listener = listener
        Log.d(TAG, "$latitude  $longitude  $apiKey")
        appDateSource.getCurrentWeather(latitude, longitude, convertTempUnit(tempUnit), apiKey)
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ onSuccessResponse -> onSuccess(onSuccessResponse) },
                { onErrorResponse -> handleError(onErrorResponse) })
    }

    private fun convertTempUnit(tempUnit: TempUnit): String {
        return Utils.getConvertedUnit(tempUnit)
    }

    private fun handleError(error: Throwable) {
        listener?.onErrorFetchingData(error)
    }

    private fun onSuccess(response: Any) {
        when (response) {
            is WeatherResponse -> {
                listener?.onWeatherResponse(response)
            }
        }
    }

    companion object {
        private const val TAG = "SDK"

        @Volatile
        private var INSTANCE: WeatherSDK? = null

        @JvmStatic  // adding to support calling from Java class
        fun getInstance(@NonNull apiKey: String): WeatherSDK {
            synchronized(WeatherSDK::class) {
                if (INSTANCE == null) {
                    INSTANCE = WeatherSDK(apiKey)
                }
                return INSTANCE!!
            }
        }
    }

    interface ErrorListener {
        fun onErrorFetchingData(error: Throwable)
    }

    interface WeatherDataListener : ErrorListener {
        fun onWeatherResponse(response: WeatherResponse)
    }

    enum class TempUnit {
        CELSIUS,
        FAHRENHEIT
    }
}