package sdk.abdul.demo.weather_sdk_demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.abdul.feedify.weather_sdk_demo.R
import com.abdul.feedify.weather_sdk_demo.databinding.ActivityMainBinding
import com.abdulansari.weather.sdk.WeatherSDK
import com.abdulansari.weather.sdk.data.model.weather.WeatherResponse
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherSDK: WeatherSDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        getCurrentWeather()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun init() {

    }

    private fun getCurrentWeather() {
        val weatherSDK = WeatherSDK.getInstance(getString(R.string.key))
        weatherSDK.getCurrentWeather(26.8719, 80.8949,
            WeatherSDK.TempUnit.FAHRENHEIT,
            object : WeatherSDK.WeatherDataListener {
                override fun onWeatherResponse(response: WeatherResponse) {
                    Log.d(TAG, response.toString())
                }

                override fun onErrorFetchingData(error: Throwable) {
                    Log.d(TAG, error.message.toString())
                }
            })

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}