package ch.walica.calc_meter.data.prefsmanager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import ch.walica.calc_meter.domain.prefsmanager.PrefsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val START_READING_KEY = intPreferencesKey("start_reading_value");

class PrefsManagerImpl(private val dataStore: DataStore<Preferences>) : PrefsManager {
    override fun getPrefs(): Flow<Int> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { prefs ->
            prefs[START_READING_KEY] ?: 0
        }
    }

    override suspend fun savePrefs(startReading: Int) {
        dataStore.edit { prefs ->
            prefs[START_READING_KEY] = startReading
        }
    }

    override suspend fun clearPrefs() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}