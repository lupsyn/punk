package app.punk.data.mappers

import app.punk.data.entities.Beer
import app.punk.datasources.entities.PunkBeer

class PunkBeerToBeer : Mapper<PunkBeer, Beer> {
    override fun map(from: PunkBeer) = Beer(
        externalApiId = from.id,
        name = from.name,
        tagline = from.tagline,
        image_url = from.tagline
    )
}