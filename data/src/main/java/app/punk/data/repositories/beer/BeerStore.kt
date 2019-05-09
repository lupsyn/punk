package app.punk.data.repositories.beer

import app.punk.data.DatabaseTransactionRunner
import app.punk.data.daos.BeerDao
import app.punk.data.daos.EntityInserter
import app.punk.data.entities.Beer
import io.reactivex.Flowable

class BeerStore constructor(
    private val entityInserter: EntityInserter,
    private val beerDao: BeerDao,
    private val transactionRunner: DatabaseTransactionRunner
) {
    fun getBeer(beerId: Int) = beerDao.getBeerWithId(beerId)

    fun observeBeer(beerId: Int): Flowable<Beer> = beerDao.getBeerWithIdFlowable(beerId)

    fun saveBeer(beer: Beer) = entityInserter.insertOrUpdate(beerDao, beer)

    //    fun lastRequestBefore(showId: Long, threshold: TemporalAmount): Boolean {
//        return lastRequestDao.isRequestBefore(Request.SHOW_DETAILS, showId, threshold)
//    }
//
//    fun updateLastRequest(showId: Long) = lastRequestDao.updateLastRequest(Request.SHOW_DETAILS, showId)
//
//    /**
//     * Gets the ID for the show with the given trakt Id. If the trakt Id does not exist in the
//     * database, it is inserted and the generated ID is returned.
//     */
    fun getIdOrSavePlaceholder(beer: Beer): Long = transactionRunner {
        beer.externalApiId.let(beerDao::getIdForExternalApiId)?.toLong() ?: beerDao.insert(beer)
    }
}