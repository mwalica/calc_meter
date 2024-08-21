package ch.walica.calc_meter.domain.usecase

import ch.walica.calc_meter.MyApp
import ch.walica.calc_meter.domain.prefsmanager.PrefsManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPrefsUseCase @Inject constructor(private val prefsManager: PrefsManager) {

    operator fun invoke(): Flow<Int> {
        return prefsManager.getPrefs()
    }
}