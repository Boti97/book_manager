package aut.bme.bookmanager.interactor.network

import aut.bme.bookmanager.model.Book
import aut.bme.bookmanager.model.BookResult
import retrofit2.Call
import retrofit2.http.*


/*
 * Contains the necessary http request declarations for the project.
 */
interface BooksApi {

    @GET("history.json")
    fun getBooks(
        @Query("title") title: String,
        @Query("api-key") apiKey: String
    ): Call<BookResult>


    /**
     * Get books
     *
     * @param title The title of the book\n\nWhen searching, you can specify a portion of a title or a full title.
     * @return Call<InlineResponse200>
    </InlineResponse200> */
    @GET("lists/best-sellers/history.json")
    fun getBooks(
        @Query("title") title: String?
    ): Call<BookResult>


    /**
     * Post a new book
     *
     * @param body Book that needs to be added
     * @return Call<Void>
    </Void> */
    @POST("lists/best-sellers/history.json")
    fun addBook(
        @Body body: Book?
    ): Call<Void?>?


    /**
     * Update a best seller book title
     *
     * @param bookId ID of the book
     * @param title The new title of the best seller.
     * @return Call<Void>
    </Void> */
    @PUT("lists/best-sellers/history.json/{bookId}")
    fun updateBook(
        @Path("bookId") bookId: Long?, @Query("title") title: String?
    ): Call<Void?>?


    /**
     * Delete a best seller book
     *
     * @param bookId ID of the book
     * @return Call<Void>
    </Void> */
    @DELETE("lists/best-sellers/history.json/{bookId}")
    fun deleteBook(
        @Path("bookId") bookId: Long?
    ): Call<Void?>?


}