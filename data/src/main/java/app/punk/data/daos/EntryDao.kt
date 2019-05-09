package app.punk.data.daos

import androidx.paging.DataSource
import app.punk.data.ExternalEntry
import app.punk.data.resultentities.EntryWithBeers
import io.reactivex.Flowable

/**
 * This interface represents a DAO which contains entities which are part of a single collective list.
 */
interface EntryDao<EC : ExternalEntry, LI : EntryWithBeers<EC>> : BeerEntityDao<EC> {
    fun entriesFlowable(count: Int, offset: Int): Flowable<List<LI>>
    fun entriesDataSource(): DataSource.Factory<Int, LI>
    fun deleteAll()
}