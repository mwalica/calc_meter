package ch.walica.calc_meter.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import ch.walica.calc_meter.data.prefsmanager.PrefsManagerImpl
import ch.walica.calc_meter.domain.prefsmanager.PrefsManager

interface AppModule {
    val dataStore: DataStore<Preferences>
    val prefsManager: PrefsManager
}

class AppModuleImpl(private val context: Context) : AppModule {
    override val dataStore: DataStore<Preferences>
        get() = PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile("apps_prefs") }
        )
    override val prefsManager: PrefsManager
        get() = PrefsManagerImpl(dataStore)

}