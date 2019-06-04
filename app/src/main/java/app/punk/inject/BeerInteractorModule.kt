import app.punk.inject.AppModule
import app.punk.interactors.UpdateBeersInteractor

object BeerInteractorModule {

    fun provideBeerInteractor() = UpdateBeersInteractor(
        AppModule.provideCoroutinesDispatchers(),
        AppModule.provideRxSchedulers(),
        BeerModule.providePaginatedBeerRepository()
    )
}