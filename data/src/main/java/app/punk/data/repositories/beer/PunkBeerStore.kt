package app.punk.data.repositories.beer

import androidx.paging.DataSource
import app.punk.data.DatabaseTransactionRunner
import app.punk.data.daos.PaginatedBeerDao
import app.punk.data.entities.PaginatedBeerEntity
import app.punk.data.resultentities.EntryWithPaginatedBeers
import io.reactivex.Flowable

class PaginatedBeerStore constructor(
    private val transactionRunner: DatabaseTransactionRunner,
    private val paginatedBeerDao: PaginatedBeerDao
) {
    fun observeForFlowable(count: Int, offset: Int): Flowable<List<EntryWithPaginatedBeers>> {
        return paginatedBeerDao.entriesFlowable(count, offset)
    }

    fun observeForPaging(): DataSource.Factory<Int, EntryWithPaginatedBeers> {
        return paginatedBeerDao.entriesDataSource()
    }

    fun savePaginatedBeers(page: Int, entries: List<PaginatedBeerEntity>) = transactionRunner {
        paginatedBeerDao.deletePage(page)
        paginatedBeerDao.insertAll(entries)
    }

    fun deleteAll() = paginatedBeerDao.deleteAll()

    fun getLastPage(): Int? = paginatedBeerDao.getLastPage()
}