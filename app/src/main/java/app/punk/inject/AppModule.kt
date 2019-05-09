package app.punk.inject

import android.content.Context
import app.punk.BuildConfig
import app.punk.PunkApplication
import app.punk.util.AppCoroutineDispatchers
import app.punk.util.AppSchedulers
import app.punk.util.Logger
import app.punk.utils.PunkLogger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.rx2.asCoroutineDispatcher
import okhttp3.Cache
import java.io.File

object AppModule {

    fun provideContext(application: PunkApplication): Context = application.applicationContext

    fun provideCoroutinesDispatchers() {
        val schedulers = provideRxSchedulers()
        AppCoroutineDispatchers(
            io = schedulers.io.asCoroutineDispatcher(),
            computation = schedulers.computation.asCoroutineDispatcher(),
            main = Dispatchers.Main
        )
    }

    fun provideCache(application: PunkApplication): Cache =
        Cache(File(application.cacheDir, "app_module_cache"), 10 * 1024 * 1024)

    fun provideCompositeDisposable() = CompositeDisposable()

    private fun provideRxSchedulers(): AppSchedulers = AppSchedulers(
        io = Schedulers.io(),
        computation = Schedulers.computation(),
        main = AndroidSchedulers.mainThread()
    )

    fun provideLogger(): Logger {
        return PunkLogger()
    }

    fun provideTimberInitializer() {
        val tiviLogger = PunkLogger()
        tiviLogger.setup(BuildConfig.DEBUG)
    }

}
