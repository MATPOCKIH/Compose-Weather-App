package aronskiy.anton.compose.weatherapp.data.models
import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherModel (
    val city: String,
    val time: String,
    val currentTemp: String,
    val condition: String,
    val conditionIcon: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: String
) {
    companion object {
        fun getEmpty() : WeatherModel = WeatherModel(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    }

    fun getMinMax() : String {
        return try {
            "${minTemp.toFloat().toInt()}°C/${maxTemp.toFloat().toInt()}°C"
        } catch (error: Throwable) {
            return "$minTemp°C/$maxTemp°C"
        }
    }

    fun formattedCurrentTemp() : String {
        return try {
            currentTemp.toFloat().toInt().toString() + "°C"
        } catch (error: Throwable) {
            currentTemp
        }
    }
}

data class ForecastDto(
    val current: CurrentForecast
)

data class CurrentForecast(
    @JsonProperty("last_updated_epoch")
    val lastUpdatedEpoch: Long,
    @JsonProperty("last_updated")
    val lastUpdated: String,
    @JsonProperty("temp_c")
    val tempC: Double,
    @JsonProperty("temp_f")
    val tempF: Double,
    @JsonProperty("is_day")
    val isDay: Long,
    val condition: Condition,
    @JsonProperty("wind_mph")
    val windMph: Double,
    @JsonProperty("wind_kph")
    val windKph: Double,
    @JsonProperty("wind_degree")
    val windDegree: Long,
    @JsonProperty("wind_dir")
    val windDir: String,
    @JsonProperty("pressure_mb")
    val pressureMb: Double,
    @JsonProperty("pressure_in")
    val pressureIn: Double,
    @JsonProperty("precip_mm")
    val precipMm: Double,
    @JsonProperty("precip_in")
    val precipIn: Double,
    val humidity: Long,
    val cloud: Long,
    @JsonProperty("feelslike_c")
    val feelslikeC: Double,
    @JsonProperty("feelslike_f")
    val feelslikeF: Double,
    @JsonProperty("vis_km")
    val visKm: Double,
    @JsonProperty("vis_miles")
    val visMiles: Double,
    val uv: Double,
    @JsonProperty("gust_mph")
    val gustMph: Double,
    @JsonProperty("gust_kph")
    val gustKph: Double,
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Long,
)
