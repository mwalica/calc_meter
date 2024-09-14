package ch.walica.calc_meter.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val state = homeViewModel.state.collectAsState(initial = HomeState()).value
    val context = LocalContext.current
    val controller = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        homeViewModel.onEvent(HomeEvent.ClearResult)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = state.enteredNumber,
            onValueChange = { homeViewModel.onEvent(HomeEvent.EnteredNumberChange(it)) },
            label = { Text(text = "Enter reading value") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(18.dp))
        Button(onClick = {
            homeViewModel.onEvent(HomeEvent.CalcIncrease)
            controller?.hide()
        }) {
            Text(text = "Calc")
        }
        Spacer(modifier = Modifier.height(18.dp))
        state.startReading.let {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Start value", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = it.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))

        state.result?.let {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Increase", style = MaterialTheme.typography.bodyLarge)
                Text(text = it.toString(), style = MaterialTheme.typography.displayLarge)
            }

        }



    }
}