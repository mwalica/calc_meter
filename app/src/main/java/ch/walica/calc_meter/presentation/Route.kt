package ch.walica.calc_meter.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val routeName: String) {

    @Serializable
    data object Home : Route("home")

    @Serializable
    data object Settings : Route("settings")
}