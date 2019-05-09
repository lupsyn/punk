package app.punk.data.mappers

import app.punk.data.entities.Beer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PunkBeerToBeer @Inject constructor() : Mapper<app.punk.datasources.entities.PunkBeer, Beer> {
    override fun map(from: app.punk.datasources.entities.PunkBeer) = Beer(
        externalApiId = from.id,
        name = from.name,
        tagline = from.tagline,
        image_url = from.tagline
    )
}