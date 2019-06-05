package app.punk.inject

import android.content.Context
import app.punk.BuildConfig
import app.punk.PunkApplication
import app.punk.util.AppCoroutineDispatchers
import app.punk.util.AppSchedulers
import app.punk.utils.PunkLogger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.rx2.asCoroutineDispatcher
import okhttp3.Cache
import java.io.File

object AppModule {

    val applicationContext: Context = PunkApplication.app

    val coroutineDispatchers by lazy {
        AppCoroutineDispatchers(
            io = rxSchedulers.io.asCoroutineDispatcher(),
            computation = rxSchedulers.computation.asCoroutineDispatcher(),
            main = Dispatchers.Main
        )
    }

    val cache: Cache = Cache(File(applicationContext.cacheDir, "app_module_cache"), 10 * 1024 * 1024)

    val compositeDisposable = CompositeDisposable()

    val rxSchedulers: AppSchedulers = AppSchedulers(
        io = Schedulers.io(),
        computation = Schedulers.computation(),
        main = AndroidSchedulers.mainThread()
    )

    val logger by lazy { PunkLogger() }

    fun initTimber() = logger.setup(BuildConfig.DEBUG)
}
