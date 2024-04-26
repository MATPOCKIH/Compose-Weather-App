package aronskiy.anton.compose.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import aronskiy.anton.compose.weatherapp.data.models.WeatherModel
import aronskiy.anton.compose.weatherapp.screens.DialogSearch
import aronskiy.anton.compose.weatherapp.screens.MainScreen
import aronskiy.anton.compose.weatherapp.ui.theme.WeatherAppTheme
import com.android.volley.Request.Method
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                val daysList = remember {
                    mutableStateOf(emptyList<WeatherModel>())
                }
                val currentDay = remember {
                    mutableStateOf(WeatherModel.getEmpty())
                }
                val dialogState = remember{
                    mutableStateOf(false)
                }
                getData("London", this, daysList, currentDay)
                // A surface container using the 'background' color from the theme
                MainScreen(
                    items = daysList,
                    currentDayState = currentDay,
                    onSearchClicked = {
                       dialogState.value = true
                    },
                    onSyncClicked = {
                        getData("London", this, daysList, currentDay)
                    }
                )

                if(dialogState.value){
                    DialogSearch(dialogState){
                        getData(it, this, daysList, currentDay)
                    }
                }
            }
        }
    }
}


private fun getData(
    city: String,
    context: Context,
    daysListState: MutableState<List<WeatherModel>>,
    currentDayState : MutableState<WeatherModel>
) {
    val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
            WEATHER_API_KEY +
            "&q=$city&days=3&aqi=no&alerts=no"
    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        Method.GET,
        url,
        { response ->
            Log.d("Anton", response)
            val items = getWeatherByDays(response)
            daysListState.value = items
            currentDayState.value = items.firstOrNull() ?: WeatherModel.getEmpty()
        },
        { error ->
            Log.d("Anton", "Error: $error")
        }
    )
    queue.add(sRequest)
}

private fun getWeatherByDays(response : String) : List<WeatherModel> {
    if (response.isEmpty()) return emptyList()
    val list = ArrayList<WeatherModel>()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location").getString("name")
    val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        list.add(
            WeatherModel(
                city = city,
                time = item.getString("date"),
                currentTemp = "",
                condition = item.getJSONObject("day").getJSONObject("condition").getString("text"),
                conditionIcon = item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                maxTemp = item.getJSONObject("day").getString("maxtemp_c"),
                minTemp = item.getJSONObject("day").getString("mintemp_c"),
                hours = item.getJSONArray("hour").toString()
            )
        )
    }
    list[0] = list[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c"),
    )
    return list
}
