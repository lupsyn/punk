package app.punk.data.resultentities

import app.punk.data.ExternalEntry
import app.punk.data.entities.Beer
import java.util.*

interface EntryWithBeers<ET : ExternalEntry> {
    var entry: ET?
    var relations: List<Beer>

    val beer: ExternalEntry
        get() {
            assert(relations.size == 1)
            return relations[0]
        }

    fun generateStableId(): Long {
        return Objects.hash(entry!!::class, beer.id).toLong()
    }
}