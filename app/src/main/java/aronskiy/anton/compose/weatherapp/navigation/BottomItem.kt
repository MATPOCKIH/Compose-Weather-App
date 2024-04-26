package aronskiy.anton.compose.weatherapp.navigation

import aronskiy.anton.compose.weatherapp.R

sealed class BottomItem (
    val title: String,
    val iconId: Int,
    val route: String
) {
    data object Screen1 : BottomItem("Screen1", R.drawable.b1, "screen_1")
    data object Screen2 : BottomItem("Screen2", R.drawable.b2, "screen_2")
    data object Screen3 : BottomItem("Screen3", R.drawable.b3, "screen_3")
    data object Screen4 : BottomItem("Screen4", R.drawable.b4, "screen_4")
}