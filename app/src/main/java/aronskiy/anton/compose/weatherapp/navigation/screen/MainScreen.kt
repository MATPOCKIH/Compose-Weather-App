package aronskiy.anton.compose.weatherapp.navigation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import aronskiy.anton.compose.weatherapp.R
import aronskiy.anton.compose.weatherapp.navigation.NavGraph
import aronskiy.anton.compose.weatherapp.navigation.ScreenBottomNavigation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Drawer(drawerState) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = Color.Gray,
                        contentColor = Color.White,
                        actionColor = Color.Red,
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            },
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(40.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            drawerState.open()
                                        }
                                    },
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "logo",
                                contentScale = ContentScale.Fit
                            )
                            Text(text = "0 / 0")
                        }
                    }, actions = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Hello",
                                    actionLabel = "Undone"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }) {
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
            ) {
                NavGraph(navController = navController, paddings)
            }
        }
    }

}