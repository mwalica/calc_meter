package ch.walica.calc_meter.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.walica.calc_meter.MyApp
import ch.walica.calc_meter.domain.prefsmanager.PrefsManager
import ch.walica.calc_meter.domain.usecase.ClearPrefsUseCase
import ch.walica.calc_meter.domain.usecase.GetPrefsUseCase
import ch.walica.calc_meter.domain.usecase.SavePrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getPrefsUseCase: GetPrefsUseCase,
    private val savePrefsUseCase: SavePrefsUseCase,
    private val clearPrefsUseCase: ClearPrefsUseCase
) : ViewModel() {

    private val _startReading = getPrefsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    private val _state = MutableStateFlow(SettingsState())

    val state = combine(_state, _startReading) { stateArg, startReadingArg ->
        stateArg.copy(
            startReading = startReadingArg
        )
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.EnteredNumberChange -> {
                _state.update {
                    it.copy(
                        enteredNumber = event.enteredText
                    )
                }
            }

            SettingsEvent.SaveStartReading -> {
                if (_state.value.enteredNumber.isBlank()) {
                    _state.update {
                        it.copy(
                            error = "Please enter value"
                        )
                    }
                    return
                }
                viewModelScope.launch {
                    savePrefsUseCase(startReading = _state.value.enteredNumber.toInt())
                }
                _state.update {
                    it.copy(
                        enteredNumber = "",
                        startReading = _state.value.startReading,
                        error = null
                    )
                }
            }

            SettingsEvent.ClearStartReading -> {
                viewModelScope.launch {
                    clearPrefsUseCase()
                }

                _state.update {
                    it.copy(
                        startReading = null
                    )
                }
            }
        }
    }

}

data class SettingsState(
    val enteredNumber: String = "",
    val startReading: Int? = null,
    val error: String? = null
)

sealed interface SettingsEvent {
    data object SaveStartReading : SettingsEvent
    data object ClearStartReading : SettingsEvent
    data class EnteredNumberChange(val enteredText: String) : SettingsEvent
}