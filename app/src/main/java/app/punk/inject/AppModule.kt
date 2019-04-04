package app.punk.inject

import android.content.Context
import app.punk.PunkApplication
import app.punk.util.AppCoroutineDispatchers
import app.punk.util.AppSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.rx2.asCoroutineDispatcher
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [AppModuleBinds::class])
class AppModule {
    @Provides
    fun provideContext(application: PunkApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideCoroutineDispatchers(schedulers: AppSchedulers) = AppCoroutineDispatchers(
        io = schedulers.io.asCoroutineDispatcher(),
        computation = schedulers.computation.asCoroutineDispatcher(),
        main = Dispatchers.Main
    )

    @Provides
    @Singleton
    @Named("cache")
    fun provideCacheDir(application: PunkApplication): File = application.cacheDir

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()


}
