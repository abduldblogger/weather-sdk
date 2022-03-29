package com.abdulansari.weather.sdk

import android.util.Log
import androidx.annotation.NonNull
import com.abdulansari.weather.sdk.data.AppsDataSource
import com.abdulansari.weather.sdk.data.model.weather.WeatherResponse
import com.abdulansari.weather.sdk.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/** entry point for sdk only methods of this classes are accessible outside the library or package*/
class WeatherSDK private constructor(private val apiKey: String) {
    private var listener: WeatherDataListener? = null
    private var appDateSource: AppsDataSource = AppsDataSource()

    /** returns the current weather based on the location passed into the latitude and longitude
     * @param latitude latitude of the location for which the weather report is to be fetched
     * @param longitude longitude of the location for which the weather report is to be fetched
     * @param tempUnit temperature unit in which the temp to be returned like in fahrenheit or celsius
     * @param listener listener or callback when the response is received from the server
     *
     * */
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

    /** callback for weather data response error*/
    interface ErrorListener {
        fun onErrorFetchingData(error: Throwable)
    }

    /** callback for weather data response*/
    interface WeatherDataListener : ErrorListener {
        fun onWeatherResponse(response: WeatherResponse)
    }

    /**temp units to be used when getting weather data*/
    enum class TempUnit {
        CELSIUS,
        FAHRENHEIT
    }
}