package app.punk.data.repositories.beer

import app.punk.data.RetrofitRunner
import app.punk.data.entities.Beer
import app.punk.data.entities.PaginatedBeerEntity
import app.punk.data.entities.Result
import app.punk.data.mappers.IndexedMapper
import app.punk.data.mappers.PunkBeerToBeer
import app.punk.data.mappers.pairMapperOf
import app.punk.datasources.entities.PunkBeer
import app.punk.datasources.services.PunkBeerService
import app.punk.extensions.executeWithRetry

class PunkBeerDataSource  constructor(
    punkBeerMapper: PunkBeerToBeer,
    private val retrofitRunner: RetrofitRunner,
    private val punkBeerService: PunkBeerService
) : BeerDataSource {
    private val paginatedExternalBeerMapper = object : IndexedMapper<PunkBeer, PaginatedBeerEntity> {
        override fun map(index: Int, from: PunkBeer): PaginatedBeerEntity {
            return PaginatedBeerEntity(externalApiId = 0, page = 0)
        }
    }

    private val resultsMapper = pairMapperOf(punkBeerMapper, paginatedExternalBeerMapper)

    override suspend fun getBeers(pageId: Int, elementPerPage: Int): Result<List<Pair<Beer, PaginatedBeerEntity>>> {
        return retrofitRunner.executeForResponse(resultsMapper) {
            punkBeerService.getBeers(pageId + 1, elementPerPage).executeWithRetry()
        }
    }

    override suspend fun getBeerDetail(beerId: Int): Result<Beer> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getRandomBeer(): Result<Beer> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
