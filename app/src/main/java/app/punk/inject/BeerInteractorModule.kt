import app.punk.inject.AppModule
import app.punk.interactors.UpdateBeersInteractor

object BeerInteractorModule {

    val beerInteractor: UpdateBeersInteractor by lazy {
        UpdateBeersInteractor(
            AppModule.coroutineDispatchers,
            AppModule.rxSchedulers,
            BeerModule.paginatedBeerRepository
        )
    }
}