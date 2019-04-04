package app.punk.inject

import android.content.Context
import app.punk.PunkApplication
import app.punk.util.AppCoroutineDispatchers
import app.punk.util.AppRxSchedulers
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
    fun provideCoroutineDispatchers(schedulers: AppRxSchedulers) = AppCoroutineDispatchers(
        io = schedulers.io.asCoroutineDispatcher(),
        computation = schedulers.computation.asCoroutineDispatcher(),
        main = Dispatchers.Main
    )

//    @Named("app")
//    @Provides
//    @Singleton
//    fun provideAppPreferences(application: Pun): SharedPreferences {
//        return PreferenceManager.getDefaultSharedPreferences(application)
//    }

    @Provides
    @Singleton
    @Named("cache")
    fun provideCacheDir(application: PunkApplication): File = application.cacheDir

//    @Provides
//    @Named("tmdb-api")
//    fun provideTmdbApiKey(): String = BuildConfig.TMDB_API_KEY
//
//    @Provides
//    @Named("trakt-client-id")
//    fun provideTraktClientId(): String = BuildConfig.TRAKT_CLIENT_ID
//
//    @Provides
//    @Named("trakt-client-secret")
//    fun provideTraktClientSecret(): String = BuildConfig.TRAKT_CLIENT_SECRET

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()


}
