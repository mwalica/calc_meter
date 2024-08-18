package ch.walica.calc_meter.domain.usecase

import ch.walica.calc_meter.MyApp
import ch.walica.calc_meter.domain.prefsmanager.PrefsManager
import kotlinx.coroutines.flow.Flow

class GetPrefsUseCase(private val prefsManager: PrefsManager = MyApp.appModule.prefsManager) {

    operator fun invoke(): Flow<Int> {
        return prefsManager.getPrefs()
    }
}