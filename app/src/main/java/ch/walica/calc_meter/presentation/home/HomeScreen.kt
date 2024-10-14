package ch.walica.calc_meter.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val state = homeViewModel.state.collectAsState(initial = HomeState()).value
    val context = LocalContext.current
    val controller = LocalSoftwareKeyboardController.current

    val scanner = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let { it ->
            scanner.process(it, 0)
                .addOnSuccessListener { result ->
                    if (result.textBlocks.isNotEmpty()) {
                        homeViewModel.onEvent(HomeEvent.EnteredNumberChange(result.textBlocks[0].text))
                    } else {
                        Toast.makeText(context, "Unrecognized text", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        homeViewModel.onEvent(HomeEvent.ClearResult)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Card(
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 42.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth(0.9f),
                    value = state.enteredNumber,
                    onValueChange = { homeViewModel.onEvent(HomeEvent.EnteredNumberChange(it)) },
                    label = { Text(text = "Enter reading value") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            resultLauncher.launch(null)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.PhotoCamera,
                                contentDescription = "Take photo"
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )


                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        homeViewModel.onEvent(HomeEvent.CalcIncrease)
                        controller?.hide()
                    }) {
                    Text(text = "Calc",
                        modifier = Modifier.padding(vertical = 4.dp),
                        style = MaterialTheme.typography.titleMedium
                        )
                }

                Spacer(modifier = Modifier.height(32.dp))
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