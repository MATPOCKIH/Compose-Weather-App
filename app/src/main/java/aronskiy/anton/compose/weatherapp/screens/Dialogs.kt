package aronskiy.anton.compose.weatherapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun DialogSearch(dialogState: MutableState<Boolean>, onSubmit: ((String) -> Unit)? = null) {
    val dialogTextState = remember {
        mutableStateOf("")
    }
    AlertDialog(
        onDismissRequest = {
            dialogState.value = false
        },
        confirmButton = {
            TextButton(onClick = {
                onSubmit?.invoke(dialogTextState.value)
                dialogState.value = false
            }) {
                Text(text = "OK")
            }

        },
        dismissButton = {
            TextButton(onClick = { dialogState.value = false }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column() {
                Text(text = "Введите название города")
                TextField(value = dialogTextState.value, onValueChange = {
                    dialogTextState.value = it
                })
            }

        }
    )
}