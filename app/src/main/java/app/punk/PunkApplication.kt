package app.punk

import android.app.Application
import app.punk.inject.AppModule

class PunkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppModule.provideTimberInitializer()
        app = this
    }

    companion object {
        lateinit var app: PunkApplication
    }


}