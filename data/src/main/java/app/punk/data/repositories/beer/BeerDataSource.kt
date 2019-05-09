package app.punk.data.repositories.beer

import app.punk.data.entities.Beer
import app.punk.data.entities.PaginatedBeerEntity
import app.punk.data.entities.Result

interface BeerDataSource {
    suspend fun getBeers(pageId: Int, elementPerPage: Int): Result<List<Pair<Beer, PaginatedBeerEntity>>>
    suspend fun getBeerDetail(beerId: Int): Result<Beer>
    suspend fun getRandomBeer(): Result<Beer>
}