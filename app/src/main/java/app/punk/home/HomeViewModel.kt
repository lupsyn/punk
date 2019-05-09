package app.punk.home

import app.punk.data.resultentities.EntryWithPaginatedBeers
import app.punk.interactors.UpdateBeersInteractor
import app.punk.interactors.launchInteractor
import app.punk.util.AppCoroutineDispatchers
import app.punk.util.AppSchedulers
import app.punk.util.Logger
import app.punk.utils.EntryViewModel
import kotlinx.coroutines.coroutineScope

class HomeViewModel constructor(
    schedulers: AppSchedulers,
    dispatchers: AppCoroutineDispatchers,
    private val interactor: UpdateBeersInteractor,
    logger: Logger
) : EntryViewModel<EntryWithPaginatedBeers>(
    schedulers,
    dispatchers,
    interactor.dataSourceFactory(),
    logger
) {
    override suspend fun callLoadMore() = coroutineScope {
        launchInteractor(interactor, UpdateBeersInteractor.ExecuteParams(UpdateBeersInteractor.Page.NEXT_PAGE)).join()
    }

    override suspend fun callRefresh() = coroutineScope {
        launchInteractor(interactor, UpdateBeersInteractor.ExecuteParams(UpdateBeersInteractor.Page.REFRESH)).join()
    }
}