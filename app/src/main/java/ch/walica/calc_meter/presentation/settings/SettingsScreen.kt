package ch.walica.calc_meter.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.walica.calc_meter.R

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel = hiltViewModel()) {

    val settingsState = settingsViewModel.state.collectAsState(SettingsState()).value

    Column {
        TextField(
            label = { Text(text = "enter start mater value") },
            value = settingsState.enteredNumber,
            onValueChange = {
                settingsViewModel.onEvent(SettingsEvent.EnteredNumberChange(it))
            })
        Button(onClick = {
            settingsViewModel.onEvent(SettingsEvent.SaveStartReading)
        }) {
            Text(text = stringResource(R.string.btn_save))
        }
        settingsState.startReading?.let {
            Text(text = it.toString())
        }

    }
}