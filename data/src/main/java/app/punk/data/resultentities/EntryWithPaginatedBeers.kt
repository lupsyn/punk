package app.punk.data.resultentities

import androidx.room.Embedded
import androidx.room.Relation
import app.punk.data.entities.Beer
import app.punk.data.entities.PaginatedBeerEntity
import java.util.*

class EntryWithPaginatedBeers : EntryWithBeers<PaginatedBeerEntity> {
    @Embedded
    override var entry: PaginatedBeerEntity? = null
    @Relation(parentColumn = "externalApiId", entityColumn = "id")

    override var relations: List<Beer> = emptyList()


    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        other is EntryWithPaginatedBeers -> entry == other.entry && relations == other.relations
        else -> false
    }


    override fun hashCode(): Int = Objects.hash(entry, relations)
}