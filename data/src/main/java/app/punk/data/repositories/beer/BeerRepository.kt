package app.punk.data.repositories.beer

import app.punk.data.entities.Beer
import io.reactivex.Flowable

interface BeerRepository {
    /**
     * Updates the show with the given id from all network sources, saves the result to the database
     */
    suspend fun getExternalBeer(externalApiId: Int): Beer

    /**
     * Updates the show with the given id from all network sources, saves the result to the database
     */
    suspend fun updateBeer(externalApiId: Int)

    fun needsUpdate(externalApiId: Int): Boolean

    fun observeBeer(externalApiId: Int): Flowable<Beer>

    fun getIdOrSavePlaceholder(show: Beer): Int
}