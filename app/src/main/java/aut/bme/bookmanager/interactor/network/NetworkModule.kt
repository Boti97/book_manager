package aut.bme.bookmanager.interactor.network

import aut.bme.bookmanager.model.NetworkConstants.BOOKS_ENDPOINT
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
 * Contains helper methods for the network communication using retrofit.
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideArtistsApi(client: OkHttpClient): BooksApi {
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BOOKS_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(BooksApi::class.java)
    }
}