package app.punk.appinitializer

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}