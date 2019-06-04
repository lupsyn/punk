package app.punk.data.repositories.beer

import androidx.paging.DataSource
import app.punk.data.entities.Success
import app.punk.data.resultentities.EntryWithPaginatedBeers
import app.punk.extensions.parallelForEach
import io.reactivex.Flowable

class PaginatedBeerRepository constructor(
    private val paginatedStore: PaginatedBeerStore,
    private val punkBeerDataSource: PunkBeerDataSource,
    private val beerRepository: BeerRepositoryImpl
) {

    fun observeForPaging(): DataSource.Factory<Int, EntryWithPaginatedBeers> = paginatedStore.observeForPaging()

    fun observeForFlowable(): Flowable<List<EntryWithPaginatedBeers>> = paginatedStore.observeForFlowable(10, 0)

    suspend fun loadNextPage() {
        val lastPage = paginatedStore.getLastPage()
        if (lastPage != null) updatePaginatedBeers(lastPage + 1, false) else refresh()
    }

    suspend fun refresh() {
        updatePaginatedBeers(0, true)
    }

    private suspend fun updatePaginatedBeers(page: Int, resetOnSave: Boolean) {
        val response = punkBeerDataSource.getBeers(page, 20)
        when (response) {
            is Success -> {
                response.data.map { (show, entry) ->
                    // Grab the show id if it exists, or save the show and use it's generated ID
                    val beerId = beerRepository.getIdOrSavePlaceholder(show)
                    // Make a copy of the entry with the id
                    entry.copy(id = beerId, page = page)
                }.also { entries ->
                    if (resetOnSave) {
                        paginatedStore.deleteAll()
                    }
                    // Save the paginated beers
                    paginatedStore.savePaginatedBeers(page, entries)
                    // Now update all of the related shows if needed
                    entries.parallelForEach { entry ->
                        if (beerRepository.needsUpdate(entry.externalApiId)) {
                            beerRepository.updateBeer(entry.externalApiId)
                        }
                    }
                }
            }
        }
    }
}