package ch.walica.calc_meter.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.walica.calc_meter.R

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel = hiltViewModel()) {

    val settingsState = settingsViewModel.state.collectAsState(SettingsState()).value
    val context = LocalContext.current

    LaunchedEffect(key1 = settingsState.error) {
        settingsState.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                label = { Text(text = "start value") },
                value = settingsState.enteredNumber,
                onValueChange = {
                    settingsViewModel.onEvent(SettingsEvent.EnteredNumberChange(it))
                })

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                settingsViewModel.onEvent(SettingsEvent.SaveStartReading)
            }) {
                Text(text = stringResource(R.string.btn_save))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                settingsViewModel.onEvent(SettingsEvent.ClearStartReading)
            }) {
                Text(text = stringResource(R.string.reset))
            }
        }


        settingsState.startReading?.let {
            Text(text = buildAnnotatedString {
                append(text = "Set start value: ")
                withStyle(
                    SpanStyle(
                        color = Color.Red
                    )
                ) {
                    append(text = it.toString())
                }
            })
        }

    }
}