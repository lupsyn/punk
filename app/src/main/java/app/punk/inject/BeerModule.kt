import app.punk.inject.NetworkModule
import app.punk.data.RetrofitRunner
import app.punk.data.daos.EntityInserter
import app.punk.data.mappers.PunkBeerToBeer
import app.punk.data.repositories.beer.*
import app.punk.inject.DatabaseModule

object BeerModule {

    val paginatedBeerRepository by lazy {
        PaginatedBeerRepository(
            paginatedBeerStore,
            punkBeerDataSource,
            beerRepositoryImpl
        )
    }

    private val beerRepositoryImpl by lazy { BeerRepositoryImpl(beerStore) }

    private val paginatedBeerStore by lazy {
        PaginatedBeerStore(
            DatabaseModule.databaseTransactionRunner,
            DatabaseModule.paginatedBeerDao
        )
    }

    private val punkBeerDataSource by lazy {
        PunkBeerDataSource(
            punkBeerToBeerMapper,
            RetrofitRunner(),
            NetworkModule.punkBeerService
        )
    }

    private val punkBeerToBeerMapper by lazy { PunkBeerToBeer() }

    private val beerStore by lazy {
        BeerStore(
            EntityInserter(DatabaseModule.databaseTransactionRunner),
            DatabaseModule.beerDao,
            DatabaseModule.databaseTransactionRunner
        )
    }
}