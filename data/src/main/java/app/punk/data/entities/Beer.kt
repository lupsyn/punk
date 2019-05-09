package app.punk.data.entities

import androidx.room.*
import app.punk.data.ExternalEntry


@Entity(
    tableName = "beers"
//    ,
//    indices = [
//        Index(value = ["externalApiId"], unique = true)
//    ]
)
data class Beer(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Int = 0,
    @ColumnInfo(name = "externalApiId") override val externalApiId: Int,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "tagline") val tagline: String? = null,
    @ColumnInfo(name = "image_url") val image_url: String? = null
) : BeerEntity, ExternalEntry

