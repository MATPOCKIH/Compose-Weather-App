package aronskiy.anton.compose.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import aronskiy.anton.compose.weatherapp.ui.theme.WeatherAppTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class ScannerActivity : ComponentActivity() {

    private val scanLauncher = registerForActivityResult(ScanContract()){ result ->
        if(result.contents.isNullOrEmpty()){

        } else {
            Toast.makeText(this, "Scan data: ${result.contents}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Button(onClick = {
                        scan()
                    }) {
                        Text("Open scanner")
                    }
                }
            }
        }
    }

    private fun scan(){
        scanLauncher.launch(ScanOptions())
    }
}