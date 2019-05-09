package app.punk.data.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import app.punk.data.entities.PaginatedBeerEntity
import app.punk.data.resultentities.EntryWithPaginatedBeers
import io.reactivex.Flowable

@Dao
abstract class PaginatedBeerDao : PaginatedEntryDao<PaginatedBeerEntity, EntryWithPaginatedBeers> {
    @Transaction
    @Query("SELECT * FROM paginated_beers ORDER BY page LIMIT :count OFFSET :offset")
    abstract override fun entriesFlowable(count: Int, offset: Int): Flowable<List<EntryWithPaginatedBeers>>

    @Transaction
    @Query("SELECT * FROM paginated_beers ORDER BY page")
    abstract override fun entriesDataSource(): DataSource.Factory<Int, EntryWithPaginatedBeers>

    @Query("DELETE FROM paginated_beers WHERE page = :page")
    abstract override fun deletePage(page: Int)

    @Query("DELETE FROM paginated_beers")
    abstract override fun deleteAll()

    @Query("SELECT MAX(page) from paginated_beers")
    abstract override fun getLastPage(): Int
}