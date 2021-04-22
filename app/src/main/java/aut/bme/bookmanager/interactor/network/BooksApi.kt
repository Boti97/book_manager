package aut.bme.bookmanager.interactor.network

import aut.bme.bookmanager.model.Book
import aut.bme.bookmanager.model.BookResult
import retrofit2.Call
import retrofit2.http.*


/*
 * Contains the necessary http request declarations for the project.
 */
interface BooksApi {

    @GET("lists/best-sellers/history.json")
    fun getBooks(
        @Query("title") title: String,
        @Query("api-key") apiKey: String
    ): Call<BookResult>

    /**
     * Add a book to favorites
     *
     * @param body Book that needs to be added
     * @return Call<Void>
    </Void> */
    @POST("favorites")
    fun addBookToFavorites(
        @Body body: Book?
    ): Call<Void>


    /**
     * Update a favorite book&#39;s title
     *
     * @param bookId ID of the book
     * @param title The new title of the best seller.
     * @return Call<Void>
    </Void> */
    @PUT("favorites/{bookId}")
    fun updateFavoriteBook(
        @Path("bookId") bookId: Long?, @Query("title") title: String?
    ): Call<Void>


    /**
     * Delete a favorite book
     *
     * @param bookId ID of the book
     * @return Call<Void>
    </Void> */
    @DELETE("favorites/{bookId}")
    fun deleteFavoriteBook(
        @Path("bookId") bookId: Long?
    ): Call<Void>

}