package aronskiy.anton.compose.weatherapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import aronskiy.anton.compose.weatherapp.R
import aronskiy.anton.compose.weatherapp.data.models.WeatherModel
import aronskiy.anton.compose.weatherapp.ui.theme.BlueLight
import coil.compose.AsyncImage

@Composable
fun MainList(items: List<WeatherModel>, onItemClicked: ((WeatherModel) -> Unit)? = null) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(
            items
        ) { index, item ->
            ListItemWeatherCard(item, onItemClicked)
        }
    }
}

@Composable
fun MainWeatherCard(
    model: WeatherModel,
    onSearchClicked: (() -> Unit)? = null,
    onSyncClicked: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BlueLight),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = model.time,
                    style = TextStyle(fontSize = 15.sp, color = Color.White),
                )
                AsyncImage(
                    modifier = Modifier.size(36.dp),
                    model = model.conditionIcon,
                    contentDescription = null,
                )
            }
            Text(
                modifier = Modifier,
                text = model.city,
                style = TextStyle(fontSize = 24.sp, color = Color.White),
            )
            Text(
                modifier = Modifier,
                text = if (model.currentTemp.isNotEmpty()) {
                    model.formattedCurrentTemp()
                } else {
                    model.getMinMax()
                },
                style = TextStyle(fontSize = 65.sp, color = Color.White),
            )
            Text(
                modifier = Modifier,
                text = model.condition,
                style = TextStyle(fontSize = 16.sp, color = Color.White),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    onSearchClicked?.invoke()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search",
                        tint = Color.White
                    )
                }
                Text(
                    modifier = Modifier,
                    text = model.getMinMax(),
                    style = TextStyle(fontSize = 16.sp, color = Color.White),
                )
                IconButton(
                    onClick = {
                        onSyncClicked?.invoke()
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cloud_sync),
                        contentDescription = "sync",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ListItemWeatherCard(
    model: WeatherModel,
    onItemClicked: ((WeatherModel) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .clickable {
                if (model.hours.isNotEmpty()) {
                    onItemClicked?.invoke(model)
                }
            },
        colors = CardDefaults.cardColors(containerColor = BlueLight),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier,
                    text = model.time,
                    style = TextStyle(fontSize = 15.sp, color = Color.White),
                )
                Text(
                    modifier = Modifier,
                    text = model.condition,
                    style = TextStyle(fontSize = 15.sp, color = Color.White),
                )
            }
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                text = model.formattedCurrentTemp().ifEmpty { model.getMinMax() },
                style = TextStyle(fontSize = 25.sp, color = Color.White),
            )
            AsyncImage(
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 16.dp),
                model = "https:" + model.conditionIcon,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherCardPreview() {
    MainWeatherCard(
        WeatherModel(
            city = "Madrid",
            time = "20 Jun 2022 13:00",
            currentTemp = "10",
            condition = "Parthly cloudy",
            conditionIcon = "//cdn.weatherapi.com/weather/64x64/day/116.png",
            maxTemp = "28",
            minTemp = "13",
            hours = ""
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ListItemWeatherCardPreview() {
    ListItemWeatherCard(
        WeatherModel(
            city = "Madrid",
            time = "20 Jun 2022 13:00",
            currentTemp = "10",
            condition = "Parthly cloudy",
            conditionIcon = "//cdn.weatherapi.com/weather/64x64/day/116.png",
            maxTemp = "28",
            minTemp = "13",
            hours = ""
        )
    )
}
