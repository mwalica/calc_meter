package ch.walica.calc_meter.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val state = homeViewModel.state.collectAsState(initial = HomeState()).value

    Column {

        TextField(
            value = state.enteredNumber,
            onValueChange = { homeViewModel.onEvent(HomeEvent.EnteredNumberChange(it)) },
            label = { Text(text = "Enter reading value") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Button(onClick = { homeViewModel.onEvent(HomeEvent.CalcIncrease) }) {
            Text(text = "Calc")
        }

        Text(text = state.result.toString())

        state.startReading?.let {
            Text(text = it.toString())
        }
    }
}