import app.punk.data.repositories.beer.BeerDataSource
import app.punk.data.repositories.beer.BeerRepository
import app.punk.data.repositories.beer.BeerRepositoryImpl
import app.punk.data.repositories.beer.PunkBeerDataSource

abstract class BeerModule {

    abstract fun bind(source: BeerRepositoryImpl): BeerRepository

    abstract fun bind(source: PunkBeerDataSource): BeerDataSource
}