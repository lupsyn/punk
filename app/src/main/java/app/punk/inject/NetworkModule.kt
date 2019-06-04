package app.punk.inject

import android.content.Context
import app.punk.datasources.services.PunkBeerService
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule {

    fun providePunkBeerService(): PunkBeerService = provideRetrofitAdapter(AppModule.provideApplicationContext()).create(PunkBeerService::class.java)

    fun provideRetrofitAdapter(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideEndpoint())
            .addConverterFactory(provideGsonConverterFactory())
            .addCallAdapterFactory(provideRxJavaCallAdapterFactory())
            .client(provideOkHttpClient(AppModule.provideCache(context)))
            .build()
    }

    private fun getBaseUrl(): String = "https://api.punkapi.com/"

    private fun provideEndpoint(): HttpUrl = HttpUrl.parse(getBaseUrl())!!

    private fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create(Gson())

    private fun provideRxJavaCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    private fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(provideHttpLoggingInterceptor())
            .cache(cache)
            .connectTimeout(200, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(provideHttpLoggingInterceptor())
        return okHttpClient.build()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

}

