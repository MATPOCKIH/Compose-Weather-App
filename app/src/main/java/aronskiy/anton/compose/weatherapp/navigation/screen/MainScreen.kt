package aronskiy.anton.compose.weatherapp.navigation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import aronskiy.anton.compose.weatherapp.navigation.NavGraph
import aronskiy.anton.compose.weatherapp.navigation.ScreenBottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            ScreenBottomNavigation(navController = navController)
        }
    ) { paddings ->
        Box(
            modifier = Modifier.padding(paddings).background(color = Color.Green)
        ){
            //NavGraph(navController = navController)
        }
    }
}