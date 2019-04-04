package app.punk.appinitializer

import android.app.Application
import android.os.Looper
import javax.inject.Inject

class RxAndroidInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            AndroidSchedulers.from(Looper.getMainLooper(), true)
        }
    }
}