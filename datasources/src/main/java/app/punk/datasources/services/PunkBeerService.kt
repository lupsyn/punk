package app.punk.datasources.services

import app.punk.datasources.entities.PunkBeer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkBeerService {

    @get:GET("/beers/random")
    val randomPunkBeer: Call<List<PunkBeer>>

    //More info at : https://punkapi.com/documentation/v2

    //curl https://api.punkapi.com/v2/beers?page=2&per_page=80
    //curl https://api.punkapi.com/v2/beers
    //curl https://api.punkapi.com/v2/beers/1
    //curl https://api.punkapi.com/v2/beers/random

    @GET("/beers")
    fun getBeers(@Query(QUERY_PAGE) page: Int, @Query(QUERY_PER_PAGE) perPage: Int): Call<List<PunkBeer>>

    @GET("/beers/{id}")
    fun getBeerDetail(@Path("id") var1: String): Call<List<PunkBeer>>

    companion object {
        const val QUERY_PAGE = "page"
        const val QUERY_PER_PAGE = "per_page"
    }
}
