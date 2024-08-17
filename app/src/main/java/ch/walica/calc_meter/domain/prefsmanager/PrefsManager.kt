package ch.walica.calc_meter.domain.prefsmanager

import kotlinx.coroutines.flow.Flow

interface PrefsManager {

    fun getPrefs(): Flow<Int>
    suspend fun savePrefs(startReading: Int)
    suspend fun clearPrefs()

}