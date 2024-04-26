package aronskiy.anton.compose.weatherapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aronskiy.anton.compose.weatherapp.R
import aronskiy.anton.compose.weatherapp.data.models.WeatherModel
import aronskiy.anton.compose.weatherapp.ui.theme.BlueLight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun MainScreen(
    items: MutableState<List<WeatherModel>>,
    currentDayState: MutableState<WeatherModel>,
    onSearchClicked: (() -> Unit)? = null,
    onSyncClicked: (() -> Unit)? = null
) {
    Image(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.4f),
        painter = painterResource(id = R.drawable.wether_bg),
        contentScale = ContentScale.FillBounds,
        contentDescription = "weather",
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        MainWeatherCard(
            model = currentDayState.value,
            onSearchClicked = onSearchClicked,
            onSyncClicked = onSyncClicked
        )
        TableLayout(Modifier.padding(top = 8.dp), items, currentDayState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TableLayout(
    modifier: Modifier,
    items: MutableState<List<WeatherModel>>,
    currentDayState: MutableState<WeatherModel>
) {
    val tabs = listOf("Hours", "Days")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = Color.White
                )
            },
            containerColor = BlueLight
        ) {
            tabs.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = text.uppercase(),
                            style = TextStyle(fontSize = 18.sp, color = Color.White)
                        )
                    })
            }
        }

        HorizontalPager(
            count = tabs.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            val list = when (index) {
                0 -> getWeatherByHours(currentDayState.value.hours)
                else -> items.value
            }
            MainList(list) {
                currentDayState.value = it
            }
        }
    }
}

private fun getWeatherByHours(hours: String): List<WeatherModel> {
    if (hours.isEmpty()) return emptyList()
    val list = ArrayList<WeatherModel>()
    val hoursArray = JSONArray(hours)
    for (i in 0 until hoursArray.length()) {
        val item = hoursArray[i] as JSONObject
        list.add(
            WeatherModel(
                city = "",
                time = item.getString("time"),
                currentTemp = item.getString("temp_c"),
                condition = item.getJSONObject("condition").getString("text"),
                conditionIcon = item.getJSONObject("condition").getString("icon"),
                maxTemp = "",
                minTemp = "",
                hours = ""
            )
        )
    }
    return list
}