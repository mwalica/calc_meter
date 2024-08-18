package ch.walica.calc_meter.domain.usecase

import ch.walica.calc_meter.MyApp
import ch.walica.calc_meter.domain.prefsmanager.PrefsManager

class ClearPrefsUseCase(private val prefsManager: PrefsManager = MyApp.appModule.prefsManager) {

    suspend operator fun invoke() {
        prefsManager.clearPrefs()
    }
}