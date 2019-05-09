package app.punk.data.daos

import app.punk.data.PaginatedEntry
import app.punk.data.resultentities.EntryWithBeers

interface PaginatedEntryDao<EC : PaginatedEntry, LI : EntryWithBeers<EC>> : EntryDao<EC, LI> {
    fun deletePage(page: Int)
    fun getLastPage(): Int?
}