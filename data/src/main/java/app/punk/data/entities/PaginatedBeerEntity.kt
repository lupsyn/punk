package app.punk.data.entities

import androidx.room.*
import app.punk.data.PaginatedEntry

@Entity(
    tableName = "paginated_beers",
    indices = [
        Index(value = ["externalApiId"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = Beer::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("externalApiId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PaginatedBeerEntity(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    @ColumnInfo(name = "externalApiId") override val externalApiId: Int,
    @ColumnInfo(name = "page") override val page: Int
) : PaginatedEntry