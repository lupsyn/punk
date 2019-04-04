package app.punk.inject

import android.app.Application
import app.punk.PunkApplication
import app.punk.appinitializer.AppInitializer
import app.punk.appinitializer.TimberInitializer
import app.punk.util.Logger
import app.punk.utils.PunkLogger
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {
    @Binds
    abstract fun provideApplication(bind: PunkApplication): Application

    @Singleton
    @Binds
    abstract fun provideLogger(bind: PunkLogger): Logger

    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(bind: TimberInitializer): AppInitializer
}