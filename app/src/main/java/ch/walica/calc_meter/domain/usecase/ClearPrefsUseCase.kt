package ch.walica.calc_meter.domain.usecase

import ch.walica.calc_meter.MyApp
import ch.walica.calc_meter.domain.prefsmanager.PrefsManager
import javax.inject.Inject

class ClearPrefsUseCase @Inject constructor(private val prefsManager: PrefsManager) {

    suspend operator fun invoke() {
        prefsManager.clearPrefs()
    }
}