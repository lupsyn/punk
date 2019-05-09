package app.punk.data

import androidx.room.Database
import androidx.room.RoomDatabase
import app.punk.data.daos.BeerDao
import app.punk.data.daos.PaginatedBeerDao
import app.punk.data.entities.Beer
import app.punk.data.entities.PaginatedBeerEntity

@Database(
    entities = [
        Beer::class,
        PaginatedBeerEntity::class
    ],
    version = 1
)

abstract class PunkDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
    abstract fun paginatedBeerDao(): PaginatedBeerDao

}