package app.punk.data.daos

import androidx.room.Dao
import androidx.room.Query
import app.punk.data.entities.Beer
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
abstract class BeerDao : BeerEntityDao<Beer> {
    @Query("SELECT * FROM beers WHERE externalApiId = :id")
    abstract fun getBeerWithExternalId(id: Int): Beer?

    @Query("SELECT * FROM beers WHERE id IN (:ids)")
    abstract fun getBeersWithIds(ids: List<Int>): Flowable<List<Beer>>

    @Query("SELECT * FROM beers WHERE externalApiId = :id")
    abstract fun getBeerWithExternalApiId(id: Int): Beer?

    @Query("SELECT * FROM beers WHERE id = :id")
    abstract fun getBeerWithIdFlowable(id: Int): Flowable<Beer>

    @Query("SELECT * FROM beers WHERE id = :id")
    abstract fun getBeerWithIdMaybe(id: Int): Maybe<Beer>

    @Query("SELECT * FROM beers WHERE id = :id")
    abstract fun getBeerWithId(id: Int): Beer?

    @Query("SELECT externalApiId FROM beers WHERE id = :id")
    abstract fun getExternalApiIdIdForBeerId(id: Int): Int?

    @Query("SELECT id FROM beers WHERE externalApiId = :traktId")
    abstract fun getIdForExternalApiId(traktId: Int): Int?
}