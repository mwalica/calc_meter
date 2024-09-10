package ch.walica.calc_meter.presentation.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.walica.calc_meter.presentation.Route
import ch.walica.calc_meter.presentation.home.HomeScreen
import ch.walica.calc_meter.presentation.settings.SettingsScreen
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    val currentRoute = navController.currentBackStackEntryFlow.map { navBackStackEntry ->
        navBackStackEntry.destination.route?.substringAfterLast(".")
    }.collectAsState(initial = "Home").value

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(top = 0.dp),
                title = { Text(text = "Increase calculator ${currentRoute}") },
                actions = {
                    if (currentRoute == "Home") {
                        IconButton(onClick = {
                            navController.navigate(Route.Settings)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = "settings"
                            )
                        }
                    }

                },
                navigationIcon = {
                    if (currentRoute == "Settings") {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "arrow back"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        MainScreenContent(padding = padding, navController = navController)
    }
}

@Composable
fun MainScreenContent(padding: PaddingValues, navController: NavHostController) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding.calculateTopPadding() + 12.dp)
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.Home) {
            composable<Route.Home> {
                HomeScreen()
            }

            composable<Route.Settings> {
                SettingsScreen()
            }
        }
    }

}



