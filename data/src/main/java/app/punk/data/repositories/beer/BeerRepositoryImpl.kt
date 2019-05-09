package app.punk.data.repositories.beer

import app.punk.data.entities.Beer
import app.punk.util.AppCoroutineDispatchers
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeerRepositoryImpl @Inject constructor(
    private val beerStore: BeerStore
) : BeerRepository {
    //TODO : fix this as it's circular dependecy
    override fun getIdOrSavePlaceholder(show: Beer): Int {
        return beerStore.getIdOrSavePlaceholder(show).toInt()
    }

    override fun observeBeer(externalApiId: Int) = beerStore.observeBeer(externalApiId)

    override suspend fun getExternalBeer(externalApiId: Int): Beer {
        if (needsUpdate(externalApiId)) {
            updateBeer(externalApiId)
        }
        return beerStore.getBeer(externalApiId)!!
    }

    override fun needsUpdate(externalApiId: Int): Boolean {
        return true
//        beerStore.lastRequestBefore(showId, Period.ofDays(7))
    }

    override suspend fun updateBeer(externalApiId: Int) = coroutineScope {
        val localShow = beerStore.getBeer(externalApiId)?.let {
            beerStore.saveBeer(it)
        }
    }

}