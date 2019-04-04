package app.punk.inject

import app.punk.PunkApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<PunkApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PunkApplication>()
}
