package aronskiy.anton.compose.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import aronskiy.anton.compose.weatherapp.navigation.screen.Screen1
import aronskiy.anton.compose.weatherapp.navigation.screen.Screen2
import aronskiy.anton.compose.weatherapp.navigation.screen.Screen3
import aronskiy.anton.compose.weatherapp.navigation.screen.Screen4

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "screen_1") {
        composable("screen_1") {
            Screen1()
        }
        composable("screen_2") {
            Screen2()
        }
        composable("screen_3") {
            Screen3()
        }
        composable("screen_4") {
            Screen4()
        }
    }
}