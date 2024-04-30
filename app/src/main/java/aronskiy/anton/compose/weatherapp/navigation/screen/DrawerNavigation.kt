package aronskiy.anton.compose.weatherapp.navigation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Blue)
        ) {
            Text("Header", fontSize = 20.sp)
        }
    }
}

@Composable
fun DrawerBody(
    scope: CoroutineScope,
    drawerState: DrawerState,
    items: List<DrawerItem>,
    selectedItem: MutableState<DrawerItem>
) {
    Spacer(modifier = Modifier.height(15.dp))
    items.forEach {
        NavigationDrawerItem(
            modifier = Modifier.padding(horizontal = 10.dp),
            label = {
                Text(it.title)
            },
            selected = selectedItem.value == it,
            icon = {
                Icon(
                    imageVector = it.imageVector,
                    contentDescription = it.title
                )
            },
            onClick = {
                scope.launch {
                    selectedItem.value = it
                    drawerState.close()
                }
            }
        )
    }
}

@Composable
fun Drawer(drawerState: DrawerState, content: @Composable () -> Unit) {
    val items = listOf(
        DrawerItem(
            Icons.Default.AccountCircle,
            "Account"
        ),
        DrawerItem(
            Icons.Default.Delete,
            "Delete"
        ),
        DrawerItem(
            Icons.Default.Build,
            "Build"
        )
    )

    val scope = rememberCoroutineScope()
    val selectedItem = remember {
        mutableStateOf(items[0])
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(scope, drawerState, items, selectedItem)
            }
        }) {
        content()
    }
}

data class DrawerItem(
    val imageVector: ImageVector,
    val title: String
)