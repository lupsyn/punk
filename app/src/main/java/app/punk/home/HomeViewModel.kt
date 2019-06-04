package app.punk.home

import app.punk.data.resultentities.EntryWithPaginatedBeers
import app.punk.inject.AppModule
import app.punk.interactors.UpdateBeersInteractor
import app.punk.interactors.launchInteractor
import app.punk.utils.EntryViewModel
import kotlinx.coroutines.coroutineScope

class HomeViewModel constructor(
     private val interactor: UpdateBeersInteractor
) : EntryViewModel<EntryWithPaginatedBeers>(
    AppModule.provideRxSchedulers(),
    AppModule.provideCoroutinesDispatchers(),
    interactor.dataSourceFactory(),
    AppModule.provideLogger()
) {
    override suspend fun callLoadMore() = coroutineScope {
        launchInteractor(interactor, UpdateBeersInteractor.ExecuteParams(UpdateBeersInteractor.Page.NEXT_PAGE)).join()
    }

    override suspend fun callRefresh() = coroutineScope {
        launchInteractor(interactor, UpdateBeersInteractor.ExecuteParams(UpdateBeersInteractor.Page.REFRESH)).join()
    }
}