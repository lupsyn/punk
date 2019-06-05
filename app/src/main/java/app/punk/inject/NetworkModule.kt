package app.punk.inject

import app.punk.datasources.services.PunkBeerService
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule {

    val punkBeerService by lazy { provideRetrofitAdapter().create(PunkBeerService::class.java) }

    private fun provideRetrofitAdapter(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    private val baseUrl by lazy { "https://api.punkapi.com/" }

    private val endpoint by lazy { HttpUrl.parse(baseUrl)!! }

    private val gsonConverterFactory by lazy { GsonConverterFactory.create(Gson()) }

    private val rxJavaCallAdapterFactory by lazy { RxJava2CallAdapterFactory.create() }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .cache(AppModule.cache)
            .connectTimeout(200, TimeUnit.SECONDS).build()
    }

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }
}

