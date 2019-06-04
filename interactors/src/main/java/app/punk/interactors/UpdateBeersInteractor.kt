package app.punk.interactors

import androidx.paging.DataSource
import app.punk.data.repositories.beer.PaginatedBeerRepository
import app.punk.data.resultentities.EntryWithPaginatedBeers
import app.punk.extensions.emptyFlowableList
import app.punk.util.AppCoroutineDispatchers
import app.punk.util.AppSchedulers
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineDispatcher

class UpdateBeersInteractor  constructor(
    dispatchers: AppCoroutineDispatchers,
    private val schedulers: AppSchedulers,
    private val paginatedBeerRepository: PaginatedBeerRepository
) : PagingInteractor<EntryWithPaginatedBeers>, SubjectInteractor<Unit, UpdateBeersInteractor.ExecuteParams, List<EntryWithPaginatedBeers>>() {
    override val dispatcher: CoroutineDispatcher = dispatchers.io

    init {
        // We don't have params, so lets set Unit to kick off the observable
        setParams(Unit)
    }

    override fun dataSourceFactory(): DataSource.Factory<Int, EntryWithPaginatedBeers> {
        return paginatedBeerRepository.observeForPaging()
    }

    override fun createObservable(params: Unit): Flowable<List<EntryWithPaginatedBeers>> {
        return paginatedBeerRepository.observeForFlowable()
            .startWith(emptyFlowableList())
            .subscribeOn(schedulers.io)
    }

    override suspend fun execute(params: Unit, executeParams: ExecuteParams) {
        when (executeParams.page) {
            Page.NEXT_PAGE -> paginatedBeerRepository.loadNextPage()
            Page.REFRESH -> paginatedBeerRepository.refresh()
        }
    }

    data class ExecuteParams(val page: Page)

    enum class Page {
        NEXT_PAGE, REFRESH
    }
}