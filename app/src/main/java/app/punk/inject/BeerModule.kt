import app.punk.inject.NetworkModule
import app.punk.data.RetrofitRunner
import app.punk.data.daos.EntityInserter
import app.punk.data.mappers.PunkBeerToBeer
import app.punk.data.repositories.beer.*
import app.punk.inject.DatabaseModule

object BeerModule {


    fun providePaginatedBeerRepository()= PaginatedBeerRepository(
        providePaginatedBeerStore(),
        providePunkBeerDataSource(),
        provideBeerRepository()
        )

    fun provideBeerRepository() = BeerRepositoryImpl(provideBeerStore())

    fun providePaginatedBeerStore() = PaginatedBeerStore(
        DatabaseModule.provideDatabaseTransactionRunner(),
        DatabaseModule.providePaginatedBeerDao()
    )

    fun providePunkBeerDataSource() = PunkBeerDataSource(
        providePunkBeerToBeerMapper(),
        RetrofitRunner(),
        NetworkModule.providePunkBeerService()
    )

    private fun providePunkBeerToBeerMapper() = PunkBeerToBeer()

    private fun provideBeerStore() = BeerStore(
        EntityInserter(
            DatabaseModule.provideDatabaseTransactionRunner()
        ),
        DatabaseModule.provideBeerDao(),
        DatabaseModule.provideDatabaseTransactionRunner()
    )

}