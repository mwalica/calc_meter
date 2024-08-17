package ch.walica.calc_meter

import android.app.Application
import ch.walica.calc_meter.common.AppModule
import ch.walica.calc_meter.common.AppModuleImpl

class MyApp : Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}