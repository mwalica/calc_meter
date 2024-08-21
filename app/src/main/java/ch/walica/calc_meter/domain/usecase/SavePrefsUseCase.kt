package ch.walica.calc_meter.domain.usecase

import ch.walica.calc_meter.MyApp
import ch.walica.calc_meter.domain.prefsmanager.PrefsManager
import javax.inject.Inject

class SavePrefsUseCase @Inject constructor(private val prefsManager: PrefsManager) {

    suspend operator fun invoke(startReading: Int) {
        prefsManager.savePrefs(startReading)
    }
}