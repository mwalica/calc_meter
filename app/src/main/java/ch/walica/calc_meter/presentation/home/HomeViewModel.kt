package ch.walica.calc_meter.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.walica.calc_meter.domain.usecase.GetPrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getPrefsUseCase: GetPrefsUseCase) :
    ViewModel() {

    private val _startReading = getPrefsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    private val _state = MutableStateFlow(HomeState())

    val state = combine(_state, _startReading) { stateArg, startReadingArg ->
        stateArg.copy(
            startReading = startReadingArg
        )
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.EnteredNumberChange -> {
                _state.update {
                    it.copy(
                        enteredNumber = event.enteredText
                    )
                }
            }

            is HomeEvent.CalcIncrease -> {
                val result = _state.value.enteredNumber.toInt() - _startReading.value
                Log.d("my_log", "Result: $result")
                _state.update {
                    it.copy(
                        result = result
                    )
                }
            }
        }
    }

}

data class HomeState(
    val enteredNumber: String = "",
    val startReading: Int = 0,
    val result: Int? = 0
)

sealed interface HomeEvent {
    data class EnteredNumberChange(val enteredText: String) : HomeEvent
    data object CalcIncrease : HomeEvent
}