package app.punk.data

import app.punk.data.entities.BeerEntity


interface ExternalEntry : BeerEntity {
    val externalApiId: Int
}

interface PaginatedEntry : ExternalEntry {
    val page: Int
}



