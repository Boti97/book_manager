package aut.bme.bookmanager.interactor.network

import aut.bme.bookmanager.model.BookResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Contains the necessary http request declarations for the project.
 */
interface BooksApi {

    @GET("history.json")
    fun getBooks(
        @Query("title") title: String,
        @Query("api-key") apiKey: String
    ): Call<BookResult>
}