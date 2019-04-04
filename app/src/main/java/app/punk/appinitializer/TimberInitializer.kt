package app.punk.appinitializer

import android.app.Application
import app.punk.BuildConfig
import app.punk.utils.PunkLogger
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val tiviLogger: PunkLogger
) : AppInitializer {
    override fun init(application: Application) = tiviLogger.setup(BuildConfig.DEBUG)
}