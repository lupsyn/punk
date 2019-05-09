package app.punk.data.entities

data class BeerDetails(
    val id: Int,
    val name: String,
    val image_url: String,
    val first_brewed: String,
    val description: String,
    val food_pairing: List<String>,
    val brewers_tips: String,
    val tagline: String
)