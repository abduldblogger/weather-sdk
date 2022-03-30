# Weather SDK (1.0)

Weather SDK uses APIs provided by [openweathermap](https://openweathermap.org/) which provides
weather related data like rain, snow extreme etc.

## Installation

Installation is very straightforward, you need to add download the library
from [github](https://github.com/abduldblogger/weather-sdk/tree/master/mylibrary) and paste in the
same app folder in which you want to integrate it, then follow the steps below:

Step 1: Follow to File > New Module
![](https://www.geeksforgeeks.org/how-to-add-a-library-project-to-android-studio/)

Click on “Import Existing Project“.
![](https://media.geeksforgeeks.org/wp-content/uploads/20210327124538/Zm7QO.png)

Step 2: Select the desired library and the desired module. Then click finish. Android Studio will
import the library into your project and will sync Gradle files.

Step 3: In the next step you need to add the imported module to your project’s dependencies.
Right-click on the app folder > Open Module settings

![](https://media.geeksforgeeks.org/wp-content/uploads/20210326232940/Screenshot414.png)

Step 4: Navigate to the dependencies tab > Click on the ‘+’ button -> click on Module Dependency.
The library module will be then added to the project’s dependencies.

![](https://media.geeksforgeeks.org/wp-content/uploads/20210326233244/Screenshot420.png)

it's done, your library is ready to be used!

## Usage

        val weatherSDK = WeatherSDK.getInstance(getString(R.string.key)) // use your key generated from https://openweathermap.org/

then to fetch current location's weather forecast details

        weatherSDK.getCurrentWeather(26.8719, 80.8949,
            WeatherSDK.TempUnit.CELSIUS,
            object : WeatherSDK.WeatherDataListener {
                override fun onWeatherResponse(response: WeatherResponse) {
                    Log.d(TAG, response.toString())
                }

                override fun onErrorFetchingData(error: Throwable) {
                    Log.d(TAG, error.message.toString())
                }
            })

use WeatherSDK.TempUnit.CELSIUS to get temperature in celsius or WeatherSDK.TempUnit.FAHRENHEIT to
get temperature in Fahrenheit

      
