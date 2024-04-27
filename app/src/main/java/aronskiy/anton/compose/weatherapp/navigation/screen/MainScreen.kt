package aronskiy.anton.compose.weatherapp.navigation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import aronskiy.anton.compose.weatherapp.R
import aronskiy.anton.compose.weatherapp.navigation.NavGraph
import aronskiy.anton.compose.weatherapp.navigation.ScreenBottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            modifier = Modifier.height(40.dp),
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "logo",
                            contentScale = ContentScale.Fit
                        )
                        Text(text = "0 / 0")
                    }
                }, actions = {
                    IconButton(onClick = {}){
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "settings"
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            ScreenBottomNavigation(navController = navController)
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .padding(paddings)
        ){
            NavGraph(navController = navController, paddings)
        }
    }
}