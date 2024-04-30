package aronskiy.anton.compose.weatherapp.navigation.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen1() {
    val searchText = remember {
        mutableStateOf("")
    }
    val activeState = remember {
        mutableStateOf(false)
    }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        query = searchText.value,
        onQueryChange = { text ->
            searchText.value = text
        },
        onSearch = {
            activeState.value = false
        },
        active = activeState.value,
        onActiveChange = {
            activeState.value = it
        },
        placeholder = {
            Text(text = "Search...")
        },
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(text = "Hello")
    }
    /*
        Text(
            modifier = Modifier
                .fillMaxSize(),
            text = "Screen 1",
            textAlign = TextAlign.Center
        )*/
}



@Composable
fun Screen2(onScanClicked: (() -> Unit)? = null) {

    val scanOptions = remember {
       ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
            setPrompt("Отсканируйте штрихкод")
            setBeepEnabled(false)
        }
    }

    val scanLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents.isNullOrEmpty()) {

        } else {
            //Toast.makeText(this, "Scan data: ${result.contents}", Toast.LENGTH_SHORT).show()
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (text, button, image) = createRefs()
        Button(
            onClick = { scanLauncher.launch(scanOptions) },
            modifier = Modifier.constrainAs(button) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        ) {
            Text(text = "Click me", color = Color.White)
        }
    }

}



@Composable
fun Screen3() {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Screen 3",
        textAlign = TextAlign.Center
    )
}

@Composable
fun Screen4() {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "Screen 4",
        textAlign = TextAlign.Center
    )
}
